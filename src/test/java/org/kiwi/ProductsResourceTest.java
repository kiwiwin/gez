package org.kiwi;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kiwi.domain.Product;
import org.kiwi.persistent.PriceMapper;
import org.kiwi.persistent.ProductRepository;
import org.kiwi.resource.handler.ResourceNotFoundException;
import org.kiwi.resource.handler.ResourceNotFoundExceptionHandler;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.kiwi.domain.ProductWithId.productWithId;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductsResourceTest extends JerseyTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private PriceMapper priceMapper;

    @Captor
    private ArgumentCaptor<Product> argumentProductCaptor;

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
    public void should_get_product_by_id() {
        when(productRepository.findProductById(eq(1))).thenReturn(productWithId(1, new Product("apple")));

        final Response response = target("/products/1")
                .request()
                .get();

        assertThat(response.getStatus(), is(200));

        final Map product = response.readEntity(Map.class);

        assertThat(product.get("name"), is("apple"));
        assertThat((String) product.get("uri"), endsWith("products/1"));
    }

    @Test
    public void should_get_404_when_product_not_exist() {
        when(productRepository.findProductById(eq(1))).thenThrow(new ResourceNotFoundException());

        final Response response = target("/products/1")
                .request()
                .get();

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void should_get_all_products() {
        when(productRepository.getAllProducts())
                .thenReturn(Arrays.asList(productWithId(1, new Product("apple")), productWithId(2, new Product("banana"))));

        final Response response = target("/products")
                .request()
                .get();

        assertThat(response.getStatus(), is(200));

        final List products = response.readEntity(List.class);

        assertThat(products.size(), is(2));

        final Map product = (Map) products.get(0);
        assertThat(product.get("name"), is("apple"));
        assertThat((String) product.get("uri"), endsWith("products/1"));

        final Map product2 = (Map) products.get(1);
        assertThat(product2.get("name"), is("banana"));
        assertThat((String) product2.get("uri"), endsWith("products/2"));

    }

    @Test
    public void should_create_product_status_201() {
        argumentProductCaptor = ArgumentCaptor.forClass(Product.class);

        final MultivaluedMap<String, String> keyValues = new MultivaluedHashMap<>();
        keyValues.putSingle("name", "apple");

        final Form productForm = new Form(keyValues);

        final Response response = target("/products")
                .request()
                .post(Entity.form(productForm));

        verify(productRepository).createProduct(argumentProductCaptor.capture());

        assertThat(response.getStatus(), is(201));
        assertThat(argumentProductCaptor.getValue().getName(), is("apple"));
    }
}
