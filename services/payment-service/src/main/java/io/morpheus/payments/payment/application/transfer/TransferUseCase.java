package io.morpheus.payments.payment.application.transfer;

import io.morpheus.payments.payment.model.request.TransferRequest;
import io.morpheus.payments.payment.model.response.TransferResponse;

public interface TransferUseCase {

    TransferResponse execute(TransferRequest request);
}
