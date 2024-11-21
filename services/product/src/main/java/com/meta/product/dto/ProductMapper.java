package com.meta.product.dto;

import com.meta.product.model.Category;
import com.meta.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductMapper {


    public Product mapToProduct(ProductRequest productRequest) {
        AtomicReference<Product> product =
                new AtomicReference<>(Product.builder()
                        .id(productRequest.id())
                        .name(productRequest.name())
                        .price(productRequest.price())
                        .description(productRequest.description())
                        .category(Category.builder()
                                .id(productRequest.categoryId())
                                .build())
                        .build());

        return product.get();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(), product.getAvailableQuantity(), product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
