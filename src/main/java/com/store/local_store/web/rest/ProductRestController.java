package com.store.local_store.web.rest;

import com.store.local_store.application.model.CreateProductCommand;
import com.store.local_store.application.model.UpdateProductCommand;
import com.store.local_store.application.use_cases.ProductUseCases;
import com.store.local_store.domain.common.PageResult;
import com.store.local_store.web.dtos.CreateProductRequest;
import com.store.local_store.web.dtos.ProductDTO;
import com.store.local_store.web.dtos.UpdateProductRequest;
import com.store.local_store.web.enums.SORT_DIR;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/v1/products")
@AllArgsConstructor
public class ProductRestController {
    private ProductUseCases productUseCases;

    @PostMapping
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody CreateProductRequest product) {
        // validate incoming data
        if (product.name() == null || product.name().isBlank() ||
                product.price() == null || product.price().compareTo(BigDecimal.ZERO) <= 0 ||
                product.quantity() == null || product.quantity() <= 0 ||
                product.categoryId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // return object with error information

        // create product command
        CreateProductCommand createProductCommand =  new CreateProductCommand(
                product.name(),
                product.price(),
                product.quantity(),
                product.categoryId());

        // create product
        ProductDTO productDTO = this.productUseCases.createProduct(createProductCommand);

        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageResult<ProductDTO>> listProducts(
            @PathParam("page") Integer page,
            @PathParam("size") Integer size,
            @PathParam("sortBy") String sortBy,
            @PathParam("sortDir") SORT_DIR sortDir) {
        // should move this to use case ?
        if (page == null || page < 0) page = 0;
        if (size == null || size <= 0) size = 10;
        if (sortBy == null || sortBy.isBlank()) sortBy = "name";
        if (sortDir == null) sortDir = SORT_DIR.ASC;

        PageResult<ProductDTO> products = this.productUseCases.listProducts(page, size, sortBy, sortDir);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest product) {
        // validate incoming data
        if ((product.name() != null && product.name().isBlank()) ||
                (product.price() != null && product.price().compareTo(BigDecimal.ZERO) <= 0) ||
                (product.quantity() != null && product.quantity() <= 0))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // create product command
        UpdateProductCommand createProductCommand =  new UpdateProductCommand(id, product.name(), product.price(), product.quantity());

        // update product
        ProductDTO productDTO = this.productUseCases.updateProduct(createProductCommand);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}
