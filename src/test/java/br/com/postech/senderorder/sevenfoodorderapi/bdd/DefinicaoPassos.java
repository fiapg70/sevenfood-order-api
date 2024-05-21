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
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DefinicaoPassos {

    private Response response;

    private String orderRequestJson;
    private final String ENDPOINT = "http://localhost:9996/api/v1/orders";

    @Dado("que eu tenho um pedido válido para criar")
    public void que_eu_tenho_um_pedido_válido_para_criar() throws JsonProcessingException {

        ProductRequest product1 = new ProductRequest();
        product1.setProductId("25388495-e14d-4e36-acd3-4104a7b8d9c5");
        product1.setQuantity(3);

        ProductRequest product2 = new ProductRequest();
        product2.setProductId("44fa7ad7-53ac-4851-9af7-585b40905e79");
        product2.setQuantity(2);

        ProductRequest product3 = new ProductRequest();
        product3.setProductId("28b94efa-9769-470c-a12e-7c19078d9a20");
        product3.setQuantity(1);

        ProductRequest product4 = new ProductRequest();
        product4.setProductId("0f45add4-f41f-4446-bcf3-36200e562100");
        product4.setQuantity(1);

        ProductRequest product5 = new ProductRequest();
        product5.setProductId("afcbfc2e-262f-4e47-a420-669df0ae46cb");
        product5.setQuantity(5);

        List<ProductRequest> products = Arrays.asList(product1, product2, product3, product4, product5);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setClientId("58070d0f-c48d-4571-b8ad-84b2169a402d");
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

//    @Então("eu recebo uma resposta com status 201")
//    public void eu_recebo_uma_resposta_com_status() {
//        response.then().statusCode(HttpStatus.CREATED.value());
//    }

    @Então("o pedido é salvo no sistema")
    public void o_pedido_é_salvo_no_sistema() {
        response.then().body("id", notNullValue());

        response.then().body("statusPedido", equalTo("EM_PROCESSAMENTO"));

        response.then().body("products.size()", greaterThan(0));

        response.then().body("clientId", notNullValue());

        response.then().body("qrCodeBase64", notNullValue());
        response.then().body("qrCode", notNullValue());
    }


    @Quando("eu envio uma solicitação GET para orders")
    public void eu_envio_uma_solicitação_get_para_orders() {
        response = given()
                .contentType("application/json")
                .when()
                .get(ENDPOINT);
    }

    @Então("eu recebo uma resposta com status {int}")
    public void eu_recebo_uma_resposta_com_status(Integer status) {
        response.then().statusCode(status);
    }

    @Então("uma lista de todos os pedidos é retornada")
    public void uma_lista_de_todos_os_pedidos_é_retornada() {
        response.then().body("$", not(empty()));
    }

    @Quando("eu envio uma solicitação GET para orders passando o id do pedido")
    public void eu_envio_uma_solicitação_get_para_orders_passando_o_id_do_pedido() {
        response = given()
                .contentType("application/json")
                .when()
                .get(ENDPOINT + "/" + 4);
    }
    @Então("os detalhes do pedido com ID passado são retornados")
    public void os_detalhes_do_pedido_com_id_passado_são_retornados() {
        response.then().statusCode(HttpStatus.OK.value());
        response.then().body("id", equalTo(4));
        response.then().body("products.size()", greaterThan(0));
        response.then().body("clientId", notNullValue());

    }

    @Quando("eu envio uma solicitação DELETE para orders passando o id do pedido {int}")
    public void eu_envio_uma_solicitação_delete_para_orders_passando_o_id_do_pedido(Integer int1) {
        response = given()
                .contentType("application/json")
                .when()
                .delete(ENDPOINT + "/" + int1);
    }

    @Então("o pedido com ID {int} é removido do sistema")
    public void o_pedido_com_id_é_removido_do_sistema(Integer orderId) {
        given()
                .contentType("application/json")
                .when()
                .get(ENDPOINT + "/" + orderId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

}
