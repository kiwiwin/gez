package org.kiwi;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kiwi.domain.Price;
import org.kiwi.domain.Product;
import org.kiwi.persistent.PriceMapper;
import org.kiwi.persistent.ProductRepository;
import org.kiwi.resource.handler.ResourceNotFoundExceptionHandler;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.kiwi.domain.PriceWithId.priceWithId;
import static org.kiwi.domain.ProductWithId.productWithId;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PricesResourceTest extends JerseyTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private PriceMapper priceMapper;

    @Override
    protected Application configure() {
        return new ResourceConfig()
                .packages("org.kiwi.resource")
                .register(ResourceNotFoundExceptionHandler.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(productRepository).to(ProductRepository.class);
                        bind(priceMapper).to(PriceMapper.class);
                    }
                });
    }

    @Test
    public void should_get_all_prices_of_a_product() {
        when(productRepository.findProductById(eq(1))).thenReturn(productWithId(1, new Product("apple")));
        when(priceMapper.getPrices(any(Product.class))).thenReturn(Arrays.asList(priceWithId(1, new Price(120, new Timestamp(114, 1, 1, 0, 0, 0, 0), "kiwi"))));

        final Response response = target("/products/1/prices")
                .request()
                .get();

        assertThat(response.getStatus(), is(200));
        final List prices = response.readEntity(List.class);

        assertThat(prices.size(), is(1));
    }
}
