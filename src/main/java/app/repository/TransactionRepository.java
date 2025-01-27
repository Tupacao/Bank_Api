package app.repository;

import app.model.Transaction;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
