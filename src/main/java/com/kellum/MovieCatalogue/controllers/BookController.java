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
import com.kellum.MovieCatalogue.model.Book;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.repositories.BookRepository;

@RestController
public class BookController implements ControllerInterface<BookRepository, Book> {
    private final BookRepository bookRepository;

    BookController(BookRepository repository) {
        this.bookRepository = repository;
    }

    @GetMapping(value = "/books")
    @Override
    public List<Book> all() {
        return bookRepository.findAll();
    }

    @PostMapping(value = "/books")
    @Override
    public Book newElement(@RequestBody Book newElement) {
        return bookRepository.save(newElement);
    }

    @GetMapping(value = "/books/{id}")
    @Override
    public Book getById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.BOOK, Long.toString(id)));
    }

    @Override
    public Book getByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    @PutMapping(value = "/books/{id}")
    @Override
    public Book replace(@PathVariable Long id, @RequestBody Book newElement) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(newElement.getTitle());
            book.setAuthors(newElement.getAuthors());
            book.setChapters(newElement.getChapters());
            book.setPageCount(newElement.getPageCount());
            book.setFormat(MediaFormat.valueOf(newElement.getFormat()));
            book.setCategory(MediaCategory.BOOK);
            return bookRepository.save(book);
        }).orElseGet(() -> {
            newElement.setId(id);
            return bookRepository.save(newElement);
        });
    }

    @DeleteMapping(value = "/books/{id}")
    @Override
    public void delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }

}
