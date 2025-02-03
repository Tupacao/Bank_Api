package app.model;

import app.shared.Status;
import app.shared.TransactionType;
import io.micronaut.data.annotation.MappedEntity;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@MappedEntity
@Table(name = "log_transactions")
public class Log {
    @Id
    @BsonId
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "Log Status is mandatory")
    @Enumerated(EnumType.STRING)
    private Status logStatus;

    @NotNull(message = "Log Type is mandatory")
    @Enumerated(EnumType.STRING)
    private TransactionType logTypeTransaction;

    @NotNull(message = "Origin Account is mandatory")
    private Long originAccountId;

    @NotNull(message = "Destination Account is mandatory")
    private Long destinationAccountId;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "Date is mandatory")
    private Date date;
}