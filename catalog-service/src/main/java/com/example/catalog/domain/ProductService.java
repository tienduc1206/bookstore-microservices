package com.example.catalog.domain;

import com.example.catalog.ApplicationProperties;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ApplicationProperties properties;

    ProductService(ProductRepository productRepository, ApplicationProperties properties) {
        this.productRepository = productRepository;
        this.properties = properties;
    }

    public PagedResult<Product> getProducts(int page) {
        page = page < 1 ? 0 : page - 1; // Convert to zero-based index
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, properties.pageSize(), sort);
        var productsPage = productRepository.findAll(pageable).map(ProductMapper::toProduct);

        PagedResult<Product> pagedResult = new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious());

        return pagedResult;
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code).map(ProductMapper::toProduct);
    }
}
