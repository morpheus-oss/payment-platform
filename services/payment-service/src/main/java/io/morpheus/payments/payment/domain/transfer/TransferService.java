package io.morpheus.payments.payment.domain.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.common.exception.ResourceNotFoundException;
import io.morpheus.payments.events.envelope.EventTypes;
import io.morpheus.payments.events.types.MoneyTransferredEvent;
import io.morpheus.payments.payment.application.port.WalletPersistencePort;
import io.morpheus.payments.payment.domain.outbox.OutboxStatus;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.persistence.entity.IdempotencyEntity;
import io.morpheus.payments.payment.persistence.entity.OutboxEventEntity;
import io.morpheus.payments.payment.persistence.entity.TransferEntity;
import io.morpheus.payments.payment.persistence.repository.IdempotencyRepository;
import io.morpheus.payments.payment.persistence.repository.OutboxEventRepository;
import io.morpheus.payments.payment.persistence.repository.TransferRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import io.morpheus.payments.payment.model.request.TransferRequest;
import io.morpheus.payments.payment.model.response.TransferResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService
{

	private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

	private final WalletPersistencePort walletPersistencePort;

	private final TransferRepository transferRepository;
	private final OutboxEventRepository outboxRepository;
	private final IdempotencyRepository idempotencyRepository;

	private final ObjectMapper objectMapper;

	@Transactional
	public TransferResponse transfer(Wallet source, Wallet destination, TransferRequest request)
	{

		if (!source.hasSufficientFunds(request.amount()))
		{ throw new InsufficientFundsException("Insufficient funds"); }

		source.withdraw(request.amount());
		destination.deposit(request.amount());

		walletPersistencePort.save(source);
		walletPersistencePort.save(destination);

		TransferEntity transfer = createTransfer(request);

		transferRepository.save(transfer);

		try
		{
			createOutboxEvent(transfer);
		}
		catch (JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}

		saveIdempotencyRecord(idempotencyKey, transfer.getId());

		return new TransferResponse(transfer.getId());
	}

	public Wallet loadSourceWallet(TransferRequest request)
	{

		return walletPersistencePort.findById(request.sourceWalletId())
				.orElseThrow(() -> new ResourceNotFoundException("Source wallet not found"));
	}

	public Wallet loadDestinationWallet(TransferRequest request)
	{

		return walletPersistencePort.findById(request.destinationWalletId())
				.orElseThrow(() -> new ResourceNotFoundException("Destination wallet not found"));
	}

	public TransferResponse validateIdempotency(final TransferRequest request)
	{
		Optional<IdempotencyEntity> existing = idempotencyRepository.findByIdempotencyKey(idempotencyKey);

		return existing.map(entity -> new TransferResponse(entity.getTransferId())).orElse(null);
	}

	private void saveIdempotencyRecord(String idempotencyKey, UUID transferId)
	{
		IdempotencyEntity idempotency = new IdempotencyEntity();
		idempotency.setId(UUID.randomUUID());
		idempotency.setIdempotencyKey(idempotencyKey);
		idempotency.setTransferId(transferId);

		idempotencyRepository.save(idempotency);
	}

	private TransferEntity createTransfer(TransferRequest request)
	{

		TransferEntity transfer = new TransferEntity();
		transfer.setId(UUID.randomUUID());
		transfer.setSourceWalletId(request.sourceWalletId());
		transfer.setDestinationWalletId(request.destinationWalletId());
		transfer.setAmount(request.amount());
		transfer.setStatus(TransferStatus.COMPLETED);

		return transfer;
	}

	private void createOutboxEvent(TransferEntity transfer) throws JsonProcessingException
	{

		MoneyTransferredEvent event = new MoneyTransferredEvent(transfer.getId(), transfer.getSourceWalletId(),
				transfer.getDestinationWalletId(), transfer.getAmount(), Instant.now());

		String payload = objectMapper.writeValueAsString(event);

		OutboxEventEntity outbox = new OutboxEventEntity();
		outbox.setId(UUID.randomUUID());
		outbox.setAggregateId(transfer.getId());
		outbox.setEventType(EventTypes.MONEY_TRANSFERRED);
		outbox.setPayload(payload);
		outbox.setStatus(OutboxStatus.PENDING);
		outbox.setRetryCount(0);

		outboxRepository.save(outbox);
	}
}
