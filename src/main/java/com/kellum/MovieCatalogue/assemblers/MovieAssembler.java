package com.kellum.MovieCatalogue.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.kellum.MovieCatalogue.model.Movie;

public class MovieAssembler implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    public EntityModel<Movie> toModel(Movie entity) {
        // TODO Auto-generated method stub
        return null;
    }

}
