package br.com.postech.senderorder.sevenfoodorderapi.application.database.mapper;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.product.ProductEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "quantity", target = "quantity")
    ProductEntity fromModelTpEntity(Product product);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    Product fromEntityToModel(ProductEntity productEntity);

    List<Product> map(List<ProductEntity> productEntities);
}
