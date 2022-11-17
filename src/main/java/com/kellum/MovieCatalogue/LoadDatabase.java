package com.kellum.MovieCatalogue;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kellum.MovieCatalogue.model.Book;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.repositories.BookRepository;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initData(BookRepository repo) {
        List<String> authors = Arrays.asList("name of an author", "name of the second author");
        return args -> {
            repo.save(new Book("book1", 1000, 10, authors, MediaFormat.AUDIO_BOOK));
            repo.save(new Book("book2", 50, 0, authors, MediaFormat.HARD_BACK));
        };
    }
}
