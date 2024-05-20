package br.com.postech.senderorder.sevenfoodorderapi.core.entities;

import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.product.ProductEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal totalPrice;
    private List<ProductEntity> products;
    private String qrCodeBase64;
    private String qrCode;

    public void update(Long id, Order order) {
        this.id = id;
        this.code = order.getCode();
        this.clientId = order.getClientId();
        this.statusPedido = order.getStatusPedido();
    }
}
