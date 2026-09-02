package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.Payment;
import com.store.local_store.persistence.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface PaymentMapper {
    @Mapping(target = "order", ignore = true)
    PaymentEntity toEntity(Payment payment);

}
