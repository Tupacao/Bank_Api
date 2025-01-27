package app.service;

import app.exception.LogTransactionException;
import app.model.LogTransaction;
import app.repository.LogTransactionRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class LogTransactionService {

    @Inject
    private LogTransactionRepository logTransactionRepository;

    public LogTransaction createLogTransaction (LogTransaction logTransaction){
        return logTransactionRepository.save(logTransaction);
    }

    public void deleteLogTransaction (Long id){
        if(logTransactionRepository.existsById(id)){
            logTransactionRepository.deleteById(id);
        }
        throw new LogTransactionException.LogTransactionNotFoundException("Log Transaction not found");
    }

    public LogTransaction updateLogTransaction (LogTransaction logTransaction, Long id){
        if(logTransactionRepository.existsById(id)){
            logTransaction.setId(id);
            return logTransactionRepository.update(logTransaction);
        }
        throw new LogTransactionException.LogTransactionNotFoundException("Log Transaction not found");
    }

    public LogTransaction getLogTransaction (Long id){
        return logTransactionRepository.findById(id).orElseThrow(() -> new LogTransactionException.LogTransactionNotFoundException("Log Transaction not found"));
    }

    public List<LogTransaction> getAllLogTransactions (){
        return logTransactionRepository.findAll();
    }
}