package com.kellum.MovieCatalogue.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class Book extends Media {

    @ElementCollection
    @Getter
    @Setter
    private List<String> authors;
    @Getter
    @Setter
    private int pageCount;
    @Getter
    @Setter
    private int chapters;

    public Book(String title, int pageCount, int chapters, List<String> authors, MediaFormat format) {
        super(title, MediaCategory.BOOK, format);
        this.authors = authors;
        this.pageCount = pageCount;
        this.chapters = chapters;
    }
}
