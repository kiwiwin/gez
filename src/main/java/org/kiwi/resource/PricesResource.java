package org.kiwi.resource;

import org.jvnet.hk2.internal.Collector;
import org.kiwi.domain.Price;
import org.kiwi.domain.Product;
import org.kiwi.json.PriceRefJson;
import org.kiwi.persistent.PriceMapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class PricesResource {
    private final Product product;
    private final PriceMapper priceMapper;

    public PricesResource(Product product, PriceMapper priceMapper) {
        this.product = product;
        this.priceMapper = priceMapper;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PriceRefJson> getAllPricesOfProduct(@Context UriInfo uriInfo) {
        return priceMapper.getPrices(product).stream()
                .map(price -> new PriceRefJson(uriInfo, product, price))
                .collect(Collectors.toList());
    }

    @GET
    @Path("{priceId}")
    public String getPrice(@PathParam("priceId") int priceId) {
        return "";
    }
}
