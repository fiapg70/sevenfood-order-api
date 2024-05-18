package br.com.postech.senderorder.sevenfoodorderapi.core.service;

import br.com.postech.senderorder.sevenfoodorderapi.commons.util.JsonMapperUtil;
import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.order.OrderEntity;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.queue.QueueOrderEntity;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository.OrderRepository;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository.QueueOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderPreparationService {

    private final QueueOrderRepository repository;
    private final OrderRepository orderRepository;

    @Scheduled(fixedRate = 30000)
    public void sendRequestQueue() {
        ObjectMapper objectMapper = JsonMapperUtil.getObjectMapper();

        List<OrderEntity> produtosList = orderRepository.findByStatusPedido(StatusPedido.EM_PROCESSAMENTO);
        log.info("OrderPreparationService.sendRequestQueue {}", produtosList);
        produtosList.forEach(order -> {
            try {
                log.info("OrderPreparationService.sendRequestQueue {}", order);
                var orderMesssage = objectMapper.writeValueAsString(order);

                QueueOrderEntity queueOrderEntity = QueueOrderEntity.builder().id(order.getId()).message(orderMesssage).build();
                repository.save(queueOrderEntity);
                log.info("OrderPreparationService.sendRequestQueue {}", orderMesssage);
                order.setStatusPedido(StatusPedido.ENVIADO_PRODUCAO);
                orderRepository.save(order);
                //TODO: Send to SQS queue
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}