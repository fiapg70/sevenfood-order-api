package br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Product object")
public class ProductRequest implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1", required = true)
    private String productId;

    private BigDecimal price;
}
