package com.kellum.MovieCatalogue.model;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.kellum.MovieCatalogue.logging.KellumLogger;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@ToString(includeFieldNames = true)
@EqualsAndHashCode
@NoArgsConstructor
public abstract class Media {

    @AllArgsConstructor
    public enum MediaFormat {
        DVD("dvd"), GAME_DISC("game_disc"), GAME_CARTRIDGE("game cartridge"),
        BLU_RAY("blu-ray"), CD("cd"), CASSETTE("cassette"),
        VINYL("vinyl"), HARD_BACK("hard_back"), PAPER_BACK("paper_back"),
        AUDIO_BOOK("audio_book"), VHS("vhs");

        @Getter
        private String value;
    }

    @AllArgsConstructor
    public enum MediaCategory {
        MOVIE("movie"),
        VIDEO_GAME("video_game"),
        MUSIC("music"),
        TV_SHOW("tv_show"),
        BOOK("book");

        @Getter
        private String value;
    }

    @Getter
    @Setter
    String title;
    @Getter
    String category;
    @Getter
    String format;

    Media(String title, MediaCategory category, MediaFormat format) {
        this.setCategory(category);
        this.setTitle(title);
        this.setFormat(format);
        KellumLogger.shared.logUsage("New " + this.toString() + "Created");
    }

    public void setCategory(MediaCategory category) {
        this.category = category.getValue();
    }

    public void setFormat(MediaFormat format) {
        this.format = format.getValue();
    }
}