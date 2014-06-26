package org.kiwi;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kiwi.domain.Product;
import org.kiwi.persistent.ProductRepository;
import org.kiwi.resource.handler.ResourceNotFoundException;
import org.kiwi.resource.handler.ResourceNotFoundExceptionHandler;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductsResourceTest extends JerseyTest {
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
    public void should_get_product_by_id() {
        when(productRepository.findProductById(eq(1))).thenReturn(new Product());


        final Response response = target("/products/1")
                .request()
                .get();

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void should_get_404_when_product_not_exist() {
        when(productRepository.findProductById(eq(1))).thenThrow(new ResourceNotFoundException());

        final Response response = target("/products/1")
                .request()
                .get();

        assertThat(response.getStatus(), is(404));
    }
}
