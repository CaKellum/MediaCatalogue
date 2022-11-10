package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.kellum.MovieCatalogue.controllers.MovieController;
import com.kellum.MovieCatalogue.model.Movie;

public class MovieAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    public EntityModel<Movie> toModel(Movie movie) {
        return EntityModel.of(movie, //
                linkTo(methodOn(MovieController.class).getById(movie.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).all()).withRel("movie"));
    }

}
