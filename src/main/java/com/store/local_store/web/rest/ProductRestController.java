package com.store.local_store.web.rest;

import com.store.local_store.application.use_cases.ProductUseCases;
import com.store.local_store.web.dtos.NewProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/v1/products")
@AllArgsConstructor
public class ProductRestController {
    private ProductUseCases productUseCases;

    @PostMapping
    public ResponseEntity<NewProductDTO> saveProduct(@RequestBody NewProductDTO product) {
        // validate incoming data
        if (product.name() == null || product.name().isBlank() ||
                product.price() == null || product.price() <= 0.0 ||
                product.categoryId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        // create product
        Long id = this.productUseCases.createProduct(product.name(), product.price(), product.categoryId());

        // return response
        return new ResponseEntity<>(new NewProductDTO(id, product.name(), product.price(), product.categoryId()), HttpStatus.CREATED);
    }
}
