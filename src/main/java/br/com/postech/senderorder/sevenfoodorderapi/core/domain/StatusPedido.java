package br.com.postech.senderorder.sevenfoodorderapi.core.domain;

public enum StatusPedido {

    EM_PROCESSAMENTO("Em Processamento", 0),
    RECEBIDO("Recebido", 1),
    ENVIADO_PRODUCAO("Enviado para Produção", 2),
    EM_PREPARACAO("Em Preparacao", 3),
    PRONTO("Pronto", 4),
    FINALIZADO("Finalizado", 5);

    private final String status;
    private final Integer code;

    StatusPedido(String status, Integer code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    public static StatusPedido getByCode(Integer code) {
        for (StatusPedido statusPedidoEnum : values()) {
            if (statusPedidoEnum.getCode().equals(code)) {
                return statusPedidoEnum;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + code);
    }


}

