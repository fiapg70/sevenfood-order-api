package br.com.postech.senderorder.sevenfoodorderapi.application.database.mapper;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.order.OrderEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "code", target = "code")
    @Mapping(source = "clientId", target = "clientId")
    @Mapping(source = "statusPedido", target = "statusPedido")
    @Mapping(source = "totalPrice", target = "totalPrice")
    OrderEntity fromModelTpEntity(Order order);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    Order fromEntityToModel(OrderEntity orderEntity);

    List<Order> map(List<OrderEntity> orderEntities);
}
