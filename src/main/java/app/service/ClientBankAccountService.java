package app.service;

import app.model.BankAccount;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class ClientBankAccountService {

    @Inject
    private BankAccountService bankAccountService;

    @Inject
    private ClientService clientService;

    public BankAccount createClientBankAccount(Long clientId, BankAccount bankAccount) {
        bankAccount.setClient(clientService.getClient(clientId));
        return bankAccountService.createBankAccount(bankAccount);
    }

    public List<BankAccount> getClientBankAccounts(Long clientId) {
        clientService.getClient(clientId);
        return bankAccountService.getClientBankAccounts(clientId);
    }

}