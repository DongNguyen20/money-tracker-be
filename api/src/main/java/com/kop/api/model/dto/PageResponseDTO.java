package com.kop.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T> {
    
    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
    private Integer currentPage;
    private Integer pageSize;
    
    public static <T> PageResponseDTO<T> of(List<T> content, long totalElements, int currentPage, int pageSize) {
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        return PageResponseDTO.<T>builder()
                .content(content)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();
    }
}