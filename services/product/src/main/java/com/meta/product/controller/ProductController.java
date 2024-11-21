package com.meta.product.controller;

import com.meta.product.dto.ProductPurchaseRequest;
import com.meta.product.dto.ProductPurchaseResponse;
import com.meta.product.dto.ProductRequest;
import com.meta.product.dto.ProductResponse;
import com.meta.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest productRequest ){
      return new ResponseEntity<>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> productPurchaseRequests)
    {
        return ResponseEntity.ok(productService.purchaseProducts(productPurchaseRequests));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Integer productId)
    {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll()
    {
        return ResponseEntity.ok(productService.findAll());
    }


}
