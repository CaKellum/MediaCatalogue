package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.kellum.MovieCatalogue.controllers.BookController;
import com.kellum.MovieCatalogue.model.Book;

@Component
public class BookAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book book) {
        return EntityModel.of(book, 
                linkTo(methodOn(BookController.class).getById(book.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("books"));
    }

}