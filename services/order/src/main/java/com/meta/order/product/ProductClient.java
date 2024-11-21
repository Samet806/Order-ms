package com.meta.order.product;

import com.meta.order.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    @Value("${application.config.product-url}")
    private String productUrl;

    public List<ProductPurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequests) {
        // 1. HttpHeaders oluşturma
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        // 2. Request Body oluşturma
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(purchaseRequests, headers);

        ParameterizedTypeReference<List<ProductPurchaseResponse>> responseType=
                new ParameterizedTypeReference<List<ProductPurchaseResponse>>() {};

        // 3. POST isteği gönderme
        ResponseEntity<List<ProductPurchaseResponse>> response = restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        if(response.getStatusCode().isError()){
            throw new BusinessException("An Error occured while processing the product");
        }
        // 4. Yanıtın dönüşümü ve döndürülmesi
        return  response.getBody();
    }
}
