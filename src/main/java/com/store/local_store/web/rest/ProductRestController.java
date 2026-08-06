package com.store.local_store.web.rest;

import com.store.local_store.application.use_cases.ProductUseCases;
import com.store.local_store.web.dtos.NewProductDTO;
import com.store.local_store.web.dtos.ProductDTO;
import com.store.local_store.web.enums.SORT_DIR;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> listProducts(
            @PathParam("page") Integer page,
            @PathParam("size") Integer size,
            @PathParam("sortBy") String sortBy,
            @PathParam("sortDir") SORT_DIR sortDir) {
        if (page == null || page < 0) page = 0;
        if (size == null || size <= 0) size = 10;
        if (sortBy == null || sortBy.isBlank()) sortBy = "name";
        if (sortDir == null) sortDir = SORT_DIR.ASC;

        Page<ProductDTO> products = this.productUseCases.listProducts(page, size, sortBy, sortDir);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
