package com.pragma.powerup.smallsquaremicroservice.domain.util;

import java.util.List;

public class Pagination {
    private Pagination() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> List<T> paginate(List<T> list, int pageSize, int pageNumber) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return list.subList(startIndex, endIndex);
    }
}
