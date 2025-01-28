package app.model;

import io.micronaut.data.annotation.MappedEntity;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@MappedEntity
@Table(name = "log_transactions")
public class LogTransaction {
    @Id
    @BsonId
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id = UUID.randomUUID().toString();

    @NotNull(message = "Log Status is mandatory")
    @Enumerated(EnumType.STRING)
    private LogStatus logStatus;

    @NotNull(message = "Transaction is mandatory")
    private Transaction transaction;

    private enum LogStatus {
        SUCCESS,
        PENDING,
        FAILED
    }
}