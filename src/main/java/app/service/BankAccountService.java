package app.service;

import app.exception.BankAccountException;
import app.model.BankAccount;
import app.repository.BankAccountRepository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class BankAccountService {

    @Inject
    private BankAccountRepository bankAccountRepository;

    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccount(Long id) {
        if (bankAccountRepository.existsById(id)) {
            bankAccountRepository.deleteById(id);
        } else {
            throw new BankAccountException.BankAccountNotFoundException("Bank Account not found");
        }
    }

    public BankAccount updateBankAccount(BankAccount bankAccount, Long id) {
        if (bankAccountRepository.existsById(id)) {
            bankAccount.setId(id);
            return bankAccountRepository.update(bankAccount);
        }
        throw new BankAccountException.BankAccountNotFoundException("Bank Account not found");
    }

    public BankAccount getBankAccount(Long id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> new BankAccountException.BankAccountNotFoundException("Bank Account not found"));
    }

    public void withdraw(BankAccount bankAccount, double amount) {
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.update(bankAccount);
    }

    public void deposit(Long id, double amount) {
        BankAccount bankAccount = getBankAccount(id);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.update(bankAccount);
    }

    public void transfer(Long id_origin, Long id_destination, double amount) {
        BankAccount origin = getBankAccount(id_origin);
        BankAccount destination = getBankAccount(id_destination);
        origin.setBalance(origin.getBalance() - amount);
        destination.setBalance(destination.getBalance() + amount);
        bankAccountRepository.update(origin);
        bankAccountRepository.update(destination);
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public List<BankAccount> getClientBankAccounts(Long clientId) {
        return bankAccountRepository.findByClientId(clientId);
    }
}