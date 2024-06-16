package br.com.postech.senderorder.sevenfoodorderapi.core.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import br.com.postech.senderorder.sevenfoodorderapi.core.domain.OrderStatusDTO;
import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.order.OrderEntity;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository.OrderRepository;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository.QueueOrderRepository;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.List;
@ExtendWith(MockitoExtension.class)
public class OrderPreparationServiceTest {

    @Mock
    private QueueOrderRepository queueOrderRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SqsTemplate sqsTemplate;

    @InjectMocks
    private OrderPreparationService orderPreparationService;

    @Value("${app.queue-sqs.name}")
    private String queueName;

    @Test
    public void whenSendRequestQueue_thenSaveQueueOrderEntity() {
        // Preparação
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatusPedido(StatusPedido.EM_PROCESSAMENTO);
        List<OrderEntity> orderList = Collections.singletonList(orderEntity);
        when(orderRepository.findByStatusPedido(StatusPedido.EM_PROCESSAMENTO)).thenReturn(orderList);

        // Ação
        orderPreparationService.sendRequestQueue();

        // Verificação
        verify(queueOrderRepository, times(1)).save(any());
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(sqsTemplate, times(1)).send(any());
    }

    @Test
    public void whenListen_thenUpdateOrderStatus() {
        // Preparação
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO("1", 1);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setStatusPedido(StatusPedido.EM_PROCESSAMENTO);
        when(orderRepository.findByCode(orderStatusDTO.getOrderId())).thenReturn(java.util.Optional.of(orderEntity));

        Acknowledgement acknowledgement = mock(Acknowledgement.class);

        // Ação
        orderPreparationService.listen("{\"orderId\":\"1\",\"statusPedido\":\"1\"}", null, acknowledgement);

        // Verificação
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
        verify(acknowledgement, times(1)).acknowledge();
    }
}
