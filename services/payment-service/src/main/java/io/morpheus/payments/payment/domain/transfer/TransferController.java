package io.morpheus.payments.payment.domain.transfer;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.morpheus.payments.payment.application.transfer.TransferUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferUseCase transferUseCase;

    @Operation (summary = "Transfer money between wallets")
    @PostMapping
    public TransferResponse transfer(
                            @RequestHeader("Idempotency-Key")
                            String idempotencyKey,
                            @Valid
                            @RequestBody
                            TransferRequest request) throws JsonProcessingException {
//        return transferService.transfer(idempotencyKey, request);
        return transferUseCase.execute(request);
    }
}
