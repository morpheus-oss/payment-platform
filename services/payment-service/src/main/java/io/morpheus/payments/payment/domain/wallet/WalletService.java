package io.morpheus.payments.payment.domain.wallet;

import io.morpheus.payments.common.exception.ResourceNotFoundException;
import io.morpheus.payments.payment.mapper.WalletMapper;
import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import io.morpheus.payments.payment.persistence.repository.WalletRepository;
import java.util.UUID;

import io.morpheus.payments.payment.model.response.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Deprecated(forRemoval = true)
public class WalletService
{

	private final WalletRepository walletRepository;

	private final WalletMapper walletMapper;

	@Transactional
	public WalletResponse create(Wallet request)
	{

		WalletEntity wallet = new WalletEntity();
		wallet.setId(UUID.randomUUID());
		wallet.setOwnerId(request.ownerId());
		wallet.setBalance(request.initialBalance());

		walletRepository.save(wallet);

		return walletMapper.toResponse(wallet);
	}

	@Transactional(readOnly = true)
	public WalletResponse get(UUID walletId)
	{
		return walletMapper.toResponse(
				walletRepository.findById(walletId).orElseThrow(() -> new ResourceNotFoundException("Wallet not found")));
	}
}
