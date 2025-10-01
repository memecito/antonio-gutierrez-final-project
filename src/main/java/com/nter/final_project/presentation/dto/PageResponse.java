package com.nter.final_project.presentation.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * A generic class that represents a paginated response structure.
 * Contains pagination information along with the content of the current page.
 *
 * @param <T> the type of the content in the response
 */
@Getter
public final class PageResponse<T> {

    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final long totalPages;
    private final List<T> content;

    /**
     * Constructor that initializes the PageResponse based on a {@link Page}.
     *
     * @param page the Spring Data {@link Page} object containing pagination information
     */
    public PageResponse(Page<T> page) {
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.content = page.getContent();
    }
}
