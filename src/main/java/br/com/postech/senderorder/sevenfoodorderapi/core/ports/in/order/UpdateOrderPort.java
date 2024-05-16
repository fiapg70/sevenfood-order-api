package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;

public interface UpdateOrderPort {
    Order update(Long id, Order order);
}
