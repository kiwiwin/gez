package org.kiwi.persistent;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kiwi.domain.Price;
import org.kiwi.domain.Product;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProductRepositoryTest {
    private SqlSession sqlSession;
    private ProductRepository productRepository;
    private PriceMapper priceMapper;

    @Before
    public void setUp() throws Exception {
        sqlSession = MybatisConnectionFactory.getSqlSessionFactory().openSession();
        productRepository = sqlSession.getMapper(ProductRepository.class);
        priceMapper = sqlSession.getMapper(PriceMapper.class);
    }

    @After
    public void tearDown() throws Exception {
        sqlSession.rollback();
        sqlSession.close();
    }

    @Test
    public void should_get_product_by_id() {
        final Product product = new Product("apple", "good");
        productRepository.createProduct(product);
        final Price price = new Price(120, new Timestamp(114, 1, 1, 0, 0, 0, 0), "kiwi");
        priceMapper.createPrice(product, price);

        final Product newProduct = productRepository.findProductById(product.getId());

        assertThat(newProduct.getName(), is("apple"));
        assertThat(newProduct.getCurrentPrice().getPrice(), is(120));
        assertThat(newProduct.getCurrentPrice().getModifiedBy(), is("kiwi"));
    }

    @Test
    public void should_all_products() {
        final Product product = new Product("apple", "good");
        productRepository.createProduct(product);

        final List<Product> products = productRepository.getAllProducts();

        assertThat(products.size(), is(1));
        assertThat(products.get(0).getName(), is("apple"));
    }
}
