package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.model.User;
import com.store.local_store.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User entityToModel(UserEntity userEntity);

    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(User user);
}
