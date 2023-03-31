package com.kellum.MovieCatalogue;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kellum.MovieCatalogue.model.Book;
import com.kellum.MovieCatalogue.model.Movie;
import com.kellum.MovieCatalogue.model.Music;
import com.kellum.MovieCatalogue.model.TvShow;
import com.kellum.MovieCatalogue.model.VideoGame;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.model.VideoGame.VGConsole;
import com.kellum.MovieCatalogue.repositories.BookRepository;
import com.kellum.MovieCatalogue.repositories.MovieRepository;
import com.kellum.MovieCatalogue.repositories.MusicRepository;
import com.kellum.MovieCatalogue.repositories.TVShowRepository;
import com.kellum.MovieCatalogue.repositories.VideoGameRepository;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initBookData(BookRepository repo) {
        List<String> authors = Arrays.asList("name of an author", "name of the second author");
        return args -> {
            repo.save(new Book("book1", 1000, 10, authors, MediaFormat.AUDIO_BOOK));
            repo.save(new Book("book2", 50, 0, authors, MediaFormat.HARD_BACK));
        };
    }

    @Bean
    CommandLineRunner initMovieData(MovieRepository repo) {
        return args -> {
            repo.save(new Movie("movie1", MediaFormat.DVD));
            repo.save(new Movie("movie2", MediaFormat.BLU_RAY));
        };
    }

    @Bean
    CommandLineRunner initMusicData(MusicRepository repo) {
        return args -> {
            repo.save(new Music("album1", Arrays.asList("name of artist"), Arrays.asList("name of song"), MediaFormat.VINYL));
            repo.save(new Music("album2", Arrays.asList("name of artist"), Arrays.asList("name of song"), MediaFormat.CD));
        };
    }

    @Bean
    CommandLineRunner initTvShowData(TVShowRepository repo) {
        return args -> {
            repo.save(new TvShow("NCIS", MediaFormat.DVD, 10, 1000));
            repo.save(new TvShow("JERICHO", MediaFormat.DVD, 2, 20));
        };
    }

    @Bean
    CommandLineRunner initVGData(VideoGameRepository repo) {
        return args -> {
            repo.save(new VideoGame("title of game", VGConsole.PLAYSTATION));
            repo.save(new VideoGame("title of game", VGConsole.PLAY_STATION_5));
        };
    }

}
