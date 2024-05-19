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
public class ProductResponde {
    private Long id;
    private String name;
    private String code;
    private String description;
    private BigDecimal price;
    private String pic;
    private ProductCategoryResponse productCategory;
    private RestaurantResponse restaurant;
}