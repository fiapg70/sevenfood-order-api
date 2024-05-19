package br.com.postech.senderorder.sevenfoodorderapi.gateway.product;

import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.ProductResponde;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class ProductWebClient {
    private final WebClient webClient;

    public ProductWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:9992/api/v1").build();
    }

    public ProductResponde getProductByCode(String code) {
        return webClient.get()
                .uri("/products/code/{code}", code)
                .retrieve()
                .bodyToMono(ProductResponde.class)
                .block();
    }
}
