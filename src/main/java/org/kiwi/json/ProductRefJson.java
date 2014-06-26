package org.kiwi.json;

import org.kiwi.domain.Product;

public class ProductRefJson {
    private final Product product;

    public ProductRefJson(Product product) {
        this.product = product;
    }

    public String getName() {
        return product.getName();
    }

    public String getUri() {
        return "/products/1";
    }
}
