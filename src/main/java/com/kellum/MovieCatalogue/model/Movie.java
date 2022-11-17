package com.kellum.MovieCatalogue.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Polymorphism(type = PolymorphismType.EXPLICIT)
@NoArgsConstructor
public class Movie extends Media {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;

    Movie(String title, MediaFormat format) {
        super(title, MediaCategory.MOVIE, format);
    }
}
