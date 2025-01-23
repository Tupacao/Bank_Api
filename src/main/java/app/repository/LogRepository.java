package app.repository;

import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.mongodb.annotation.MongoRepository;

@MongoRepository
public interface LogRepository extends JpaRepository<Log, Long> {
}