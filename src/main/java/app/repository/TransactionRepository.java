package app.repository;

import app.model.Transaction;
import io.micronaut.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
