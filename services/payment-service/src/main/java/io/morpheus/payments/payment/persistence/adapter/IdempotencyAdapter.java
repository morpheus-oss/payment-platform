package io.morpheus.payments.payment.persistence.adapter;

import io.morpheus.payments.payment.application.port.out.IdempotencyPort;
import io.morpheus.payments.payment.persistence.entity.IdempotencyEntity;
import io.morpheus.payments.payment.persistence.repository.IdempotencyRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class IdempotencyAdapter implements IdempotencyPort
{

    private final IdempotencyRepository idempotencyRepository;

    public IdempotencyAdapter(final IdempotencyRepository idempotencyRepository) {
        this.idempotencyRepository = idempotencyRepository;
    }

    @Override
    public Optional<UUID> findTransferId(final String idempotencyKey) {
        return idempotencyRepository.findByIdempotencyKey(idempotencyKey)
                                    .map(IdempotencyEntity::getTransferId);
    }

    @Override
    public void save(final String idempotencyKey,
                     final UUID transferId) {

        IdempotencyEntity entity = new IdempotencyEntity();
        entity.setId(UUID.randomUUID());
        entity.setIdempotencyKey(idempotencyKey);
        entity.setTransferId(transferId);

        idempotencyRepository.save(entity);
    }
}
