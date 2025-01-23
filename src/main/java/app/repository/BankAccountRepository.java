package app.repository;

import app.model.BankAccount;
import io.micronaut.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
