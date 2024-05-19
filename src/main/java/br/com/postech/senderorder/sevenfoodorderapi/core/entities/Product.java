package br.com.postech.senderorder.sevenfoodorderapi.core.entities;

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
//@Tag(name = "Resident object")
public class Product implements Serializable {
    private Long id;
    private String productId;
    private Integer quantity;
}
