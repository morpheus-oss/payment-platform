package io.morpheus.payments.payment.persistence.adapter;

import io.morpheus.payments.payment.application.port.out.IdempotencyPort;
import io.morpheus.payments.payment.persistence.entity.IdempotencyEntity;
import io.morpheus.payments.payment.persistence.mapper.IdempotencyMapper;
import io.morpheus.payments.payment.persistence.repository.IdempotencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class IdempotencyPersistenceAdapter implements IdempotencyPort {

    private final IdempotencyRepository repository;
    private final IdempotencyMapper mapper;

    @Override
    public Optional<UUID> findTransferId(final String idempotencyKey) {
        return mapper.toTransferId(repository.findById(idempotencyKey).orElse(null));
    }

    @Override
    public void save(final String idempotencyKey,
                     final UUID transferId) {

        IdempotencyEntity entity = mapper.toEntity(idempotencyKey, transferId);
        repository.save(entity);
    }
}
