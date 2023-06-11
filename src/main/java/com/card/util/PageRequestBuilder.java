package com.card.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public final class PageRequestBuilder {

    private PageRequestBuilder(){}

    /**
     * Default page number. 0 means first page.
     */
    public static final int DEFAULT_PAGE_NUMBER = 0;

    /**
     * Number of elements in a page.
     */
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 30;

    public static PageRequest getPageRequest(Integer pageSize, Integer pageNumber, String sortingCriteria) {
        return getPageRequest(pageSize, pageNumber, sortingCriteria, true);
    }

    public static PageRequest getPageRequest(Integer pageSize, Integer pageNumber, String sortingCriteria, boolean isMaxSize) {
        Set<String> sortingFields = new LinkedHashSet<>(Arrays.asList(StringUtils.split(StringUtils.defaultIfEmpty(sortingCriteria, ""), ",")));
        List<Order> sortingOrders = sortingFields
                .stream()
                .map(PageRequestBuilder::getOrder)
                .toList();
        Sort sort = sortingOrders.isEmpty() ? Sort.unsorted() : Sort.by(sortingOrders);

        pageNumber = defaultIfNull(pageNumber, DEFAULT_PAGE_NUMBER);
        pageNumber = pageNumber <= 0 ? DEFAULT_PAGE_NUMBER : pageNumber;

        pageSize = defaultIfNull(pageSize, DEFAULT_PAGE_SIZE);
        pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
        pageSize = (isMaxSize && pageSize > MAX_PAGE_SIZE) ? MAX_PAGE_SIZE : pageSize;


        return PageRequest.of(
                pageNumber,
                pageSize,
                sort);
    }

    private static Order getOrder(String value) {
        if (StringUtils.startsWith(value, "-")) {
            return new Order(Direction.DESC, StringUtils.substringAfter(value, "-"));
        } else if (StringUtils.startsWith(value, "+")) {
            return new Order(Direction.ASC, StringUtils.substringAfter(value, "+"));
        } else {
            return new Order(Direction.ASC, StringUtils.trim(value));
        }
    }
}
