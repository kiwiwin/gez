package org.kiwi.resource;

import org.kiwi.domain.Product;
import org.kiwi.json.ProductRefJson;
import org.kiwi.persistent.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/products")
public class ProductsResource {
    @Inject
    private ProductRepository productRepository;

    @GET
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductRefJson getProductById(@PathParam("productId") int productId) {
        return new ProductRefJson(productRepository.findProductById(productId));
    }
}
