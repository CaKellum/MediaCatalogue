package com.kellum.MovieCatalogue.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Polymorphism(type = PolymorphismType.EXPLICIT)
@NoArgsConstructor
public class Music extends Media {

    @ElementCollection
    @Getter
    @Setter
    private List<String> trackList;
    @ElementCollection
    @Getter
    @Setter
    private List<String> artists;

    Music(String title, List<String> artists, List<String> trackList, MediaFormat format) {
        super(title, MediaCategory.MUSIC, format);
        this.artists = artists;
        this.trackList = trackList;
    }

    public int getTrackCount() {
        return this.trackList.size();
    }

}
