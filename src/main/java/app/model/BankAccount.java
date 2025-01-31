package app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(message = "Account number is mandatory")
    private long accountNumber;

    @NotNull(message = "Balance is mandatory")
    private double balance;

    @NotNull(message = "Account Type is mandatory")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    @JsonBackReference
    private Client client;

    private enum AccountType {
        SAVINGS,
        CURRENT
    }
}