package br.com.postech.senderorder.sevenfoodorderapi.application.database.repository;

import br.com.postech.senderorder.sevenfoodorderapi.application.database.mapper.OrderMapper;
import br.com.postech.senderorder.sevenfoodorderapi.application.database.mapper.ProductMapper;
import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.OrderCreation;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.out.OrderRepositoryPort;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.PaymentDto;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.PaymentResponseDto;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.ProductResponde;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.product.PaymentWebClient;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.product.ProductWebClient;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.order.OrderEntity;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.product.ProductEntity;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository.OrderRepository;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductWebClient productWebClient;
    private final PaymentWebClient paymentWebClient;

    @Override
    public Order save(OrderCreation orderCreation) {

        try {
            List<ProductEntity> productEntityList = new ArrayList<>();
            AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
            orderCreation.products().forEach(product -> {
                ProductEntity productEntity = productEntityAssembly(product);
                Integer quantity = productEntity.getQuantity();
                BigDecimal priceQuantity = productEntity.getPrice().multiply(BigDecimal.valueOf(quantity));
                log.info("Price quantity: {}", priceQuantity);
                totalPrice.set(totalPrice.get().add(priceQuantity));
                productEntityList.add(productEntity);
            });

            BigDecimal totalPriceForOrder = totalPrice.get();
            OrderEntity savedOrder = assemblyAndSaveOrder(orderCreation, totalPriceForOrder, productEntityList);

            PaymentResponseDto paymentResponseDto = createPaymentByOrder(savedOrder);
            if (paymentResponseDto == null) {
                throw new RuntimeException("Error to create payment");
            }
            //TODO - se o pagamento vier ok manda para prodution (cozinha)
            return assemblyOrderResponse(savedOrder, paymentResponseDto);
        } catch (Exception e) {
            log.error("Error to save order", e);
            throw new RuntimeException("Order already exists");
        }
    }
    
    @Override
    public boolean remove(Long id) {
        try {
            orderRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Order findById(Long id) {
        Optional<OrderEntity> buOrder = orderRepository.findById(id);
        if (buOrder.isPresent()) {
            return orderMapper.fromEntityToModel(buOrder.get());
        }
        return null;
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> all = orderRepository.findAll();
        return orderMapper.map(all);
    }

    @Override
    public Order update(Long id, Order order) {
        Optional<OrderEntity> resultById = orderRepository.findById(id);
        if (resultById.isPresent()) {
            OrderEntity orderToChange = resultById.get();
            orderToChange.update(id, orderMapper.fromModelTpEntity(order));

            return orderMapper.fromEntityToModel(orderRepository.save(orderToChange));
        }

        return null;
    }

    @Override
    public Order findByCode(String code) {
        return orderRepository.findByCode(code)
                .map(orderMapper::fromEntityToModel)
                .orElse(null);
    }

    private ProductResponde findProductInfoClient(String productId) {
        return productWebClient.getProductByCode(productId);
    }

    private PaymentResponseDto createPaymentByOrder(OrderEntity savedOrder) {
        PaymentDto paymentDto = PaymentDto.builder()
                .orderId(savedOrder.getCode())
                .clientId(savedOrder.getClientId())
                .transactionAmount(savedOrder.getTotalPrice())
                .build();
        return paymentWebClient.setPayment(paymentDto);
    }

    private OrderEntity assemblyAndSaveOrder(OrderCreation orderCreation,  BigDecimal totalPrice, List<ProductEntity> productEntityList) {
        OrderEntity orderEntity = OrderEntity.builder()
                .statusPedido(StatusPedido.EM_PROCESSAMENTO)
                .clientId(orderCreation.clientId())
                .code(UUID.randomUUID().toString())
                .totalPrice(totalPrice)
                .products(productEntityList)
                .build();

        return orderRepository.save(orderEntity);
    }

    private ProductEntity productEntityAssembly(Product product) {
        ProductEntity productEntity = productMapper.fromModelTpEntity(product);
        String productId = productEntity.getProductId();

        ProductResponde productsInfos = findProductInfoClient(productId);
        productEntity.setPrice(productsInfos.getPrice());
        productEntity.setName(productsInfos.getName());
        productEntity.setQuantity(product.getQuantity());

        return productRepository.save(productEntity);
    }

    private Order assemblyOrderResponse(OrderEntity savedOrder, PaymentResponseDto paymentResponseDto) {
        Order orderResponse = orderMapper.fromEntityToModel(savedOrder);
        orderResponse.setQrCodeBase64(paymentResponseDto.getQrCodeBase64());
        orderResponse.setQrCode(paymentResponseDto.getQrCode());
        return orderResponse;
    }
}
