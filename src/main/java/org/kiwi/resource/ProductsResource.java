package org.kiwi.resource;

import org.kiwi.domain.Product;
import org.kiwi.json.ProductRefJson;
import org.kiwi.persistent.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
public class ProductsResource {
    @Inject
    private ProductRepository productRepository;

    @GET
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductRefJson getProductById(@PathParam("productId") int productId, @Context UriInfo uriInfo) {
        return new ProductRefJson(uriInfo, productRepository.findProductById(productId));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductRefJson> getAllProducts(@Context UriInfo uriInfo) {
        return productRepository.getAllProducts().stream()
                .map(product -> new ProductRefJson(uriInfo, product))
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createProduct(Form form) {
        final int updated = productRepository.createProduct(getProduct(form));
        return Response.status(201).build();
    }

    private Product getProduct(Form form) {
        final MultivaluedMap<String, String> map = form.asMap();
        return new Product(map.getFirst("name"));
    }
}
