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
public class TvShow extends Media {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;

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
