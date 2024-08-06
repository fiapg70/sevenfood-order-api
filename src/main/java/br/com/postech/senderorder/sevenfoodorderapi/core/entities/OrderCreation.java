package br.com.postech.senderorder.sevenfoodorderapi.core.entities;

import java.util.List;

public record OrderCreation(String clientId, List<Product> products) {
}
