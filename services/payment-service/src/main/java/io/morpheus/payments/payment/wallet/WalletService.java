package io.morpheus.payments.payment.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.morpheus.payments.common.exception.ResourceNotFoundException;
import io.morpheus.payments.payment.persistence.entity.WalletEntity;
import io.morpheus.payments.payment.persistence.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;

    private final WalletMapper walletMapper;

    @Transactional
    public WalletResponse create(WalletRequest request) {

        WalletEntity wallet = new WalletEntity();
        wallet.setId(UUID.randomUUID());
        wallet.setOwnerId(request.ownerId());
        wallet.setBalance(request.initialBalance());

        walletRepository.save(wallet);

        return walletMapper.toResponse(wallet);
    }

    @Transactional(readOnly = true)
    public WalletResponse get(UUID walletId) {
        return walletMapper.toResponse(
                    walletRepository.findById(walletId)
                                    .orElseThrow(
                                        () -> new ResourceNotFoundException("Wallet not found")
                                    ));

    }
}