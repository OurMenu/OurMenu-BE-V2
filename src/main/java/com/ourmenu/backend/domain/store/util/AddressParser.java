package com.ourmenu.backend.domain.store.util;

import java.util.Arrays;
import java.util.List;

public class AddressParser {

    private AddressParser() {
    }

    private static List<String> parseAddress(String address) {
        return Arrays.stream(address.split(" ")).toList();
    }

    public static String parseAddressToCityDistrict(String address) {
        List<String> addresses = parseAddress(address);
        if (addresses.size() >= 2) {
            return addresses.get(0) + " " + addresses.get(1);
        }
        if (addresses.size() == 1) {
            return addresses.get(0);
        }
        return "";
    }
}
