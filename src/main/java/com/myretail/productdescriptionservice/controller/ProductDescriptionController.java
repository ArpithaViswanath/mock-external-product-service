package com.myretail.productdescriptionservice.controller;

import com.myretail.productdescriptionservice.models.ProductDescription;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.myretail.productdescriptionservice.constants.ProductDescriptionServiceConstants.*;

@RestController
@Slf4j
@RequestMapping("/v1/productdescription")
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

        log.info("Received the request to fetch all Product Descriptions");

        return new ResponseEntity<>(productDescMap.entrySet().stream()
                .map(entry -> new ProductDescription(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @RequestMapping(value = QUERY_PARAM_PRODUCT_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Fetches the product description for a given product ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful Retrieval of Product Description")
            }
    )
    public ResponseEntity<ProductDescription> getProduct(@PathVariable(PRODUCT_ID) String productId) {

        log.info("Received the request to fetch the Product Description for product ID {}!.", productId);

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

        log.info("Received the request to create the Product Description for a product ID {}!", productDescription.getId());

        String newProductId = productDescription.getId();
        String newProductName = productDescription.getName();
        productDescMap.put(newProductId, newProductName);
        return new ResponseEntity<>(new ProductDescription(newProductId, productDescMap.get(newProductId)),
                HttpStatus.OK);
    }

    @RequestMapping(value = QUERY_PARAM_PRODUCT_ID,
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Updates the product description for a given product ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful update of Product Description")
            }
    )
    public ResponseEntity<ProductDescription> updateProduct(@PathVariable(PRODUCT_ID) String productId,
                                                            @RequestBody String productName) {

        log.info("Received the request to update the Product Description for product ID {}!.", productId);

        if (productDescMap.containsKey(productId)) {
            productDescMap.put(productId, productName);
            return new ResponseEntity<>(new ProductDescription(productId, productDescMap.get(productId)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = QUERY_PARAM_PRODUCT_ID,
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Deletes the product description for a given product ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful deletion of Product Description"),
                    @ApiResponse(code = 404, message = "Product ID not found for deletion")
            }
    )
    public ResponseEntity<?> deleteProduct(@PathVariable(PRODUCT_ID) String productId) {

        log.info("Received the request to delete the Product Description for product ID {}!", productId);

        if (productDescMap.containsKey(productId)) {
            productDescMap.remove(productId);
            return new ResponseEntity<>(productDescMap.entrySet().stream()
                    .map(entry -> new ProductDescription(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList()), HttpStatus.OK);
        } else {
            log.info("No product id {} found!", productId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
