package com.myretail.productdescriptionservice.controller;

import com.myretail.productdescriptionservice.models.ProductDescription;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/productdescription")
public class ProductDescriptionController {

    @RequestMapping("/{productId}")
    public ProductDescription getProduct(@PathVariable("productId") String productId) {

        Map<String, String> productDescMap = new HashMap<String, String>();
        productDescMap.put("1", "IPhone 13");
        productDescMap.put("2", "Organic Jaggery");
        productDescMap.put("3", "Diapers");
        productDescMap.put("4", "Sugarcane Juice");

        return new ProductDescription(productId, productDescMap.get(productId));
    }
}
