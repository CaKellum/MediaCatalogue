package com.kellum.MovieCatalogue.controllers;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kellum.MovieCatalogue.model.Movie;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.repositories.MovieRepository;
import com.kellum.MovieCatalogue.assemblers.MovieAssembler;
import com.kellum.MovieCatalogue.exceptions.MediaNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class MovieController implements ControllerInterface<MovieRepository, Movie>{
    private final MovieRepository movieRepository;
    private final MovieAssembler movieAssembler;

    MovieController(MovieRepository repository, MovieAssembler assembler) {
        this.movieRepository = repository;
        this.movieAssembler = assembler;
    }

    @GetMapping("/movies")
    @Override
    public CollectionModel<EntityModel<Movie>> all() {
        return movieAssembler.toCollectionModel(movieRepository.findAll());
    }

    @PostMapping(value = "/movies")
    @Override
    public EntityModel<Movie> newElement(@RequestBody Movie newElement) {
        return movieAssembler.toModel(movieRepository.save(newElement));
    }

    @GetMapping(value = "/movies/{id}")
    @Override
    public EntityModel<Movie> getById(@PathVariable Long id) {
        Movie movie =  movieRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.MOVIE, Long.toString(id)));
        return movieAssembler.toModel(movie);
    }

    @PutMapping(value = "/movies/{id}")
    @Override
    public EntityModel<Movie> replace(@PathVariable Long id, @RequestBody Movie newElement) {
        Movie updatedMovie = movieRepository.findById(id).map(movie -> {
            movie.setTitle(newElement.getTitle());
            movie.setFormat(MediaFormat.valueOf(newElement.getFormat()));
            return movieRepository.save(movie);
        }).orElseGet(() -> {
            newElement.setId(id);
            return movieRepository.save(newElement);
        });
        return movieAssembler.toModel(updatedMovie);
    }

    @DeleteMapping(value = "/movies/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        movieRepository.deleteById(id);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping(value = "/movies/{title}")
    @Override
    public EntityModel<Movie> getByTitle(@PathVariable String title) {
        return getById(getIdFromTitle(title).getContent());
    }

    @GetMapping(value = "/movies/id/{title}")
    @Override
    public EntityModel<Long> getIdFromTitle(@PathVariable String title) {
        List<Movie> allMovie = movieRepository.findAll();
        allMovie.removeIf(movie -> (!movie.getTitle().equals(title)));
        return EntityModel.of(allMovie.get(0).getId(), linkTo(methodOn(this.getClass()).getIdFromTitle(title)).withSelfRel());
    }
}
