package com.store.local_store.domain.services;

import com.store.local_store.application.model.UpdateProductCommand;
import com.store.local_store.domain.common.PageResult;
import com.store.local_store.domain.model.Order;
import com.store.local_store.domain.model.OrderItem;
import com.store.local_store.domain.model.Product;
import com.store.local_store.domain.ports.repos.ProductRepository;
import com.store.local_store.web.enums.SORT_DIR;
import com.store.local_store.web.exceptions.custom.InsufficientStockException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Product create(Product product) {
        Long id = this.productRepository.create(product);
        product.setId(id);
        return product;
    }

    public PageResult<Product> listProducts(Integer page, Integer size, String sortBy, SORT_DIR sortDir) {
        return this.productRepository.list(page, size, sortBy, sortDir);
    }

    public Product findById(Long id) {
        return this.productRepository.findProduct(id);
    }

    public void update(Product product, UpdateProductCommand command) {
        if (command.name() != null) product.setName(command.name());
        if (command.price() != null) product.setPrice(command.price());
        if (command.quantity() != null) product.setAvailableStock(command.quantity());
        this.productRepository.save(product);
    }

    public void reserveProduct(Product product, Long quantity) {
        // update available stock and reserved stock
        Integer rowsAffected = this.productRepository.reserveProductStock(product.getId(), quantity);
        if (rowsAffected == 0)
            throw new InsufficientStockException(
                    "Not enough stock for product: "+ product.getName() + "; required quantity: "+quantity);
    }

    public void releaseReservation(Order order) {
        Integer rowsAffected;
        for (OrderItem item : order.getItems()) {
            rowsAffected = this.productRepository.freeReservedStock(item.getProductId(), item.getQuantity());
            if (rowsAffected == 0)
                // temporal
                throw new RuntimeException("Invalid order reservation: "+order.getId()+
                        "; order item id: "+item.getId());
        }
    }

    public void completeReservation(Order order) {
        Integer rowsAffected;
        for (OrderItem item : order.getItems()) {
            rowsAffected = this.productRepository.removeReserve(item.getProductId(), item.getQuantity());
            if (rowsAffected == 0)
                // temporal
                throw new RuntimeException("Unable to removed reserved products;\n" +
                        "order id: "+order.getId()+"; order item id:"+ item.getId()+"; product Id: "+item.getProductId());
        }
    }
}
