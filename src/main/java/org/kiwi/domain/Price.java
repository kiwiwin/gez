package org.kiwi.domain;

import java.sql.Timestamp;

public class Price {
    private final int price;
    private final Timestamp modifiedAt;
    private final String modifiedBy;
    int id;

    public Price(int price, Timestamp modifiedAt, String modifiedBy) {
        this.price = price;
        this.modifiedAt = modifiedAt;
        this.modifiedBy = modifiedBy;
    }

    public int getPrice() {
        return price;
    }

    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public int getId() {
        return id;
    }
}
