package app.service;

import app.exception.LogTransactionException;
import app.model.Log;
import app.repository.LogTransactionRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class LogService {

    @Inject
    private LogTransactionRepository logTransactionRepository;

    public Log createLogTransaction(Log logTransaction) {
        return logTransactionRepository.save(logTransaction);
    }

    public void deleteLogTransaction(String id) {
        if (logTransactionRepository.existsById(id)) {
            logTransactionRepository.deleteById(id);
        } else {
            throw new LogTransactionException.LogTransactionNotFoundException("Log Transaction not found");

        }
    }

    public Log updateLogTransaction(Log logTransaction, String id) {
        if (logTransactionRepository.existsById(id)) {
            logTransaction.setId(id);
            return logTransactionRepository.update(logTransaction);
        }
        throw new LogTransactionException.LogTransactionNotFoundException("Log Transaction not found");
    }

    public Log getLogTransaction(String id) {
        return logTransactionRepository.findById(id).orElseThrow(() -> new LogTransactionException.LogTransactionNotFoundException("Log Transaction not found"));
    }

    public List<Log> getAllLogTransactions() {
        return logTransactionRepository.findAll();
    }
}