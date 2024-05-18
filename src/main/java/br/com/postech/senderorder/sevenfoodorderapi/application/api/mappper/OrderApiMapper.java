package br.com.postech.senderorder.sevenfoodorderapi.application.api.mappper;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.OrderRequest;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.response.OrderResponse;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderApiMapper {

    @Mapping(target = "clientId", source = "clientId")
    Order fromRquest(OrderRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    OrderResponse fromEntidy(Order order);

    List<Order> map(List<OrderRequest> orders);
    List<OrderResponse> mapResponse(List<Order> orders);
}
