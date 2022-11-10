package com.kellum.MovieCatalogue.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.kellum.MovieCatalogue.model.Music;

public class MusicAssembler implements RepresentationModelAssembler<Music, EntityModel<Music>> {

    @Override
    public EntityModel<Music> toModel(Music entity) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
