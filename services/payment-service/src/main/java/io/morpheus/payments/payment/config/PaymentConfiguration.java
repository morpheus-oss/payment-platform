package io.morpheus.payments.payment.config;


import io.morpheus.payments.payment.application.port.WalletPersistencePort;
import io.morpheus.payments.payment.application.port.in.CreateWalletPort;
import io.morpheus.payments.payment.application.port.out.WalletRepositoryPort;
import io.morpheus.payments.payment.application.usecase.DefaultCreateWalletUseCase;
import io.morpheus.payments.payment.mapper.WalletMapper;
import io.morpheus.payments.payment.persistence.WalletPersistenceAdapter;
import io.morpheus.payments.payment.persistence.WalletRepositoryAdapter;
import io.morpheus.payments.payment.persistence.repository.WalletRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    CreateWalletPort createWalletPort(WalletPersistencePort walletPersistencePort) {
        return new DefaultCreateWalletUseCase(walletPersistencePort);
    }

    @Bean
    WalletRepositoryPort walletRepositoryPort(WalletRepository walletRepository, WalletMapper walletMapper) {
        return new WalletRepositoryAdapter(walletRepository, walletMapper);
    }

    @Bean
    WalletPersistencePort walletPersistencePort(WalletRepository walletRepository, WalletMapper walletMapper) {
        return new WalletPersistenceAdapter(walletRepository, walletMapper);
    }
}
