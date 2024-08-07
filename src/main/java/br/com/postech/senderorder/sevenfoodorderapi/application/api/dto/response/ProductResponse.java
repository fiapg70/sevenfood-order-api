package br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.response;

import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.ProductCategoryResponse;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.RestaurantResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
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
public class ProductResponse implements Serializable {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1", required = true)
    private Long id;

    @Schema(description = "Name of the Product.",
            example = "Vicente", required = true)
    @Size(min = 3, max = 255)
    private String name;

    @Schema(description = "Description of the Product.",
            example = "Vicente", required = true)
    @Size(min = 0, max = 255)
    private String description;

    @Schema(description = "value the Product.",
            example = "V$", required = true)
    private BigDecimal price;

    @Schema(description = "value the Product.",
            example = "V$", required = true)
    private String pic;

    private ProductCategoryResponse productCategory;

    private RestaurantResponse restaurant;

}
