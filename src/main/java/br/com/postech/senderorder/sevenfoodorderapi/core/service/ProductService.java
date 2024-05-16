package br.com.postech.senderorder.sevenfoodorderapi.core.service;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.product.*;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.out.ProductRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService implements CreateProductPort, FindByIdProductPort, FindProductsPort {

    private final ProductRepositoryPort productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
       return productRepository.findAll();
    }
}
