package io.morpheus.payments.payment.mapper;

import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import io.morpheus.payments.payment.model.response.WalletResponse;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletResponse toResponse(WalletEntity entity) {

        return new WalletResponse(entity.getId(), entity.getOwnerId(), entity.getBalance());
    }
}
