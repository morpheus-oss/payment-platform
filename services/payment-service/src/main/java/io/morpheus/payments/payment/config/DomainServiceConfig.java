package io.morpheus.payments.payment.config;

import io.morpheus.payments.payment.application.port.out.OutboxPublisherPort;
import io.morpheus.payments.payment.application.port.out.TransferPersistencePort;
import io.morpheus.payments.payment.application.port.out.WalletPersistencePort;
import io.morpheus.payments.payment.domain.transfer.TransferService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig    {

    @Bean
    TransferService transferService(final WalletPersistencePort walletPersistencePort,
                                    final TransferPersistencePort transferPersistencePort,
                                    final OutboxPublisherPort outboxPublisherPort) {

        return new TransferService(walletPersistencePort, transferPersistencePort, outboxPublisherPort);
    }

}
