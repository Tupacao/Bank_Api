package app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Setter
@Getter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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