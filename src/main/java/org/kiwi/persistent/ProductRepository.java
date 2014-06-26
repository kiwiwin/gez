package org.kiwi.persistent;

import org.apache.ibatis.annotations.Param;
import org.kiwi.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product findProductById(@Param("productId")int productId);

    List<Product> getAllProducts();

    int createProduct(@Param("product")Product product);
}
