package org.kiwi.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/products")
public class ProductsResource {
    @GET
    @Path("{productId}")
    public String getProductById(@PathParam("productId") int productId) {
        return "";
    }
}
