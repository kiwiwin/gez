package org.kiwi.persistent;

import org.kiwi.domain.Product;

public interface ProductRepository {
    Product findProductById(int productId);
}
