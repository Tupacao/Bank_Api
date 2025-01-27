package app.service;

import app.exception.LogTransactionException;
import app.model.Transaction;
import app.repository.TransactionRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class TransactionService {

    @Inject
    private TransactionRepository transactionRepository;

    public Transaction createTransaction (Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction (Long id){
        if(transactionRepository.existsById(id)){
            transactionRepository.deleteById(id);
        }
        throw new LogTransactionException.LogTransactionNotFoundException("Transaction not found");
    }

    public Transaction updateTransaction (Transaction transaction){
        if(transactionRepository.existsById(transaction.getId())){
            return transactionRepository.update(transaction);
        }
        throw new LogTransactionException.LogTransactionNotFoundException("Transaction not found");
    }

    public Transaction getTransaction (Long id){
        return transactionRepository.findById(id).orElseThrow(() -> new LogTransactionException.LogTransactionNotFoundException("Transaction not found"));
    }

    public List<Transaction> getAllTransactions (){
        return transactionRepository.findAll();
    }

}