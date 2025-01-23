package app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@NoArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Account number is mandatory")
    private long accountNumber;

    @NotNull(message = "Balance is mandatory")
    private double balance;

    @NotBlank(message = "Acount Type is mandatory")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private enum AccountType {
        SAVINGS,
        CURRENT
    }
}
