package br.com.postech.senderorder.sevenfoodorderapi.gateway.product;

import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.ProductResponde;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;


@Component
public class ProductWebClient {

    @Value("${app.product-rest.url}")
    private String url;

    private final WebClient.Builder webClientBuilder;
    private WebClient webClient;

    public ProductWebClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostConstruct
    private void init() {
        this.webClient = webClientBuilder.baseUrl(url).build();
    }

    public ProductResponde getProductByCode(String code) {
        return webClient.get()
                .uri("/products/code/{code}", code)
                .retrieve()
                .bodyToMono(ProductResponde.class)
                .block();
    }

    private String getUrl() {
        return url;
    }
}
