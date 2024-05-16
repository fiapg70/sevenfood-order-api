package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order.impl;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.OrderPreparation;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order.OrderPreparationPort;
import br.com.postech.senderorder.sevenfoodorderapi.core.service.OrderPreparationService;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.queue.QueueOrderEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderPreparationPortImpl implements OrderPreparationPort {

    private final OrderPreparationService orderPreparationService;
    @Override
    public void queueOrder(Order order) throws JsonProcessingException {

        List<Product> products = new ArrayList<>();

        order.getProducts().forEach(product -> {
            Product productResume = new Product();
            productResume.setId(product.getId());
            productResume.setProductId(product.getProductId());
            productResume.setPrice(product.getPrice());
            products.add(productResume);
        });

        OrderPreparation build = OrderPreparation.builder().name(order.getClientId())
                .id(order.getId())
                .products(products).build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        QueueOrderEntity entity = new QueueOrderEntity();
        String json = objectMapper.writeValueAsString(build);
        entity.setJson(json);

        orderPreparationService.sendRequestQueue(entity);

    }
}
