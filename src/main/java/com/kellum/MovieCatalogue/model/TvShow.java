package com.kellum.MovieCatalogue.model;

import javax.persistence.Entity;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Polymorphism(type=PolymorphismType.EXPLICIT)
public class TvShow extends Media {

    @Getter
    @Setter
    private int seasons;
    
    @Getter
    @Setter
    private int episodes;

    public TvShow(String title, MediaFormat format, int seasons, int episodes) {
        super(title, MediaCategory.TV_SHOW, format);
    }
}
