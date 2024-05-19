package br.com.postech.senderorder.sevenfoodorderapi.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDTO {
    private String orderId;
    private Integer statusPedido;
}
