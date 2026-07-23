package io.morpheus.payments.payment.persistence.mapper;

import io.morpheus.payments.payment.domain.shared.Money;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.domain.wallet.WalletId;
import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import io.morpheus.payments.payment.model.response.WalletResponse;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class WalletMapper {

    public WalletResponse toResponse(WalletEntity entity) {

        return new WalletResponse(entity.getId(),
                                  entity.getOwnerId(),
                                  entity.getCurrencyCode(),
                                  entity.getBalance());
    }

    public WalletEntity toEntity(Wallet wallet) {

        WalletEntity entity = new WalletEntity();

        entity.setOwnerId(wallet.ownerId());
        entity.setCurrencyCode(wallet.balance().currency().getCurrencyCode());
        entity.setBalance(wallet.balance().amount());

        return entity;
    }

    public Wallet toDomain(WalletEntity persisted) {

        return Wallet.from(
                    WalletId.from(persisted.getId()),
                    persisted.getOwnerId(),
                    Money.of(
                        persisted.getBalance(),
                        Currency.getInstance(persisted.getCurrencyCode())
                    )
                );
    }
}
