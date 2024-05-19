package br.com.postech.senderorder.sevenfoodorderapi.core.ports.out;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;

import java.util.List;

public interface OrderRepositoryPort {
    Order save(Order order, List<Product> products);
    boolean remove(Long id);
    Order findById(Long id);
    List<Order> findAll();
    Order update(Long id, Order order);
    Order findByCode(String code);
}
