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
import com.kellum.MovieCatalogue.model.VideoGame;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.repositories.VideoGameRepository;

@RestController
public class VideoGameController implements ControllerInterface<VideoGameRepository, VideoGame> {
    private final VideoGameRepository vgRepository;

    VideoGameController(VideoGameRepository repository) {
        this.vgRepository = repository;
    }

    @GetMapping(value = "/video_games")
    @Override
    public List<VideoGame> all() {
        return vgRepository.findAll();
    }

    @PostMapping(value = "/video_games")
    @Override
    public VideoGame newElement(@RequestBody VideoGame newElement) {
        return vgRepository.save(newElement);
    }

    @GetMapping(value = "/video_game/{id}")
    @Override
    public VideoGame getById(@PathVariable Long id) {
        return vgRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.VIDEO_GAME, Long.toString(id)));
    }

    @PutMapping(value = "/video_game/{id}")
    @Override
    public VideoGame replace(@PathVariable Long id, @RequestBody VideoGame newElement) {
        // TODO Auto-generated method stub
        return null;
    }

    @DeleteMapping(value = "/video_game/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        vgRepository.deleteById(id);
    }

}
