package org.example.catalogservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.catalogservice.dto.ProductRequestDto;
import org.example.catalogservice.dto.ProductResponseDto;
import org.example.catalogservice.entity.Product;
import org.example.catalogservice.repository.ProductRepository;
import org.example.catalogservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductResponseDto addProduct(@Valid @RequestBody ProductRequestDto productRequestDto){
        return productService.addProduct(productRequestDto);
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDto findProductById(@Valid @PathVariable Long id){
        return productService.findProductById(id);
    }
}
