package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;

import java.util.List;

public interface CreateOrderPort {
    Order save(Order order, List<Product> product);
}
