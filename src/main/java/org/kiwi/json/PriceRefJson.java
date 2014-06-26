package org.kiwi.json;

import org.kiwi.domain.Price;
import org.kiwi.domain.Product;

import javax.ws.rs.core.UriInfo;

public class PriceRefJson {
    private final UriInfo uriInfo;
    private final Product product;
    private final Price price;

    public PriceRefJson(UriInfo uriInfo, Product product, Price price) {
        this.uriInfo = uriInfo;
        this.product = product;
        this.price = price;
    }

    public int getPrice() {
        return price.getPrice();
    }

    public String getModifiedAt() {
        return price.getModifiedAt().toString();
    }

    public String getModifiedBy() {
        return price.getModifiedBy();
    }

    public String getUri() {
        return uriInfo.getBaseUri() + "products/" + product.getId() + "/prices/" + price.getId();
    }
}
