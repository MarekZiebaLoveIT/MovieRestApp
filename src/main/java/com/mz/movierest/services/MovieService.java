package com.mz.movierest.services;

import com.mz.movierest.models.Movie;
import com.mz.movierest.models.MovieCategory;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Movie addMovie(Movie movie);

    void removeMovie(Long id);

    Movie updateMovie(Long id, Movie formMovie);

    Optional<Movie> findMovie(Long id);

    List<Movie> getMoviesByParams(String title, String director, Integer yearFrom, Integer yearTo, MovieCategory category);
}
