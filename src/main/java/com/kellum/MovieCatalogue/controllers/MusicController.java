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

import com.kellum.MovieCatalogue.assemblers.MusicAssembler;
import com.kellum.MovieCatalogue.exceptions.MediaNotFoundException;
import com.kellum.MovieCatalogue.model.Music;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.repositories.MusicRepository;

@RestController
public class MusicController implements ControllerInterface<MusicRepository, Music> {
    private final MusicRepository musicRepository;
    private final MusicAssembler musicAssembler;

    MusicController(MusicRepository repository, MusicAssembler assembler) {
        this.musicRepository = repository;
        this.musicAssembler = assembler;
    }

    @GetMapping(value = "/music")
    @Override
    public CollectionModel<EntityModel<Music>> all() {
        return musicAssembler.toCollectionModel(musicRepository.findAll());
    }

    @PostMapping(value = "/music")
    @Override
    public EntityModel<Music> newElement(@RequestBody Music newElement) {
        return musicAssembler.toModel(musicRepository.save(newElement));
    }

    @GetMapping(value = "/music/{id}")
    @Override
    public EntityModel<Music> getById(@PathVariable Long id) {
        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.MUSIC, Long.toString(id)));
        return musicAssembler.toModel(music);
    }

    @PutMapping(value = "/music/{id}")
    @Override
    public EntityModel<Music> replace(@PathVariable Long id, @RequestBody Music newElement) {
        Music updateMusic = musicRepository.findById(id).map(music -> {
            music.setArtists(newElement.getArtists());
            music.setTrackList(newElement.getTrackList());
            music.setCategory(MediaCategory.MUSIC);
            music.setTitle(newElement.getTitle());
            music.setFormat(MediaFormat.valueOf(newElement.getFormat()));
            return musicRepository.save(music);
        }).orElseGet(() -> {
            newElement.setId(id);
            return musicRepository.save(newElement);
        });
        return musicAssembler.toModel(updateMusic);
    }

    @DeleteMapping(value = "/music/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        musicRepository.deleteById(id);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping(value = "/music/{title}")
    @Override
    public EntityModel<Music> getByTitle(@PathVariable String title) {
        return getById(getIdFromTitle(title).getContent());
    }

    @GetMapping(value = "/music/id/{title}")
    @Override
    public EntityModel<Long> getIdFromTitle(@PathVariable String title) {
        List<Music> allMusic = musicRepository.findAll();
        allMusic.removeIf(music -> (!music.getTitle().equals(title)));
        return EntityModel.of(allMusic.get(0).getId(),
                linkTo(methodOn(this.getClass()).getIdFromTitle(title)).withSelfRel());
    }
}
