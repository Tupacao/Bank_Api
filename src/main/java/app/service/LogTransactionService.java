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

    public Transaction createLogTransaction(TransactionDTO transactionDTO, Long id) {
        Date now = new Date();

        Transaction transaction = TransactionMapper.INSTANCE.toEntity(transactionDTO);
        transaction.setDate(now);
        transaction.setTransactionStatus(Status.WAITING);

        Log log = new Log();
        log.setDate(now);
        log.setLogStatus(Status.WAITING);
        log.setOriginAccountId(transactionDTO.getOriginAccount().getId());
        log.setDestinationAccountId(transactionDTO.getDestinationAccount().getId());

        transactionService.createTransaction(transaction);
        logService.createLogTransaction(log);

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

    private void withdrawTransaction(Transaction transaction) {
        Log log = updateLogTransaction(transaction);
        // TODO: Implementar metodo
    }

    private void depositTransaction(Transaction transaction) {
        Log log = updateLogTransaction(transaction);
        // TODO: Implementar metodo
    }

    private void transferTransaction(Transaction transaction) {
        Log log = updateLogTransaction(transaction);
        // TODO: Implementar metodo
    }

    private Log updateLogTransaction(Transaction transaction) {
        Log log = new Log();
        log.setDate(new Date());
        log.setLogStatus(Status.PENDING);
        log.setOriginAccountId(transaction.getOriginAccount().getId());
        log.setDestinationAccountId(transaction.getDestinationAccount().getId());
        return log;
    }
}