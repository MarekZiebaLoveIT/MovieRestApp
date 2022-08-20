package com.mz.movierest.controllers;

import com.mz.movierest.models.Movie;
import com.mz.movierest.models.MovieCategory;
import com.mz.movierest.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public List<Movie> search(@RequestBody String request) {
        return service.findMovieBySpecification();
        // TODO: 2022-08-20  
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
