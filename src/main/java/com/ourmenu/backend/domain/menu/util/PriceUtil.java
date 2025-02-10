package com.ourmenu.backend.domain.menu.util;

public class PriceUtil {

    private PriceUtil() {

    }

    public static Long convertMinPrice(Long minPrice) {
        if (minPrice == null) {
            return null;
        }
        if (minPrice.equals(5000L)) {
            return null;
        }
        return minPrice;
    }

    public static Long convertMaxPrice(Long maxPrice) {
        if (maxPrice == null) {
            return null;
        }
        if (maxPrice.equals(50000L)) {
            return null;
        }
        return maxPrice;
    }
}
