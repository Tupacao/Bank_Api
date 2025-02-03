package app.service;

import app.dto.mapper.TransactionMapper;
import app.dto.request.TransactionDTO;
import app.model.Log;
import app.model.Transaction;
import app.shared.Status;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Date;

@Singleton
public class LogTransactionService {

    @Inject
    private LogService logService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private BankAccountService bankAccountService;

    public Transaction createLogTransaction(TransactionDTO transactionDTO, Long id) {
        Transaction transaction = TransactionMapper.INSTANCE.toEntity(transactionDTO);
        transaction.setDate( new Date());
        transaction.setTransactionStatus(Status.WAITING);

        publishLog(transaction, Status.WAITING);

        transactionService.createTransaction(transaction);

        // TODO: Fazer a parte de send kafka bem aqui, ai em cima ele tá criando a transação,o log e salvando no bd

        return transaction;
    }

    public void processTransaction(Transaction transaction) {
        switch (transaction.getTransactionType()) {
            case WITHDRAW:
                withdrawTransaction(transaction);
                break;
            case DEPOSIT:
                depositTransaction(transaction);
                break;
            case TRANSFER:
                transferTransaction(transaction);
                break;
        }
    }

    public void deleteLogTransaction(Transaction transaction, boolean var) {
        // TODO: cria o log e deleta a transação, isso que o ultimo consumer vai chamar, altere a vontade
        transactionService.deleteTransaction(transaction.getId());
        publishLog(transaction, var ? Status.SUCCESS : Status.FAILED);
    }

    private void withdrawTransaction(Transaction transaction) {
        publishLog(transaction, Status.PENDING);

        transaction.setTransactionStatus(Status.PENDING);

        double amount = transaction.getAmount();

        if(transaction.getOriginAccount().getBalance() >= amount) {
            bankAccountService.withdraw(transaction.getOriginAccount(), amount);
            transaction.setTransactionStatus(Status.SUCCESS);
            transactionService.updateTransaction(transaction, transaction.getId());
        } else {
            transaction.setTransactionStatus(Status.FAILED);
        }

        // TODO: Usa o kafka pra mandar se deu sucesso ou falha
    }

    private void depositTransaction(Transaction transaction) {
        publishLog(transaction, Status.PENDING);

        transaction.setTransactionStatus(Status.PENDING);

        double amount = transaction.getAmount();

        bankAccountService.deposit(transaction.getDestinationAccount().getId(), amount);
        transaction.setTransactionStatus(Status.SUCCESS);
        transactionService.updateTransaction(transaction, transaction.getId());

        // TODO: Usa o kafka pra mandar se deu sucesso ou falha
    }

    private void transferTransaction(Transaction transaction) {
        publishLog(transaction, Status.PENDING);

        transaction.setTransactionStatus(Status.PENDING);

        double amount = transaction.getAmount();

        if(transaction.getOriginAccount().getBalance() >= amount) {
            bankAccountService.transfer(transaction.getOriginAccount().getId(), transaction.getDestinationAccount().getId(), amount);
            transaction.setTransactionStatus(Status.SUCCESS);
            transactionService.updateTransaction(transaction, transaction.getId());
        } else {
            transaction.setTransactionStatus(Status.FAILED);
        }

        // TODO: Usa o kafka pra mandar se deu sucesso ou falha

    }

    private void publishLog(Transaction transaction, Status status) {
        Log log = new Log();
        log.setDate(new Date());
        log.setLogStatus(status);
        log.setOriginAccountId(transaction.getOriginAccount().getId());
        log.setDestinationAccountId(transaction.getDestinationAccount().getId());
        log.setLogTypeTransaction(transaction.getTransactionType());
        logService.createLogTransaction(log);
    }
}