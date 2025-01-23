package app.model;

import io.micronaut.data.annotation.MappedEntity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import org.bson.codecs.pojo.annotations.BsonId;

@MappedEntity
@Getter
@Setter
@Table(name = "log_transactions")
public class LogTransaction {
    @Id
    @BsonId
    private long id;

    @NotNull(message = "Transaction is mandatory")
    private Transaction transaction;

    @NotNull(message = "Log Status is mandatory")
    @Enumerated(EnumType.STRING)
    private LogStatus logStatus;

    private enum LogStatus {
        SUCCESS,
        PENDING,
        FAILED
    }
}