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
    @Mapping(target = "orderId", source = "code")
    @Mapping(target = "statusPedido", source = "statusPedido")
    @Mapping(target = "products", source = "products")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "qrCodeBase64", source = "qrCodeBase64")
    @Mapping(target = "qrCode", source = "qrCode")
    OrderResponse fromEntidy(Order order);

    List<Order> map(List<OrderRequest> orders);

    List<OrderResponse> mapResponse(List<Order> orders);
}
