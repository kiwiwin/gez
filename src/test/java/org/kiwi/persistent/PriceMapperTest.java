package org.kiwi.persistent;

import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kiwi.domain.Price;
import org.kiwi.domain.Product;

import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class PriceMapperTest {
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
    public void should_get_a_price_of_a_product() {
        final Product product = new Product("apple", "good");
        productRepository.createProduct(product);
        final Price price = new Price(120, new Timestamp(114, 1, 1, 0, 0, 0, 0), "kiwi");
        priceMapper.createPrice(product, price);


        final Price newPrice = priceMapper.getPrice(product, price.getId());

        assertThat(newPrice.getPrice(), is(120));
        assertThat(newPrice.getModifiedBy(), is("kiwi"));
    }

}
