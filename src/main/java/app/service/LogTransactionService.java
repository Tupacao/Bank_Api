package app.service;

import app.dto.mapper.TransactionMapper;
import app.dto.request.TransactionDTO;
import app.kafka.producer.TransactionProducerService;
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

    public Transaction createLogTransaction(TransactionDTO transactionDTO, Long id) {
        Transaction transaction = TransactionMapper.INSTANCE.toEntity(transactionDTO);
        transaction.setDate( new Date());
        transaction.setTransactionStatus(Status.WAITING);

        Log log = newLog(transaction, Status.WAITING);

        transactionService.createTransaction(transaction);
        logService.createLogTransaction(log);

        // TODO: Fazer a parte de send kafka bem aqui, ai em cima ele tá criando a transação,o log e salvando no bd
        TransactionProto.Transaction.Builder transactionBuilder = TransactionProto.Transaction.newBuilder();
        transactionBuilder.setTransactionId(transaction.getId());
        TransactionProto.Transaction transactionProto = transactionBuilder.build();

        transactionProducerService.sendTransaction(String.valueOf(transaction.getTransactionStatus()), transactionProto);

        return transaction;
    }

    public String processTransaction(Transaction transaction) {
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
        //tem que retornar alguma string para ser usada no stream e enviar uma mensagem para o segundo topico
        return "teste";
    }

    public void deleteLogTransaction(Transaction transaction, boolean var) {
        // TODO: cria o log e deleta a transação, isso que o ultimo consumer vai chamar, altere a vontade
        transactionService.deleteTransaction(transaction.getId());
        Log log = newLog(transaction, var ? Status.SUCCESS : Status.FAILED);
        logService.createLogTransaction(log);
    }

    private void withdrawTransaction(Transaction transaction) {

        Log log = newLog(transaction, Status.PENDING);
        logService.createLogTransaction(log);

        transaction.setTransactionStatus(Status.PENDING);
        // TODO: Implementar metodo eu

        // TODO: Usa o kafka pra mandar se deu sucesso ou falha (String)
    }

    private void depositTransaction(Transaction transaction) {

        Log log =  newLog(transaction, Status.PENDING);
        logService.createLogTransaction(log);

        transaction.setTransactionStatus(Status.PENDING);
        // TODO: Implementar metodo eu

        // TODO: Usa o kafka pra mandar se deu sucesso ou falha
    }

    private void transferTransaction(Transaction transaction) {

        Log log =  newLog(transaction, Status.PENDING);
        logService.createLogTransaction(log);

        transaction.setTransactionStatus(Status.PENDING);
        // TODO: Implementar metodo eu

        // TODO: Usa o kafka pra mandar se deu sucesso ou falha
    }

    private Log newLog(Transaction transaction, Status status) {
        Log log = new Log();
        log.setDate(new Date());
        log.setLogStatus(status);
        log.setOriginAccountId(transaction.getOriginAccount().getId());
        log.setDestinationAccountId(transaction.getDestinationAccount().getId());
        return log;
    }
}