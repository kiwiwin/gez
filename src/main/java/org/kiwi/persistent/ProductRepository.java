package org.kiwi.persistent;

import org.kiwi.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product findProductById(int productId);

    List<Product> getAllProducts();

    int createProduct(Product product);
}
