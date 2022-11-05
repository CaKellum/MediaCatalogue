package com.kellum.MovieCatalogue.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kellum.MovieCatalogue.model.Movie;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.repositories.MovieRepository;
import com.kellum.MovieCatalogue.exceptions.MediaNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class MovieController implements ControllerInterface<MovieRepository, Movie>{
    private final MovieRepository movieRepository;

    MovieController(MovieRepository repository) {
        this.movieRepository = repository;
    }

    @GetMapping("/movies")
    @Override
    public List<Movie> all() {
        return movieRepository.findAll();
    }

    @PostMapping(value = "/movies")
    @Override
    public Movie newElement(@RequestBody Movie newElement) {
        return movieRepository.save(newElement);
    }

    @GetMapping(value = "/movies/{id}")
    @Override
    public Movie getById(@PathVariable Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.MOVIE, Long.toString(id)));
    }

    @PutMapping(value = "/movies/{id}")
    @Override
    public Movie replace(@PathVariable Long id, @RequestBody Movie newElement) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(newElement.getTitle());
            movie.setFormat(MediaFormat.valueOf(newElement.getFormat()));
            return movieRepository.save(movie);
        }).orElseGet(() -> {
            newElement.setId(id);
            return movieRepository.save(newElement);
        });
    }

    @DeleteMapping(value = "/movies/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        movieRepository.deleteById(id);
    }

    @GetMapping(value = "/movies/{title}")
    @Override
    public Movie getByTitle(@PathVariable String title) {
        return getById(getIdFromTitle(title));
    }

    @GetMapping(value = "/movies/id/{title}")
    @Override
    public Long getIdFromTitle(@PathVariable String title) {
        List<Movie> allMovie = all();
        allMovie.removeIf(movie -> (!movie.getTitle().equals(title)));
        return allMovie.get(0).getId();
    }
}
