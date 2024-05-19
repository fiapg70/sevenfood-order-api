package br.com.postech.senderorder.sevenfoodorderapi.core.service;

import br.com.postech.senderorder.sevenfoodorderapi.commons.util.JsonMapperUtil;
import br.com.postech.senderorder.sevenfoodorderapi.core.domain.OrderStatusDTO;
import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.order.OrderEntity;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.queue.QueueOrderEntity;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository.OrderRepository;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository.QueueOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class OrderPreparationService {

    @Value("${app.queue-sqs.name}")
    private String queueName;

    private final QueueOrderRepository repository;
    private final OrderRepository orderRepository;
    private final SqsTemplate sqsTemplate;

    @Autowired
    public OrderPreparationService(QueueOrderRepository repository, OrderRepository orderRepository, SqsTemplate sqsTemplate) {
        this.repository = repository;
        this.orderRepository = orderRepository;
        this.sqsTemplate = sqsTemplate;
    }

    @PostConstruct
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
                sendMessage(orderMesssage);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendMessage(String message) {
        sqsTemplate
                .send(sqsSendOptions ->
                        sqsSendOptions
                                .queue(queueName)
                                .payload(message)
                );
    }

    //@SqsListener(value = "${app.queue-status-sqs.name}")
    public void listen(String payload, @Headers Map<String, Object> header, Acknowledgement acknowledgement) {
        try {
            ObjectMapper objectMapper = JsonMapperUtil.getObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            OrderStatusDTO orderStatusDTO = objectMapper.readValue(payload, OrderStatusDTO.class);

            orderRepository.findByCode(orderStatusDTO.getOrderId()).ifPresent(order -> {
                order.setStatusPedido(StatusPedido.getByCode(orderStatusDTO.getStatusPedido()));
                orderRepository.save(order);
            });

            log.info("OrderPreparationService.listen {}", payload);
            // Acknowledge the message after successful processing
            acknowledgement.acknowledge();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}