package com.kellum.MovieCatalogue.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kellum.MovieCatalogue.model.Media;

import java.util.List;;

public interface ControllerInterface<T extends JpaRepository<E, Long>, E extends Media> {
    List<E> all();

    E newElement(E newElement);

    E getById(Long id);

    E getByTitel(String title);

    E replace(Long id, E newElement);

    void delete(Long id);
}
