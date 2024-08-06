package br.com.postech.senderorder.sevenfoodorderapi.core.service;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.OrderCreation;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order.*;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.out.OrderRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService implements CreateOrderPort, UpdateOrderPort, FindByIdOrderPort, FindOrdersPort, DeleteOrderPort {

    private final OrderRepositoryPort orderRepository;

    @Override
    public Order save(OrderCreation orderCreation) {
        return orderRepository.save(orderCreation);
    }

    @Override
    public Order update(Long id, Order order) {
        return orderRepository.update(id, order);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order findByCode(String code) {
        return orderRepository.findByCode(code);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            orderRepository.remove(id);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
