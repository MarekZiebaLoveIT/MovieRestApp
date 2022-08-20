package com.mz.movierest.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 30, message = "Title requires 3-30 symbols")
    @Column(length = 30)
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 5, max = 200, message = "Content requires 5-200 symbols")
    @Column(length = 200)
    private String content;

    @NotBlank(message = "Director's Name is required")
    @Size(min = 3, max = 30, message = "Title requires 3-30 symbols")
    @Column(length = 30)
    private String director;

    @NotNull(message = "Year of movie is required")
    @Min(value = 1895, message = "Minimum year is 1895, guess why...")
    @Max(value = 2099, message = "Maximum year is set for 2099, for now...")
    private int year;

    @NotNull(message = "Category is require")
    @Enumerated(EnumType.STRING)
    private MovieCategory category;

    @NotBlank(message = "Url for pics is required")
    private String picUrl;

}
