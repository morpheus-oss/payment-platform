package io.morpheus.payments.payment.persistence.mapper;

import io.morpheus.payments.payment.persistence.entity.IdempotencyEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class IdempotencyMapper {

    public IdempotencyEntity toEntity(final String idempotencyKey,
                                      final UUID transferId) {
        IdempotencyEntity entity = new IdempotencyEntity();

        entity.setIdempotencyKey(idempotencyKey);
        entity.setTransferId(transferId);

        return entity;
    }

    public Optional<UUID> toTransferId(final IdempotencyEntity entity) {

        return (entity != null) ? Optional.of(entity.getTransferId()) : Optional.empty();
    }

}
