package br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.order;

import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.domain.AuditDomain;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.product.ProductEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Restaurant object")
public class OrderEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Product.",
            example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "Code of the Product.",
            example = "V$", required = true)
    @NotNull(message = "o campo \"name\" é obrigario")
    @Size(min = 3, max = 255)
    @Column(name = "code", length = 255)
    private String code;

    @Schema(description = "Client of the User.",
            example = "1", required = true, ref = "User")
    @NotNull
    @Column(name = "client_id")
    private String clientId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products;

    @Column(name = "status_pedido", nullable = false)
    @NotNull(message = "o campo \"status\" é obrigario")
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public void update(Long id, OrderEntity order) {
        this.id = id;
        this.code = order.getCode();
        this.clientId = order.getClientId();
        this.statusPedido = order.getStatusPedido();
        //this.products = order.getProducts();
    }
}
