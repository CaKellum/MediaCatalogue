package com.kellum.MovieCatalogue.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kellum.MovieCatalogue.exceptions.MediaNotFoundException;
import com.kellum.MovieCatalogue.model.TvShow;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.repositories.TVShowRepository;

@RestController
public class TVShowController implements ControllerInterface<TVShowRepository, TvShow> {
    private final TVShowRepository tvShowRepository;

    TVShowController(TVShowRepository repository) {
        this.tvShowRepository = repository;
    }

    @GetMapping(value = "/tv")
    @Override
    public List<TvShow> all() {
        return tvShowRepository.findAll();
    }

    @PostMapping(value = "/tv")
    @Override
    public TvShow newElement(@RequestBody TvShow newElement) {
        return tvShowRepository.save(newElement);
    }

    @GetMapping(value = "/tv/{id}")
    @Override
    public TvShow getById(@PathVariable Long id) {
        return tvShowRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.TV_SHOW, Long.toString(id)));
    }

    @PutMapping(value = "/tv/{id}")
    @Override
    public TvShow replace(@PathVariable Long id, @RequestBody TvShow newElement) {
        return tvShowRepository.findById(id).map(tvShow -> {
            tvShow.setTitle(newElement.getTitle());
            tvShow.setFormat(MediaFormat.valueOf(newElement.getFormat()));
            tvShow.setCategory(MediaCategory.TV_SHOW);
            return tvShowRepository.save(tvShow);
        }).orElseGet(() -> {
            newElement.setId(id);
            return tvShowRepository.save(newElement);
        });
    }

    @DeleteMapping(value = "/tv/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        tvShowRepository.deleteById(id);
    }

    @GetMapping(value = "/tv/{title}")
    @Override
    public TvShow getByTitle(String title) {
        List<TvShow> tvAll = tvShowRepository.findAll();
        tvAll.removeIf(tvShow -> (tvShow.getTitle().equals(title)));
        return getById(tvAll.get(0).getId());
    }

    @GetMapping(value = "/tv/id/{title}")
    @Override
    public Long getIdFromTitle(String title) {
        List<TvShow> tvAll = tvShowRepository.findAll();
        tvAll.removeIf(tvShow -> (tvShow.getTitle().equals(title)));
        return tvAll.get(0).getId();
    }

}
