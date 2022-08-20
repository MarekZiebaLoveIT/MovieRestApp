package com.mz.movierest.dto;

import lombok.Data;

@Data
public class MovieRequestDTO {

    private Long movieId;
    private Integer currentPageNumber = 0;
    private Integer pageSize = 15;
    private String sortColumnName = "title";
    private String sortDirection = "desc";
    private String filterText;

}
