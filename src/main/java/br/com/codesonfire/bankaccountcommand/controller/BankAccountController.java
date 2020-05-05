package br.com.codesonfire.bankaccountcommand.controller;

import br.com.codesonfire.bankaccountcommand.command.AddBankAccountCommand;
import br.com.codesonfire.bankaccountcommand.command.RemoveBankAccountCommand;
import br.com.codesonfire.bankaccountcommand.command.UpdateBalanceBankAccountCommand;
import br.com.codesonfire.bankaccountcommand.dto.BankAccountDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/bank-accounts")
public class BankAccountController {

    private CommandGateway commandGateway;

    @PostMapping
    public CompletableFuture<String> create(@RequestBody BankAccountDTO dto) {
        var command = new AddBankAccountCommand(UUID.randomUUID().toString(), dto.getName());
        log.info("Executing command: {}", command);
        return commandGateway.send(command);
    }

    @PutMapping("/{id}/balances")
    public CompletableFuture<String> updateBalance(@PathVariable String id, @RequestBody BankAccountDTO dto) {
        var command = new UpdateBalanceBankAccountCommand(id, dto.getBalance());
        log.info("Executing command: {}", command);
        return commandGateway.send(command);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<String> remove(@PathVariable String id) {
        var command = new RemoveBankAccountCommand(id);
        log.info("Executing command: {}", command);
        return commandGateway.send(command);
    }
}
