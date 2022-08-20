package com.mz.movierest.services;

import com.mz.movierest.dto.MovieRequestDTO;
import com.mz.movierest.exceptions.ItemNotFoundException;
import com.mz.movierest.models.Movie;
import com.mz.movierest.models.MovieCategory;
import com.mz.movierest.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Override
    public Movie addMovie(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public void removeMovie(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Movie updateMovie(Long id, Movie formMovie) {
        var movie = repository.findById(id)
                                     .orElseThrow(() -> new ItemNotFoundException("Movie not found"));

        movie.setTitle(formMovie.getTitle());
        movie.setContent(formMovie.getContent());
        movie.setDirector(formMovie.getDirector());
        movie.setYear(formMovie.getYear());
        movie.setCategory(formMovie.getCategory());
        return repository.save(movie);

    }

    @Override
    public Optional<Movie> findMovie(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Movie> getMoviesByParams(String title, String director, Integer yearFrom, Integer yearTo, MovieCategory category) {
        return repository.findAll();
    }

    @Override
    public Page<Movie> findMovieBySpecification(MovieRequestDTO movieRequestDTO) {
        return repository.findAll(getSpecification(movieRequestDTO), PageRequest.of(movieRequestDTO.getCurrentPageNumber(), movieRequestDTO.getPageSize(),
                Sort.by(isNotNullOrEmpty(movieRequestDTO.getSortDirection()) ? Sort.Direction.fromString(movieRequestDTO.getSortDirection()) : Sort.Direction.DESC, movieRequestDTO.getSortColumnName())));
    }



    private Specification<Movie> getSpecification(MovieRequestDTO movieRequestDTO) {

        return (root, query, criteriaBuilder) -> {

            query.distinct(true);

            Predicate predicateForMovie = criteriaBuilder.equal(root.get("id"), movieRequestDTO.getMovieId());

            if (isNotNullOrEmpty(movieRequestDTO.getFilterText())) {

                Predicate predicateForData = criteriaBuilder.or(
                        criteriaBuilder.like(root.get("title"), "%" + movieRequestDTO.getFilterText() + "%"),
                        criteriaBuilder.like(root.get("director"), "%" + movieRequestDTO.getFilterText() + "%"),
                        criteriaBuilder.like(root.get("year"), "%" + movieRequestDTO.getFilterText() + "%"),
                        criteriaBuilder.like(root.get("category"), "%" + movieRequestDTO.getFilterText() + "%"));
                return criteriaBuilder.and(predicateForMovie, predicateForData);
            }

            return criteriaBuilder.and(predicateForMovie);
        };

    }

    public boolean isNotNullOrEmpty(String inputString)
    {
        return inputString != null && !inputString.isBlank() && !inputString.isEmpty() && !inputString.equals("undefined") && !inputString.equals("null") && !inputString.equals(" ");
    }

}
