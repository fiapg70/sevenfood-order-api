package br.com.postech.senderorder.sevenfoodorderapi.core.ports.out;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;

import java.util.List;

public interface ProductRepositoryPort {
    Product save(Product product);
    boolean remove(Long id);
    Product findById(Long id);
    List<Product> findAll();
    Product update(Long id, Product product);
}
