package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.kellum.MovieCatalogue.controllers.VideoGameController;
import com.kellum.MovieCatalogue.model.VideoGame;

@Component
public class VideoGameAssembler implements RepresentationModelAssembler<VideoGame, EntityModel<VideoGame>> {

    @Override
    public EntityModel<VideoGame> toModel(VideoGame videoGame) {
        return EntityModel.of(videoGame, //
                linkTo(methodOn(VideoGameController.class).getById(videoGame.getId())).withSelfRel(),
                linkTo(methodOn(VideoGameController.class).all()).withRel("VideoGame"));
    }

    @Override
    public CollectionModel<EntityModel<VideoGame>> toCollectionModel(Iterable<? extends VideoGame> list) {
        ArrayList<VideoGame> listOfVGS = new ArrayList<>();
        list.forEach(vg -> {
            listOfVGS.add(vg);
        });
        List<EntityModel<VideoGame>> VGSEntityList = listOfVGS.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(VGSEntityList, linkTo(methodOn(VideoGameController.class).all()).withSelfRel());
    }

}
