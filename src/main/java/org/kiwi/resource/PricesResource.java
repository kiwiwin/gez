package org.kiwi.resource;

import org.glassfish.jersey.server.Uri;
import org.kiwi.domain.Price;
import org.kiwi.domain.Product;
import org.kiwi.json.PriceRefJson;
import org.kiwi.persistent.PriceMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.Timestamp;
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
    @Produces(MediaType.APPLICATION_JSON)
    public PriceRefJson getPrice(@PathParam("priceId") int priceId, @Context UriInfo uriInfo) {
        return new PriceRefJson(uriInfo, product, priceMapper.getPrice(product, priceId));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createPrice(Form form, @Context UriInfo uriInfo) {
        final Price price = getPriceFromForm(form);
        priceMapper.createPrice(product, price);
        return Response.status(201).header("location", new PriceRefJson(uriInfo, product, price).getUri()).build();
    }

    private Price getPriceFromForm(Form form) {
        final MultivaluedMap<String, String> map = form.asMap();
        final Timestamp modifiedAt = Timestamp.valueOf(map.getFirst("modifiedAt"));
        final String modifiedBy = map.getFirst("modifiedBy");
        final int price = Integer.valueOf(map.getFirst("price"));

        return new Price(price, modifiedAt, modifiedBy);
    }

}
