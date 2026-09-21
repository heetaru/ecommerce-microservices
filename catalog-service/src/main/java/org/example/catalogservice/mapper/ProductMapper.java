package org.example.catalogservice.mapper;

import org.example.catalogservice.dto.ProductRequestDto;
import org.example.catalogservice.dto.ProductResponseDto;
import org.example.catalogservice.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDto dto);
    ProductResponseDto toDto(Product product);
}
