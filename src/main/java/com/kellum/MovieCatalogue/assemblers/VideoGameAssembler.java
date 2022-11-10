package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.kellum.MovieCatalogue.controllers.VideoGameController;
import com.kellum.MovieCatalogue.model.VideoGame;

public class VideoGameAssembler implements RepresentationModelAssembler<VideoGame, EntityModel<VideoGame>> {

    @Override
    public EntityModel<VideoGame> toModel(VideoGame videoGame) {
        return EntityModel.of(videoGame, //
                linkTo(methodOn(VideoGameController.class).getById(videoGame.getId())).withSelfRel(),
                linkTo(methodOn(VideoGameController.class).all()).withRel("VideoGame"));
    }

}
