package com.mz.movierest.controllers;

import com.mz.movierest.models.Movie;
import com.mz.movierest.models.MovieCategory;
import com.mz.movierest.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/movies")
public class MovieRestController {

    private final MovieService service;

    @GetMapping()
    public List<Movie> getMovies(@RequestParam(required = false, name = "t") String title,
                                 @RequestParam(required = false, name = "d") String director,
                                 @RequestParam(required = false, name = "yf") Integer yearFrom,
                                 @RequestParam(required = false, name = "yt") Integer yearTo,
                                 @RequestParam(required = false, name = "c") MovieCategory category) {
        return service.getMoviesByParams(title, director, yearFrom, yearTo, category);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        var optionalMovie = service.findMovie(id);
        if (optionalMovie.isPresent()) {
            return ResponseEntity.ok(optionalMovie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping()
    public ResponseEntity<Movie> addMovie(@RequestBody @Valid Movie movie) {
        return ResponseEntity.ok(service.addMovie(movie));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Movie> editMovie(@PathVariable Long id, @RequestBody @Valid Movie movie) {
        return ResponseEntity.ok(service.updateMovie(id, movie));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMovie(@PathVariable Long id) {
        var optionalMovie = service.findMovie(id);
        if (optionalMovie.isPresent()) {
            service.removeMovie(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
