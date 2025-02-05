package app.dto.request;

import app.shared.TransactionType;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Serdeable
@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    @NotNull(message = "Amount is mandatory")
    private double amount;

    @NotNull(message = "Transaction type is mandatory")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
