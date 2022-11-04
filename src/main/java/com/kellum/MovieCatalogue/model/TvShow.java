package com.kellum.MovieCatalogue.model;

import javax.persistence.Entity;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@Entity
@Polymorphism(type=PolymorphismType.EXPLICIT)
public class TvShow extends Media {
    public TvShow(String title, MediaFormat format) {
        super(title, MediaCategory.TV_SHOW, format);
    }
}
