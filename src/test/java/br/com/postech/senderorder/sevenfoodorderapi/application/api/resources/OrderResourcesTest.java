package br.com.postech.senderorder.sevenfoodorderapi.application.api.resources;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.OrderRequest;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.ProductRequest;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.response.OrderResponse;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.mappper.OrderApiMapper;
import br.com.postech.senderorder.sevenfoodorderapi.core.entities.Order;
import br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order.CreateOrderPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class OrderResourcesTest {

    private MockMvc mockMvc;

    @Mock
    private CreateOrderPort createOrderPort;

    @Mock
    private OrderApiMapper orderApiMapper;

    @InjectMocks
    private OrderResources orderResources;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderResources).build();
    }

    @Test
    public void createOrder() throws Exception {
        // Preparação dos dados de entrada
        List<ProductRequest> products = Arrays.asList(
                new ProductRequest("25388495-e14d-4e36-acd3-4104a7b8d9c5", 3),
                new ProductRequest("44fa7ad7-53ac-4851-9af7-585b40905e79", 2),
                new ProductRequest("28b94efa-9769-470c-a12e-7c19078d9a20", 1),
                new ProductRequest("0f45add4-f41f-4446-bcf3-36200e562100", 1),
                new ProductRequest("afcbfc2e-262f-4e47-a420-669df0ae46cb", 5)
        );

        OrderRequest orderRequest = new OrderRequest("58070d0f-c48d-4571-b8ad-84b2169a402d", products);

        // Configuração do Mockito
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);
        given(orderApiMapper.fromRquest(any(OrderRequest.class))).willReturn(new Order());
        given(createOrderPort.save(any(Order.class), anyList())).willReturn(new Order());
        given(orderApiMapper.fromEntidy(any(Order.class))).willReturn(orderResponse);

        // URI de localização esperada
        URI location = new URI("/v1/orders/" + orderResponse.getId());

        // Execução do teste
        mockMvc.perform(post("/v1/orders")
                        .content(new ObjectMapper().writeValueAsString(orderRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", location.toString()))
                .andExpect(jsonPath("$.id").value(orderResponse.getId()));

        // Verificações
        then(orderApiMapper).should().fromRquest(any(OrderRequest.class));
        then(createOrderPort).should().save(any(Order.class), anyList());
        then(orderApiMapper).should().fromEntidy(any(Order.class));
    }
}
