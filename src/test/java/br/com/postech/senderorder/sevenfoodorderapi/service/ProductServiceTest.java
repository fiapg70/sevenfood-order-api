/*package br.com.postech.senderorder.sevenfoodorderapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService restaurantService;

    @Mock
    ProductRepositoryPort restaurantRepository;

    @Mock
    ProductRepository repository;

    @Mock
    ProductMapper mapper;


    private ProductEntity getProductEntity() {
        return ProductEntity.builder()
                .name("Seven Food")
                .cnpj("02.365.347/0001-63")
                .build();
    }

    private Product getProduct() {
        return Product.builder()
                .name("Seven Food")
                .cnpj("02.365.347/0001-63")
                .build();
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    public void getAllEmployeesTest() {
        List<Product> list = new ArrayList<>();
        List<ProductEntity> listEntity = new ArrayList<>();

        Product client = getProduct();
       // Product client1 = getProduct();
        //Product client2 = getProduct();

        ProductEntity clientEntity = getProductEntity();
        //ProductEntity clientEntity1 = getProductEntity();
        //ProductEntity clientEntity2 = getProductEntity();

        list.add(client);
        //list.add(client1);
        //list.add(client2);

        listEntity.add(clientEntity);
        //listEntity.add(clientEntity1);
        //listEntity.add(clientEntity2);

        when(repository.findAll()).thenReturn(listEntity);
        List<Product> restaurants = mapper.map(listEntity);
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        // test
        List<Product> restaurantList = restaurantService.findAll();

        assertNotNull(restaurantList);
        //verify(restaurantRepository, times(1)).findAll();
    }

    @Disabled
    public void getProductByIdTest() {
        Product restaurant1 = getProduct();
        when(restaurantRepository.findById(1L)).thenReturn(restaurant1);

        Product restaurant = restaurantService.findById(1L);

        assertEquals("Seven Food", restaurant.getName());
        assertEquals("02.365.347/0001-63", restaurant.getCnpj());
    }

    /*@Test
    public void getFindProductByShortIdTest() {
        Product client = getProduct();
        when(restaurantService.findById(1l)).thenReturn(Optional.ofNullable(client));

        Optional<Product> result = restaurantService.findById(1l);

        assertEquals("Everis", client.getName());
        assertEquals("root@localhost", client.getEmail());
    }

    @Test
    public void createProductTest() {
        Product url = getProduct();
        restaurantService.save(url);

        verify(restaurantRepository, times(1)).save(url);
    }

    @Test
    public void createAndStoreProductTest() {
        Product client = getProduct();
        restaurantService.save(client);

        when(restaurantService.save(client)).thenReturn(client);
        Product result = restaurantService.save(client);

        assertEquals("root@localhost", result.getEmail());
    }

    @Test(expected = URLException.class)
    public void createAndStoreProductTest_and_should_throw_constraint_violation_execption() {
        thrown.expect(URLException.class);
        thrown.expectMessage("Product is not valid!");

        Product client = getProduct();
        restaurantService.save(client);

        when(restaurantService.save(client)).thenReturn(client);
        Product result = restaurantService.save(client);

        assertEquals("root@localhost", result.getEmail());
    }

}*/