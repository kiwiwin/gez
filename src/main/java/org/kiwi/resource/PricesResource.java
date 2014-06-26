package org.kiwi.resource;

import org.kiwi.domain.Price;
import org.kiwi.domain.Product;
import org.kiwi.persistent.PriceMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class PricesResource {
    private final Product product;
    private final PriceMapper priceMapper;

    public PricesResource(Product product, PriceMapper priceMapper) {
        this.product = product;
        this.priceMapper = priceMapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Price> getAllPricesOfProduct() {
        return priceMapper.getPrices(product);
    }
}
