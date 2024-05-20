package br.com.postech.senderorder.sevenfoodorderapi.application.database.repository;

import br.com.postech.senderorder.sevenfoodorderapi.application.database.mapper.OrderMapper;
import br.com.postech.senderorder.sevenfoodorderapi.application.database.mapper.ProductMapper;
import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.out.OrderRepositoryPort;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.PaymentDto;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.PaymentResponseDto;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.ProductResponde;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.product.ProductWebClient;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.product.PaymentWebClient;
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
    public Order save(Order order, List<Product> products) {
        try {
            order.setStatusPedido(StatusPedido.EM_PROCESSAMENTO);

            List<ProductEntity> productEntityList = new ArrayList<>();
            AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);
            products.forEach(product -> {
                ProductEntity productEntity = productMapper.fromModelTpEntity(product);
                String productId = productEntity.getProductId();

                ProductResponde productByCode = productWebClient.getProductByCode(productId);
                productEntity.setPrice(productByCode.getPrice());
                productEntity.setName(productByCode.getName());
                productEntity.setQuantity(product.getQuantity());

                ProductEntity saved = productRepository.save(productEntity);
                productEntityList.add(saved);
                totalPrice.set(totalPrice.get().add(saved.getPrice()));
            });

            OrderEntity orderEntity = orderMapper.fromModelTpEntity(order);
            orderEntity.setCode(UUID.randomUUID().toString());
            orderEntity.setProducts(productEntityList);
            orderEntity.setTotalPrice(totalPrice.get());
            OrderEntity saved = orderRepository.save(orderEntity);

            PaymentDto paymentDto = PaymentDto.builder()
                    .orderId(saved.getCode())
                    .clientId(saved.getClientId())
                    .transactionAmount(saved.getTotalPrice())
                    .build();

            PaymentResponseDto paymentResponseDto = paymentWebClient.setPayment(paymentDto);
            log.info("PaymentResponseDto: {}", paymentResponseDto);

            Order orderResponse = orderMapper.fromEntityToModel(saved);
            orderResponse.setQrCodeBase64(paymentResponseDto.getQrCodeBase64());
            orderResponse.setQrCode(paymentResponseDto.getQrCode());
            return orderResponse;
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
}
