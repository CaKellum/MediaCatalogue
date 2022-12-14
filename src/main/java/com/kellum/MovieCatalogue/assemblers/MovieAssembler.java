package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.kellum.MovieCatalogue.controllers.MovieController;
import com.kellum.MovieCatalogue.model.Movie;

@Component
public class MovieAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    public EntityModel<Movie> toModel(Movie movie) {
        return EntityModel.of(movie, //
                linkTo(methodOn(MovieController.class).getById(movie.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).all()).withRel("movie"));
    }

    @Override
    public CollectionModel<EntityModel<Movie>> toCollectionModel(Iterable<? extends Movie> list) {
        ArrayList<Movie> listOfMovies = new ArrayList<>();
        list.forEach(movie -> {
            listOfMovies.add(movie);
        });
        List<EntityModel<Movie>> boookEntityList = listOfMovies.stream().map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(boookEntityList, linkTo(methodOn(MovieController.class).all()).withSelfRel());
    }
}
