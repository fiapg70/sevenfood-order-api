package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.product;


import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;

public interface CreateProductPort {
    Product save(Product product);
}
