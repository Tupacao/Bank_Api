package app.repository;

import app.model.Log;

import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.mongodb.annotation.MongoRepository;

@MongoRepository
public interface LogTransactionRepository extends JpaRepository<Log, String> {
}