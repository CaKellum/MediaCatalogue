package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.kellum.MovieCatalogue.controllers.TVShowController;
import com.kellum.MovieCatalogue.model.TvShow;

public class TVShowAssembler implements RepresentationModelAssembler<TvShow, EntityModel<TvShow>> {

    @Override
    public EntityModel<TvShow> toModel(TvShow tvShow) {
        return EntityModel.of(tvShow, //
                linkTo(methodOn(TVShowController.class).getById(tvShow.getId())).withSelfRel(),
                linkTo(methodOn(TVShowController.class).all()).withRel("tvShow"));
    }

}
