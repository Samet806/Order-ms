package com.meta.product.service;

import com.meta.product.dto.*;
import com.meta.product.exception.ProductNotFoundException;
import com.meta.product.exception.ProductPuchaseException;
import com.meta.product.model.Product;
import com.meta.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public Integer createProduct(ProductRequest productRequest) {
        Product product = productMapper.mapToProduct(productRequest);
        return productRepository.save(product).getId();

    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequests) {
        var productIds = productPurchaseRequests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPuchaseException("One or more products does not exists");
        }

        var storedRequest = productPurchaseRequests
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchasedProduct = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequest.get(i);

            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPuchaseException("InSufficiant stock quantity for product with ıd");
            }

            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProduct.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProduct;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId).map(productMapper::toProductResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with the ID::" + productId));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse).collect(Collectors.toList());
    }
}
