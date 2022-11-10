package com.kellum.MovieCatalogue.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
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
                linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<Book>> toCollectionModel(Iterable<? extends Book> list) {
        ArrayList<Book> listOfBooks = new ArrayList<>();
        list.forEach(book -> {
            listOfBooks.add(book);
        });
        List<EntityModel<Book>> bookEntityList = listOfBooks.stream().map(this::toModel).collect(Collectors.toList());
        return CollectionModel.of(bookEntityList, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }

}