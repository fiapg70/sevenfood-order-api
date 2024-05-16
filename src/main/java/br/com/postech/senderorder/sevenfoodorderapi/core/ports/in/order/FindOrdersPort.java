package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;

import java.util.List;

public interface FindOrdersPort {
    List<Order> findAll();
}
