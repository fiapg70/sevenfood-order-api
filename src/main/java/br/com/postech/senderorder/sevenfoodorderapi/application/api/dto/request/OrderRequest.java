package br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Order object")
public class OrderRequest implements Serializable {

    @Schema(description = "Client of the Product.",
            example = "Luara Balestero da Mata", required = true, ref = "Client")
    private String clientId;

    private List<ProductRequest> products;
}
