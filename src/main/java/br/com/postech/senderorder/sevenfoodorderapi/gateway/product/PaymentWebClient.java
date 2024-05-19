package br.com.postech.senderorder.sevenfoodorderapi.gateway.product;

import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.PaymentDto;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.PaymentResponseDto;
import br.com.postech.senderorder.sevenfoodorderapi.gateway.dto.ProductResponde;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
public class PaymentWebClient {
    private final WebClient webClient;

    public PaymentWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8888/api/v1").build();
    }

    public PaymentResponseDto setPayment(PaymentDto paymentDto) {
        try {
            log.info("Sending payment request: {}", paymentDto);
            return webClient.post()
                    .uri("/payments")
                    .bodyValue(paymentDto)
                    .retrieve()
                    .bodyToMono(PaymentResponseDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error during web client call: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        }
    }
}
