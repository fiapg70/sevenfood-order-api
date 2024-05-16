package br.com.postech.senderorder.sevenfoodorderapi.core.entities;

import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.ProductRequest;
import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Tag(name = "Resident object")
public class Order implements Serializable {
    private Long id;
    private String clientId;
    private String code;
    private List<Product> products;
    private StatusPedido statusPedido;
    private Double totalPrice;

    public void createOrder(String clientId, List<Product> products) {
        this.clientId = clientId;
        this.products = products;
    }

    public void update(Long id, Order order) {
        this.id = id;
        this.code = order.getCode();
        this.products = order.getProducts();
        this.clientId = order.getClientId();
        this.products = order.getProducts();
        this.statusPedido = order.getStatusPedido();
    }
}
