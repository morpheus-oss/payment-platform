package io.morpheus.payments.payment.controller;

import io.morpheus.payments.payment.application.port.in.CreateWalletPort;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.model.response.WalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final CreateWalletPort createWalletPort;

	@Operation(summary = "Create a wallet")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CreateWalletResult create(@Valid @RequestBody Wallet request)    {
		return walletService.create(request);
	}

	@Operation(summary = "Get wallet with {walletId}.")
	@GetMapping("/{walletId}")
	public WalletResponse get(@PathVariable UUID walletId)  {
		return walletService.get(walletId);
	}
}
