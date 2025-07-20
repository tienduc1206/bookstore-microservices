package com.example.catalog.web.controllers;

import com.example.catalog.domain.PagedResult;
import com.example.catalog.domain.Product;
import com.example.catalog.domain.ProductNotFoundException;
import com.example.catalog.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "0") int page) {
        return productService.getProducts(page);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Product> getMethodName(@PathVariable String code) {
        return ResponseEntity.ok(
                productService.getProductByCode(code).orElseThrow(() -> ProductNotFoundException.forCode(code)));
    }
}
