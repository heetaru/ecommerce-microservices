package org.example.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.example.catalogservice.dto.ProductRequestDto;
import org.example.catalogservice.dto.ProductResponseDto;
import org.example.catalogservice.entity.Product;
import org.example.catalogservice.mapper.ProductMapper;
import org.example.catalogservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    final ProductRepository productRepository;
    final ProductMapper productMapper;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto){
        Product newProduct = productMapper.toEntity(productRequestDto);
        Product savedProduct = productRepository.save(newProduct);
        return productMapper.toDto(savedProduct);
    }
    public List<ProductResponseDto> findAllProducts(){
        List <Product> allProducts = productRepository.findAll();
        return allProducts.stream()
                .map(productMapper::toDto)
                .toList();
    }
    public ProductResponseDto findProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return productMapper.toDto(product);
    }


}
