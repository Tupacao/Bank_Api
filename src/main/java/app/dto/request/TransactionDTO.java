package app.dto.request;

import app.model.BankAccount;

import app.shared.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    @NotNull(message = "Amount is mandatory")
    private double amount;

    @NotNull(message = "Transaction type is mandatory")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull(message = "Origin Bank Account is mandatory")
    private BankAccount originAccount;

    private BankAccount destinationAccount;
}
