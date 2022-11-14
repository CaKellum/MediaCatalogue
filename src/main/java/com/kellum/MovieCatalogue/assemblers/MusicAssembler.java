package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.kellum.MovieCatalogue.controllers.MusicController;
import com.kellum.MovieCatalogue.model.Music;
@Component
public class MusicAssembler implements RepresentationModelAssembler<Music, EntityModel<Music>> {

    @Override
    public EntityModel<Music> toModel(Music music) {
        return EntityModel.of(music, //
                linkTo(methodOn(MusicController.class).getById(music.getId())).withSelfRel(),
                linkTo(methodOn(MusicController.class).all()).withRel("music"));
    }

    
    @Override
    public CollectionModel<EntityModel<Music>> toCollectionModel(Iterable<? extends Music> list) {
        ArrayList<Music> listOfMusics = new ArrayList<>();
        list.forEach(music -> { listOfMusics.add(music); });
        List<EntityModel<Music>> boookEntityList = listOfMusics.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(boookEntityList, linkTo(methodOn(MusicController.class).all()).withSelfRel());
    }

}
