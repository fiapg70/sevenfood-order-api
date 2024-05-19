package br.com.postech.senderorder.sevenfoodorderapi.bdd;

import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.OrderRequest;
import br.com.postech.senderorder.sevenfoodorderapi.application.api.dto.request.ProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class DefinicaoPassos {

    private Response response;

    private String orderRequestJson;
    private final String ENDPOINT = "http://localhost:8080/v1/orders";

    @Dado("que eu tenho um pedido válido para criar")
    public void que_eu_tenho_um_pedido_válido_para_criar() throws JsonProcessingException {

        ProductRequest product1 = new ProductRequest();
        product1.setProductId("1");
        product1.setPrice(new BigDecimal("10.99"));

        ProductRequest product2 = new ProductRequest();
        product2.setProductId("2");
        product2.setPrice(new BigDecimal("5.49"));

        List<ProductRequest> products = Arrays.asList(product1, product2);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setClientId("Luara Balestero da Mata");
        orderRequest.setProducts(products);

        orderRequestJson = new ObjectMapper().writeValueAsString(orderRequest);
    }

    @Quando("eu envio uma solicitação")
    public void eu_envio_uma_solicitação() {
        response = given()
                .contentType("application/json")
                .body(orderRequestJson)
                .when()
                .post(ENDPOINT);
    }

    @Então("eu recebo uma resposta com status 201")
    public void eu_recebo_uma_resposta_com_status() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/OrderResponseSchema.json"));;
    }

    @Então("o pedido é salvo no sistema")
    public void o_pedido_é_salvo_no_sistema() {
        // Verifica se o corpo da resposta contém um ID de pedido não nulo
        response.then().body("id", notNullValue());

        // Adicionais verificações podem incluir:
        // Verificar se o status do pedido está correto
        response.then().body("statusPedido", equalTo("EM_PREPARACAO"));

        // Verificar se a lista de produtos não está vazia
        response.then().body("products.size()", greaterThan(0));

        // Verificar se as informações do cliente estão presentes
        response.then().body("client.id", notNullValue());
        response.then().body("client.name", equalTo("Luara Balestero da Mata"));

        // Verificar se o código do pedido está presente
        response.then().body("code", notNullValue());
    }
}
