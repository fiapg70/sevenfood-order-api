package br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.response;

import br.com.postech.senderorder.sevenfoodorderapi.core.domain.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Product object")
public class OrderResponse implements Comparator<OrderResponse> {

    @Schema(description = "Unique identifier of the Driver.",
            example = "1", required = true)
    private Long id;

    private String clientId;

    @Schema(description = "Name of the Product.",
            example = "Vicente", required = true)
    @Size(min = 3, max = 255)
    private String orderId;

    @Schema(description = "Description of the Product.",
            example = "Vicente", required = true)
    @Size(min = 0, max = 255)
    private List<ProductResponse> products;

    private StatusPedido statusPedido;

    private BigDecimal totalPrice;

    private String qrCodeBase64;
    private String qrCode;

    @Override
    public int compare(OrderResponse o1, OrderResponse o2) {
        // Define a ordem desejada para o status do pedido
        List<StatusPedido> order = List.of(
                StatusPedido.PRONTO,
                StatusPedido.EM_PREPARACAO,
                StatusPedido.RECEBIDO
        );

            // Compara o statusPedidoEnum usando a ordem definida
            int statusComparison = Integer.compare(
                    order.indexOf(o1.getStatusPedido()),
                    order.indexOf(o2.getStatusPedido())
            );


            // Se os status são diferentes, retorna a comparação
            if (statusComparison != 0) {
                return statusComparison;
            }
        // Se os status são iguais, compare pelos IDs em ordem crescente
        return Long.compare(o1.getId(), o2.getId());
    }
}
