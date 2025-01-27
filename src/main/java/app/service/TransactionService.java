package app.service;

import app.exception.TransactionException;
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
        throw new TransactionException.TransactionNotFoundException("Transaction not found");
    }

    public Transaction updateTransaction (Transaction transaction, Long id){
        if(transactionRepository.existsById(id)){
            transaction.setId(id);
            return transactionRepository.update(transaction);
        }
        throw new TransactionException.TransactionNotFoundException("Transaction not found");
    }

    public Transaction getTransaction (Long id){
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionException.TransactionNotFoundException("Transaction not found"));
    }

    public List<Transaction> getAllTransactions (){
        return transactionRepository.findAll();
    }

}