package br.com.postech.senderorder.sevenfoodorderapi.application.event;

import org.springframework.context.ApplicationEvent;

public class PaymentApprovedEvent extends ApplicationEvent {

    public PaymentApprovedEvent(Object source) {
        super(source);
    }
}
