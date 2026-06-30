package io.morpheus.payments.payment.persistence.entity;

import io.morpheus.payments.payment.domain.transfer.TransferStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transfers")
@Getter
@Setter
@NoArgsConstructor
public class TransferEntity {

    @Id
    private UUID id;

    private UUID sourceWalletId;

    private UUID destinationWalletId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransferStatus status;

    private Instant createdAt;
}