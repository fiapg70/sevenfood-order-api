package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.product;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;

import java.util.List;

public interface FindProductsPort {
    List<Product> findAll();
}
