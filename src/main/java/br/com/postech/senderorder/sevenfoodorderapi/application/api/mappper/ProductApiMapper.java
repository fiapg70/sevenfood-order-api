package br.com.postech.senderorder.sevenfoodorderapi.application.api.mappper;

import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.ProductRequest;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.response.ProductResponse;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductApiMapper {

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "price", target = "price")
    Product fromRquest(ProductRequest request);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id")
    ProductResponse fromEntidy(Product product);

   List<Product> map(List<ProductRequest> products);


}
