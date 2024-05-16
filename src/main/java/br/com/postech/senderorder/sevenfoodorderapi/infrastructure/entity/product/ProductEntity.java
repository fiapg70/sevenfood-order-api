package br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.product;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.domain.AuditDomain;
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

@Entity
@Table(name = "tb_product")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Product object")
public class ProductEntity extends AuditDomain {

    @Schema(description = "Unique identifier of the Product.",
            example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Schema(description = "description of the Product.",
            example = "V$", required = false)
    @Size(min = 0, max = 255)
    @Column(name = "description", length = 255)
    private String productId;

    @Schema(description = "price of the Product.",
            example = "V$", required = true)
    @NotNull(message = "o campo \"price\" é obrigario")
    private BigDecimal price;

    public void update(Long id, Product product) {
        this.id = id;
        this.productId = product.getProductId();
        this.price = product.getPrice();
    }
}
