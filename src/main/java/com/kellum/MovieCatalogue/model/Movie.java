package com.kellum.MovieCatalogue.model;

import javax.persistence.Entity;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import lombok.NoArgsConstructor;

@Entity
@Polymorphism(type = PolymorphismType.EXPLICIT)
@NoArgsConstructor
public class Movie extends Media {

    Movie(String title, MediaFormat format) {
        super(title, MediaCategory.MOVIE, format);
    }
}
