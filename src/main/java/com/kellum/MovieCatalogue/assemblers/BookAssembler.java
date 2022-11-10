package com.kellum.MovieCatalogue.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.kellum.MovieCatalogue.model.Book;

@Component
public class BookAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book entity) {
        // TODO Auto-generated method stub
        return null;
    }

}