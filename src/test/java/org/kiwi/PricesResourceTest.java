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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
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

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Captor
    private ArgumentCaptor<Price> priceArgumentCaptor;

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

        final Map price = (Map) prices.get(0);
        assertThat(price.get("price"), is(120));
        assertThat(price.get("modifiedAt"), is(new Timestamp(114, 1, 1, 0, 0, 0, 0).toString()));
        assertThat(price.get("modifiedBy"), is("kiwi"));
        assertThat((String)price.get("uri"), endsWith("products/1/prices/1"));
    }

    @Test
    public void should_get_price_of_a_product() {
        when(productRepository.findProductById(eq(1))).thenReturn(productWithId(1, new Product("apple")));
        when(priceMapper.getPrice(any(Product.class), eq(1))).thenReturn(priceWithId(1, new Price(120, new Timestamp(114, 1, 1, 0, 0, 0, 0), "kiwi")));


        final Response response = target("/products/1/prices/1")
                .request()
                .get();

        assertThat(response.getStatus(), is(200));

        final Map price = response.readEntity(Map.class);

        assertThat(price.get("price"), is(120));
        assertThat(price.get("modifiedAt"), is(new Timestamp(114, 1, 1, 0, 0, 0, 0).toString()));
        assertThat(price.get("modifiedBy"), is("kiwi"));
        assertThat((String)price.get("uri"), endsWith("products/1/prices/1"));
    }

    @Test
    public void should_create_price() {
        when(productRepository.findProductById(eq(1))).thenReturn(productWithId(1, new Product("apple")));

        final MultivaluedMap<String, String> keyValues = new MultivaluedHashMap<>();


        final Form priceForm = new Form(keyValues);

        final Response response = target("/products/1/prices")
                .request()
                .post(Entity.form(priceForm));

        assertThat(response.getStatus(), is(201));
    }
}
