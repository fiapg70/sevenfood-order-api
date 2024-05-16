package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderPreparationPort {

    void queueOrder(Order order) throws JsonProcessingException;

}
