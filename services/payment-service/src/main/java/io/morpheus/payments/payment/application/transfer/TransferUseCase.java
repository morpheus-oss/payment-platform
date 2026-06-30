package io.morpheus.payments.payment.application.transfer;

import io.morpheus.payments.payment.domain.transfer.TransferRequest;
import io.morpheus.payments.payment.domain.transfer.TransferResponse;


public interface TransferUseCase {

    TransferResponse execute(TransferRequest request);

}