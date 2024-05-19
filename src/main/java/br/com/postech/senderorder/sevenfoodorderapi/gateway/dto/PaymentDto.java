package br.com.postech.senderorder.sevenfoodorderapi.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private BigDecimal transactionAmount;
    private String description;
    private String clientId;
    private String orderId;
}