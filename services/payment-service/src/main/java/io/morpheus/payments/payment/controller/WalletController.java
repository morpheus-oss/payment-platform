package io.morpheus.payments.payment.controller;

import io.morpheus.payments.payment.application.command.CreateWalletCommand;
import io.morpheus.payments.payment.application.mapper.CreateWalletMapper;
import io.morpheus.payments.payment.application.port.in.CreateWalletPort;
import io.morpheus.payments.payment.application.result.CreateWalletResult;
import io.morpheus.payments.payment.domain.wallet.Wallet;
import io.morpheus.payments.payment.model.response.WalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final CreateWalletPort createWalletPort;

    private final CreateWalletMapper createWalletMapper;

	@Operation(summary = "Create a wallet")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public WalletResponse create(@Valid @RequestBody Wallet request)    {
        CreateWalletCommand command =
            createWalletMapper.toCommand(request);

        CreateWalletResult result =
            createWalletPort.createWallet(command);

        WalletResponse response =
            createWalletMapper.toResponse(result);
		return response;
	}

	@Operation(summary = "Get wallet with {walletId}.")
	@GetMapping("/{walletId}")
	public WalletResponse get(@PathVariable UUID walletId)  {
		return createWalletPort.get(walletId);
	}
}
