package br.com.postech.senderorder.sevenfoodorderapi.core.ports.in.order;

public interface DeleteOrderPort {
    boolean remove(Long id);
}
