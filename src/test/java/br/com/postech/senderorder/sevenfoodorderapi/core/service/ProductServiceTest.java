package br.com.postech.senderorder.sevenfoodorderapi.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Product;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.out.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        // Inicialize sua entidade Product aqui
        product = new Product();
        product.setId(1L);
        product.setProductId("25388495-e14d-4e36-acd3-4104a7b8d9c5");

    }

    @Test
    void whenSaveProduct_thenProductIsSaved() {
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertNotNull(savedProduct);
        verify(productRepository).save(product);
    }

    @Test
    void whenFindById_thenProductIsFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(product);
        Product foundProduct = productService.findById(productId);
        assertNotNull(foundProduct);
        verify(productRepository).findById(productId);
    }

    @Test
    void whenFindAll_thenAllProductsAreFound() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product, product));
        List<Product> products = productService.findAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        verify(productRepository).findAll();
    }
}
