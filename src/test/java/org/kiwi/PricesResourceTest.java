package org.kiwi;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kiwi.persistent.ProductRepository;
import org.kiwi.resource.handler.ResourceNotFoundExceptionHandler;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PricesResourceTest extends JerseyTest {
    @Mock
    private ProductRepository productRepository;

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .packages("org.kiwi.resource")
                .register(ResourceNotFoundExceptionHandler.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(productRepository).to(ProductRepository.class);
                    }
                });
    }

    @Test
    public void should_get_all_prices_of_a_product() {
        final Response response = target("/products/1/prices")
                .request()
                .get();

        assertThat(response.getStatus(), is(200));
    }
}
