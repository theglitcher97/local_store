package com.store.local_store.persistence.mapper;

import com.store.local_store.domain.common.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PageResultMapper {

    @Mapping(source = "content",
            target = "items",
            defaultExpression = "java(new java.util.ArrayList<com.store.local_store.domain.common.PageResult>())")
    @Mapping(source = "number", target = "page")
    @Mapping(source = "size", target = "size")
    @Mapping(source = "totalElements", target = "totalItems")
    @Mapping(source = "totalPages", target = "totalPages")
    PageResult toPageResult(Page page);
}
