package io.morpheus.payments.payment.domain.wallet;

import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper
{

	public WalletResponse toResponse(WalletEntity entity)
	{

		return new WalletResponse(entity.getId(), entity.getOwnerId(), entity.getBalance());
	}
}
