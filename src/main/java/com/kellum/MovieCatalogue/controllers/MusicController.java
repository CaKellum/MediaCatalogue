package com.kellum.MovieCatalogue.controllers;

import java.util.List;

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
    public List<Music> all() {
        return musicRepository.findAll();
    }

    @PostMapping(value = "/music")
    @Override
    public Music newElement(@RequestBody Music newElement) {
        return musicRepository.save(newElement);
    }

    @GetMapping(value = "/music/{id}")
    @Override
    public Music getById(@PathVariable Long id) {
        return musicRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.MUSIC, Long.toString(id)));
    }

    @PutMapping(value = "/music/{id}")
    @Override
    public Music replace(@PathVariable Long id, @RequestBody Music newElement) {
        return musicRepository.findById(id).map(music -> {
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
    }

    @DeleteMapping(value = "/music/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        musicRepository.deleteById(id);
    }

    @GetMapping(value = "/music/{title}")
    @Override
    public Music getByTitle(@PathVariable String title) {
        return getById(getIdFromTitle(title));
    }

    @GetMapping(value = "/music/id/{title}")
    @Override
    public Long getIdFromTitle(@PathVariable String title) {
        List<Music> allMusic = all();
        allMusic.removeIf(music -> (!music.getTitle().equals(title)));
        return allMusic.get(0).getId();
    }
}
