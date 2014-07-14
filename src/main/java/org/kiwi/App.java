package org.kiwi;

import org.apache.ibatis.session.SqlSession;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.kiwi.persistent.MybatisConnectionFactory;
import org.kiwi.persistent.PriceMapper;
import org.kiwi.persistent.ProductRepository;
import org.kiwi.resource.handler.ResourceNotFoundExceptionHandler;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class App {

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(8080).build();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final HttpServer httpServer = startServer();

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                httpServer.shutdownNow();
            }
        }
    }

    private static HttpServer startServer() {
        final SqlSession sqlSession = MybatisConnectionFactory.getSqlSessionFactory().openSession();
        final PriceMapper priceMapper = sqlSession.getMapper(PriceMapper.class);
        final ProductRepository productRepository = sqlSession.getMapper(ProductRepository.class);

        final ResourceConfig config = new ResourceConfig()
                .packages("org.kiwi.resource")
                .register(ResourceNotFoundExceptionHandler.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(priceMapper).to(PriceMapper.class);
                        bind(productRepository).to(ProductRepository.class);
                    }
                });
        return GrizzlyHttpServerFactory.createHttpServer(getBaseURI(), config);
    }
}
