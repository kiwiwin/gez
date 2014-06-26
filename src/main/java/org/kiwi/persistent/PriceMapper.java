package org.kiwi.persistent;

import org.apache.ibatis.annotations.Param;
import org.kiwi.domain.Price;
import org.kiwi.domain.Product;

import java.util.List;

public interface PriceMapper {
    List<Price> getPrices(@Param("product")Product product);

    Price getPrice(@Param("product")Product product, @Param("priceId")int priceId);

    int createPrice(@Param("product")Product product, @Param("price")Price price);
}
