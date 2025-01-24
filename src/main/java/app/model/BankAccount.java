package app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private Long id;

    @NotNull(message = "Account number is mandatory")
    private long accountNumber;

    @NotNull(message = "Balance is mandatory")
    private double balance;

    @NotBlank(message = "Acount Type is mandatory")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne
    private Client client;

    private enum AccountType {
        SAVINGS,
        CURRENT
    }
}
