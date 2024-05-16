package br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository;

import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
