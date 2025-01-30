package app.repository;

import app.model.LogTransaction;

import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.mongodb.annotation.MongoRepository;

@MongoRepository
public interface LogTransactionRepository extends JpaRepository<LogTransaction, String> {
}