package br.com.postech.senderorder.sevenfoodorderapi.infrastructure.repository;

import br.com.postech.senderorder.sevenfoodorderapi.infrastructure.entity.queue.QueueOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueOrderRepository extends JpaRepository<QueueOrderEntity, Long> {
}
