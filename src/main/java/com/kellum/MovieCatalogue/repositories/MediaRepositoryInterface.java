package com.kellum.MovieCatalogue.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kellum.MovieCatalogue.model.Media;

public interface MediaRepositoryInterface<T extends Media> extends JpaRepository<T, Long> {}