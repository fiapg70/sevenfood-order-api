package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;

public interface FindByIdOrderPort {
    Order findById(Long id);
    Order findByCode(String code);
}
