package org.kiwi;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProductsResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .packages("org.kiwi.resource");
    }

    @Test
    public void should_get_product_by_id() {
        final Response response = target("/products/1")
                .request()
                .get();

        assertThat(response.getStatus(), is(200));
    }
}
