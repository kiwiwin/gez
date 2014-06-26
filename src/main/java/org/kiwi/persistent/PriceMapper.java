package org.kiwi.persistent;

import org.kiwi.domain.Price;
import org.kiwi.domain.Product;

import java.util.List;

public interface PriceMapper {
    List<Price> getPrices(Product any);

    Price getPrice(Product product, int priceId);
}
