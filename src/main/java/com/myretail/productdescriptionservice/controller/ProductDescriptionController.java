package com.myretail.productdescriptionservice.controller;

import com.myretail.productdescriptionservice.models.ProductDescription;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productdescription")
public class ProductDescriptionController {

    private static Map<String, String> productDescMap;

    @PostConstruct
    public void init() {
        productDescMap = new HashMap<>();
        productDescMap.put("1", "IPhone 13");
        productDescMap.put("2", "Organic Jaggery");
        productDescMap.put("3", "Diapers");
        productDescMap.put("4", "Sugarcane Juice");
    }

    @GetMapping
    @ApiOperation("Fetches all the product descriptions")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Retrieval of Product Description")
            }
    )
    public ResponseEntity<List<ProductDescription>> getAllProducts() {
        return new ResponseEntity<>(productDescMap.entrySet().stream()
                .map(entry -> new ProductDescription(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{productId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Fetches the product description for a given product ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Retrieval of Product Description")
            }
    )
    public ResponseEntity<ProductDescription> getProduct(@PathVariable("productId") String productId) {
        if (productDescMap.containsKey(productId)) {
            return new ResponseEntity<>(
                    new ProductDescription(productId, productDescMap.get(productId)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    @ApiOperation("Updates/Creates the product description for a given product ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful update of Product Description")
            }
    )
    public ResponseEntity<ProductDescription> addProduct(@RequestBody ProductDescription productDescription) {
        productDescMap.put(productDescription.getId(), productDescription.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
