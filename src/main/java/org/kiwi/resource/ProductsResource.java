package org.kiwi.resource;

import org.kiwi.persistent.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/products")
public class ProductsResource {
    @Inject
    private ProductRepository productRepository;

    @GET
    @Path("{productId}")
    public String getProductById(@PathParam("productId") int productId) {
        productRepository.findProductById(productId);
        return "";
    }
}
