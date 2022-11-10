package com.kellum.MovieCatalogue.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.kellum.MovieCatalogue.model.TvShow;

public class TVShowAssembler implements RepresentationModelAssembler<TvShow, EntityModel<TvShow>> {

    @Override
    public EntityModel<TvShow> toModel(TvShow entity) {
        // TODO Auto-generated method stub
        return null;
    }

}
