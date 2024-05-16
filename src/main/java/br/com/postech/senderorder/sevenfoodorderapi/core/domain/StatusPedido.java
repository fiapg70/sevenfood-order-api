package br.com.postech.senderorder.sevenfoodorderapi.core.domain;

public enum StatusPedido {

    EM_PROCESSAMENTO("Em Processamento", 0L),
    RECEBIDO("Recebido", 1L),
    EM_PREPARACAO("Em Preparacao", 2L),
    PRONTO("Pronto", 3L),
    FINALIZADO("Finalizado", 4L);

    private final String status;
    private final Long cod;

    StatusPedido(String status, Long cod) {
        this.status = status;
        this.cod = cod;
    }

    public String getStatus() {
        return status;
    }

    public Long getCod() {
        return cod;
    }
    public static StatusPedido getByCod(Long cod) {
        for (StatusPedido statusPedidoEnum : values()) {
            if (statusPedidoEnum.getCod().equals(cod)) {
                return statusPedidoEnum;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + cod);
    }


}

