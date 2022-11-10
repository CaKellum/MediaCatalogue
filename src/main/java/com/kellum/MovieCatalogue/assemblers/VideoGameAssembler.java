package com.kellum.MovieCatalogue.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.kellum.MovieCatalogue.model.VideoGame;

public class VideoGameAssembler implements RepresentationModelAssembler<VideoGame, EntityModel<VideoGame>> {

    @Override
    public EntityModel<VideoGame> toModel(VideoGame entity) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
