package com.kellum.MovieCatalogue.controllers;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kellum.MovieCatalogue.assemblers.TVShowAssembler;
import com.kellum.MovieCatalogue.exceptions.MediaNotFoundException;
import com.kellum.MovieCatalogue.model.TvShow;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.repositories.TVShowRepository;

@RestController
public class TVShowController implements ControllerInterface<TVShowRepository, TvShow> {
    private final TVShowRepository tvShowRepository;
    private final TVShowAssembler tvShowAssembler;

    TVShowController(TVShowRepository repository, TVShowAssembler assembler) {
        this.tvShowRepository = repository;
        this.tvShowAssembler = assembler;
    }

    @GetMapping(value = "/tv")
    @Override
    public  CollectionModel<EntityModel<TvShow>> all() {
        return tvShowAssembler.toCollectionModel(tvShowRepository.findAll());
    }

    @PostMapping(value = "/tv")
    @Override
    public EntityModel<TvShow> newElement(@RequestBody TvShow newElement) {
        return tvShowAssembler.toModel(tvShowRepository.save(newElement));
    }

    @GetMapping(value = "/tv/{id}")
    @Override
    public EntityModel<TvShow> getById(@PathVariable Long id) {
        TvShow tv =  tvShowRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.TV_SHOW, Long.toString(id)));
        return tvShowAssembler.toModel(tv);
    }

    @PutMapping(value = "/tv/{id}")
    @Override
    public EntityModel<TvShow> replace(@PathVariable Long id, @RequestBody TvShow newElement) {
        TvShow tv = tvShowRepository.findById(id).map(tvShow -> {
            tvShow.setTitle(newElement.getTitle());
            tvShow.setFormat(MediaFormat.valueOf(newElement.getFormat()));
            tvShow.setCategory(MediaCategory.TV_SHOW);
            return tvShowRepository.save(tvShow);
        }).orElseGet(() -> {
            newElement.setId(id);
            return tvShowRepository.save(newElement);
        });
        return tvShowAssembler.toModel(tv);
    }

    @DeleteMapping(value = "/tv/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        tvShowRepository.deleteById(id);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping(value = "/tv/{title}")
    @Override
    public EntityModel<TvShow> getByTitle(@PathVariable String title) {
        return getById(getIdFromTitle(title).getContent());
    }

    @GetMapping(value = "/tv/id/{title}")
    @Override
    public EntityModel<Long> getIdFromTitle(@PathVariable String title) {
        List<TvShow> tvAll = tvShowRepository.findAll();
        tvAll.removeIf(tvShow -> (!tvShow.getTitle().equals(title)));
        return EntityModel.of(tvAll.get(0).getId(),
                linkTo(methodOn(this.getClass()).getIdFromTitle(title)).withSelfRel());
    }

}
