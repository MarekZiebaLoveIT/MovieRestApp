package com.mz.movierest.services;

import com.mz.movierest.exceptions.ItemNotFoundException;
import com.mz.movierest.models.Movie;
import com.mz.movierest.models.MovieCategory;
import com.mz.movierest.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRepository repository;

    public Movie addMovie(Movie movie) {
        return repository.save(movie);
    }

    public void removeMovie(Long id) {
        repository.deleteById(id);
    }

    public Movie updateMovie(Long id, Movie formMovie) {
        var optionalMovie = findMovie(id);
        if (optionalMovie.isPresent()) {
            var dbMovie = optionalMovie.get();
            dbMovie.setTitle(formMovie.getTitle());
            dbMovie.setContent(formMovie.getContent());
            dbMovie.setDirector(formMovie.getDirector());
            dbMovie.setYear(formMovie.getYear());
            dbMovie.setCategory(formMovie.getCategory());
            return repository.save(dbMovie);
        }
        throw new ItemNotFoundException("Movie not found");
    }

    public Optional<Movie> findMovie(Long id) {
        return repository.findById(id);
    }

    public List<Movie> getMoviesByParams(String title, String director, Integer yearFrom, Integer yearTo, MovieCategory category) {
        if (title == null && director == null && yearFrom == null && yearTo == null && category == null) {
            return repository.findAll();
        }
        return repository.findByParams(title, director, yearFrom, yearTo, category);
    }

}
