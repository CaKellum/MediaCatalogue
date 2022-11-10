package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.kellum.MovieCatalogue.controllers.TVShowController;
import com.kellum.MovieCatalogue.model.TvShow;
@Component
public class TVShowAssembler implements RepresentationModelAssembler<TvShow, EntityModel<TvShow>> {

    @Override
    public EntityModel<TvShow> toModel(TvShow tvShow) {
        return EntityModel.of(tvShow, //
                linkTo(methodOn(TVShowController.class).getById(tvShow.getId())).withSelfRel(),
                linkTo(methodOn(TVShowController.class).all()).withRel("tvShow"));
    }

    
    @Override
    public CollectionModel<EntityModel<TvShow>> toCollectionModel(Iterable<? extends TvShow> list) {
        ArrayList<TvShow> listOfTvShows = new ArrayList<>();
        list.forEach(tvShow -> { listOfTvShows.add(tvShow); });
        List<EntityModel<TvShow>> boookEntityList = listOfTvShows.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(boookEntityList, linkTo(methodOn(TVShowController.class).all()).withSelfRel());
    }

}
