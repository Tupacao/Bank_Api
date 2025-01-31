package app.dto.mapper;

import app.dto.request.TransactionDTO;
import app.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDTO toDto(Transaction transition);

    Transaction toEntity(TransactionDTO transitionDto);
}
