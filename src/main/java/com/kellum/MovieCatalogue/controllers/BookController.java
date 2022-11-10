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

import com.kellum.MovieCatalogue.assemblers.BookAssembler;
import com.kellum.MovieCatalogue.exceptions.MediaNotFoundException;
import com.kellum.MovieCatalogue.model.Book;
import com.kellum.MovieCatalogue.model.Media.MediaCategory;
import com.kellum.MovieCatalogue.model.Media.MediaFormat;
import com.kellum.MovieCatalogue.repositories.BookRepository;

@RestController
public class BookController implements ControllerInterface<BookRepository, Book> {
    private final BookRepository bookRepository;
    private final BookAssembler bookAssembler;

    BookController(BookRepository repository, BookAssembler assembler) {
        this.bookRepository = repository;
        this.bookAssembler = assembler;
    }

    @GetMapping(value = "/books")
    @Override
    public CollectionModel<EntityModel<Book>> all() {
        return bookAssembler.toCollectionModel(bookRepository.findAll());
    }

    @PostMapping(value = "/books")
    @Override
    public EntityModel<Book> newElement(@RequestBody Book newElement) {
        return bookAssembler.toModel(bookRepository.save(newElement));
    }

    @GetMapping(value = "/books/{id}")
    @Override
    public EntityModel<Book> getById(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new MediaNotFoundException(MediaCategory.BOOK, Long.toString(id)));
        return bookAssembler.toModel(book);
    }

    @GetMapping(value = "/books/{title}")
    @Override
    public EntityModel<Book> getByTitle(@PathVariable String title) {
        return getById(getIdFromTitle(title).getContent());
    }

    @PutMapping(value = "/books/{id}")
    @Override
    public EntityModel<Book> replace(@PathVariable Long id, @RequestBody Book newElement) {
        Book newBook = bookRepository.findById(id).map(book -> {
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
        return bookAssembler.toModel(newBook);
    }

    @DeleteMapping(value = "/books/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok().body(id);  
    }

    @GetMapping(value = "/books/id/{title}")
    @Override
    public EntityModel<Long> getIdFromTitle(@PathVariable String title) {
        List<Book> allBooks = bookRepository.findAll();
        allBooks.removeIf(book -> (!book.getTitle().equals(title)));
        return EntityModel.of(allBooks.get(0).getId(),
                linkTo(methodOn(this.getClass()).getIdFromTitle(title)).withSelfRel());
    }  
}
