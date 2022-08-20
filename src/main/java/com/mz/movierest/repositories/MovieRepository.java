package com.mz.movierest.repositories;

import com.mz.movierest.models.Movie;
import com.mz.movierest.models.MovieCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("""
            select m from Movie m where
            m.title like %:title% or
            m.director like %:director% or 
            (:c is null or m.category = :c) or
            (:yf is null or m.year >= :yearFrom) and 
            (:yt is null or m.year <= :yearTo)
            """)

    List<Movie> findByParams(
            @Param("t") String title,
            @Param("d") String director,
            @Param("yf") Integer yearFrom,
            @Param("yt") Integer yearTo,
            @Param("c") MovieCategory category
    );
}
