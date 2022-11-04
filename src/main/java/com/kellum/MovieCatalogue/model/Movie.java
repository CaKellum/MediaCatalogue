package com.kellum.MovieCatalogue.model;

import javax.persistence.Entity;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@Entity
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Movie extends Media {

    Movie(String title, MediaFormat format) {
        super(title, MediaCategory.MOVIE, format);
    }
}
