package app.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Temporal;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TemporalType;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Date is mandatory")
    private Date date;

    @NotNull(message = "Amount is mandatory")
    private double amount;

    @NotNull(message = "Transaction type is mandatory")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull(message = "Transaction Status is mandatory")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @NotNull(message = "Origin Bank Account is mandatory")
    @ManyToOne
    private BankAccount originAccount;

    @NotNull(message = "Destination Bank Account is mandatory")
    @ManyToOne
    private BankAccount destinationAccount;

    private enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    }

    private enum TransactionStatus {
        SUCCESS,
        PENDING,
        FAILED
    }
}