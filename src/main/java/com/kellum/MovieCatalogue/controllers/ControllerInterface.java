package com.kellum.MovieCatalogue.controllers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import com.kellum.MovieCatalogue.model.Media;


public interface ControllerInterface<T extends JpaRepository<E, Long>, E extends Media> {
    CollectionModel<EntityModel<E>> all();

    EntityModel<E> newElement(E newElement);

    EntityModel<E> getById(Long id);

    EntityModel<E> getByTitle(String title);

    EntityModel<Long> getIdFromTitle(String title);

    EntityModel<E> replace(Long id, E newElement);

    ResponseEntity<?> delete(Long id);
}
