package br.com.postech.senderorder.sevenfoodorderapi.core.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.out.OrderRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepositoryPort orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        // Inicialize suas entidades Order e Product aqui
        order = new Order();
        products = Arrays.asList(new Product(), new Product());
    }

    @Test
    void whenSaveOrder_thenOrderIsSaved() {
        when(orderRepository.save(order, products)).thenReturn(order);
        Order savedOrder = orderService.save(order, products);
        assertNotNull(savedOrder);
        verify(orderRepository).save(order, products);
    }

    @Test
    void whenUpdateOrder_thenOrderIsUpdated() {
        Long orderId = 1L;
        when(orderRepository.update(orderId, order)).thenReturn(order);
        Order updatedOrder = orderService.update(orderId, order);
        assertNotNull(updatedOrder);
        verify(orderRepository).update(orderId, order);
    }

    @Test
    void whenFindById_thenOrderIsFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(order);
        Order foundOrder = orderService.findById(orderId);
        assertNotNull(foundOrder);
        verify(orderRepository).findById(orderId);
    }

    @Test
    void whenFindByCode_thenOrderIsFound() {
        String orderCode = "CODE123";
        when(orderRepository.findByCode(orderCode)).thenReturn(order);
        Order foundOrder = orderService.findByCode(orderCode);
        assertNotNull(foundOrder);
        verify(orderRepository).findByCode(orderCode);
    }

    @Test
    void whenFindAll_thenAllOrdersAreFound() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order, order));
        List<Order> orders = orderService.findAll();
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        verify(orderRepository).findAll();
    }


    @Test
    void whenRemoveOrderFails_thenFalseIsReturned() {
        Long orderId = 1L;
        doThrow(new RuntimeException()).when(orderRepository).remove(orderId);
        boolean result = orderService.remove(orderId);
        assertFalse(result);
    }
}
