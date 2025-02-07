package app.service;

import app.dto.mapper.TransactionMapper;
import app.dto.request.TransactionDTO;
import app.kafka.producer.TransactionProducerService;
import app.exception.BankAccountException;
import app.exception.TransactionException;
import app.model.Log;
import app.model.Transaction;
import app.shared.Status;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import app.proto.TransactionProto;

import java.util.Date;

@Singleton
public class LogTransactionService {

    @Inject
    private LogService logService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private TransactionProducerService transactionProducerService;

    @Inject
    private BankAccountService bankAccountService;

    public Transaction createLogTransaction(TransactionDTO transactionDTO, Long origin_id, Long destination_id) {
        Transaction transaction = TransactionMapper.INSTANCE.toEntity(transactionDTO);
        transaction.setDate(new Date());
        transaction.setTransactionStatus(Status.WAITING);
        transaction.setOriginAccount(bankAccountService.getBankAccount(origin_id));
        transaction.setDestinationAccount(bankAccountService.getBankAccount(destination_id));

        publishLog(transaction, Status.WAITING);

        transaction = transactionService.createTransaction(transaction);

        TransactionProto.Transaction transactionProto = TransactionProto.Transaction.newBuilder()
                .setTransactionId(transaction.getId())
                .build();

        transactionProducerService.sendTransaction(String.valueOf(transaction.getTransactionStatus()), transactionProto);

        return transaction;
    }

    public String processTransaction(Transaction transaction) {
        return switch (transaction.getTransactionType()) {
            case WITHDRAW -> withdrawTransaction(transaction);
            case DEPOSIT -> depositTransaction(transaction);
            case TRANSFER -> transferTransaction(transaction);
            default -> "Unexpected error in transaction";
        };
    }

    public void deleteLogTransaction(Transaction transaction, boolean var) {
        transactionService.deleteTransaction(transaction.getId());
        publishLog(transaction, var ? Status.SUCCESS : Status.FAILED);
    }

    private String withdrawTransaction(Transaction transaction) {
        publishLog(transaction, Status.PENDING);

        if (transaction.getOriginAccount().getId() != transaction.getDestinationAccount().getId()) {
            return errorTransaction(transaction, "Origin and destination account must be the same");
        }

        transaction.setTransactionStatus(Status.PENDING);

        double amount = transaction.getAmount();

        if (transaction.getOriginAccount().getBalance() >= amount) {
            bankAccountService.withdraw(transaction.getOriginAccount(), amount);
            transaction.setTransactionStatus(Status.SUCCESS);
            transactionService.updateTransaction(transaction, transaction.getId());
            deleteLogTransaction(transaction, true);
            return "Transferência realizada com sucesso";
        } else {
            deleteLogTransaction(transaction, false);
            return errorTransaction(transaction, "Insufficient funds");
        }
    }

    private String depositTransaction(Transaction transaction) {
        publishLog(transaction, Status.PENDING);

        if (transaction.getOriginAccount().getId() != transaction.getDestinationAccount().getId()) {
            return errorTransaction(transaction, "Origin and destination account must be the same");
        }

        transaction.setTransactionStatus(Status.PENDING);

        double amount = transaction.getAmount();

        bankAccountService.deposit(transaction.getDestinationAccount(), amount);
        transaction.setTransactionStatus(Status.SUCCESS);
        transactionService.updateTransaction(transaction, transaction.getId());
        deleteLogTransaction(transaction, true);
        return "Transferência realizada com sucesso";
    }

    private String transferTransaction(Transaction transaction) {
        publishLog(transaction, Status.PENDING);

        if (transaction.getOriginAccount().getId() == transaction.getDestinationAccount().getId()) {
            return errorTransaction(transaction, "Origin and destination account must be different");
        }

        transaction.setTransactionStatus(Status.PENDING);

        double amount = transaction.getAmount();

        if (transaction.getOriginAccount().getBalance() >= amount) {
            bankAccountService.transfer(transaction.getOriginAccount(), transaction.getDestinationAccount(), amount);
            transaction.setTransactionStatus(Status.SUCCESS);
            transactionService.updateTransaction(transaction, transaction.getId());
            deleteLogTransaction(transaction, true);
            return "Transferência realizada com sucesso";
        } else {
            deleteLogTransaction(transaction, false);
            return errorTransaction(transaction, "Insufficient funds");
        }
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

    private String errorTransaction(Transaction transaction, String message) {
        publishLog(transaction, Status.FAILED);
        transaction.setTransactionStatus(Status.FAILED);
        transactionService.updateTransaction(transaction, transaction.getId());
        deleteLogTransaction(transaction, false);
        return "Error: " + message;
    }
}