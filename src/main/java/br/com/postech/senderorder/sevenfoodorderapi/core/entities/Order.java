package br.com.postech.senderorder.sevenfoodorderapi.core.entities;

import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Resident object")
public class Order implements Serializable {
    private Long id;
    private String clientId;
    private String code;
    private StatusPedido statusPedido;
    private Double totalPrice;

    public void update(Long id, Order order) {
        this.id = id;
        this.code = order.getCode();
        this.clientId = order.getClientId();
        this.statusPedido = order.getStatusPedido();
    }
}
