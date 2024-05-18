package br.com.postech.senderorder.sevenfoodorderapi.core.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPreparation {
    private String clientId;
    private String orderId;
    private List<Product> products;
}
