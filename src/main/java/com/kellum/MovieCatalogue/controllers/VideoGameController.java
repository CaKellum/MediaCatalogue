package com.kellum.MovieCatalogue.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kellum.MovieCatalogue.assemblers.VideoGameAssembler;
import com.kellum.MovieCatalogue.exceptions.MediaNotFoundException;
import com.kellum.MovieCatalogue.model.VideoGame;
import com.kellum.MovieCatalogue.model.VideoGame.VGConsole;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.repositories.VideoGameRepository;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class VideoGameController implements ControllerInterface<VideoGameRepository, VideoGame> {
    private final VideoGameRepository vgRepository;
    private final VideoGameAssembler vgAssembler;

    VideoGameController(VideoGameRepository repository, VideoGameAssembler assembler) {
        this.vgRepository = repository;
        this.vgAssembler = assembler;
    }

    // MARK: Get Lists of Games

    @GetMapping(value = "/video_games")
    @Override
    public CollectionModel<EntityModel<VideoGame>> all() {
        List<EntityModel<VideoGame>> list = vgRepository.findAll().stream().map(vgAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list, linkTo(methodOn(this.getClass()).all()).withSelfRel());
    }

    @GetMapping(value = "/video_games/{console}")
    public CollectionModel<EntityModel<VideoGame>> getVideoGamesForConsole(@RequestParam String console) {
        List<VideoGame> vGames = vgRepository.findAll();
        vGames.removeIf(videoGame -> (!videoGame.getCategory().equals(console)));
        List<EntityModel<VideoGame>> vGamesList = vGames.stream().map(vgAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(vGamesList,
                linkTo(methodOn(this.getClass()).getVideoGamesForConsole(console)).withSelfRel());
    }

    // MARK: Add Video Game

    @PostMapping(value = "/video_games")
    @Override
    public VideoGame newElement(@RequestBody VideoGame newElement) {
        return vgRepository.save(newElement);
    }

    // MARK: Get single video game

    @GetMapping(value = "/video_game/{id}")
    @Override
    public VideoGame getById(@PathVariable Long id) {
        return vgRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.VIDEO_GAME, Long.toString(id)));
    }

    // MARK: Update single video game

    @PutMapping(value = "/video_game/{id}")
    @Override
    public VideoGame replace(@PathVariable Long id, @RequestBody VideoGame newElement) {
        return vgRepository.findById(id).map(videoGame -> {
            VGConsole console = VGConsole.valueOf(newElement.getConsole());
            videoGame.setTitle(newElement.getTitle());
            videoGame.setConsole(console);
            videoGame.setCategory(MediaCategory.VIDEO_GAME);
            videoGame.setFormat(console.getFormat());
            return vgRepository.save(videoGame);
        }).orElseGet(() -> {
            newElement.setId(id);
            return vgRepository.save(newElement);
        });
    }

    // MARK: Delete single video game

    @DeleteMapping(value = "/video_game/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        vgRepository.deleteById(id);
    }

    @GetMapping(value = "/video_game/{title}")
    @Override
    public VideoGame getByTitle(@PathVariable String title) {
        return getById(getIdFromTitle(title));
    }

    @GetMapping(value = "/video_game/id/{title}")
    @Override
    public Long getIdFromTitle(@PathVariable String title) {
        List<VideoGame> allVG = vgRepository.findAll();
        allVG.removeIf(vg -> (!vg.getTitle().equals(title)));
        return allVG.get(0).getId();
    }

}
