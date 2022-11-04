package com.kellum.MovieCatalogue.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@Entity
@Polymorphism(type = PolymorphismType.EXPLICIT)
public class VideoGame extends Media {
    @AllArgsConstructor
    enum Console {
        PLAYSTATION("playstation", MediaFormat.GAME_DISC), PLAY_STATION_2("playstation 2", MediaFormat.GAME_DISC),
        PLAY_STATION_3("playstation 3", MediaFormat.GAME_DISC),
        PLAY_STATION_4("playstation 4", MediaFormat.GAME_DISC), PLAY_STATION_5("playstation 5", MediaFormat.GAME_DISC),
        NES("nes", MediaFormat.GAME_CARTRIDGE),
        SNES("snes", MediaFormat.GAME_CARTRIDGE), FAMICOM("famicom", MediaFormat.GAME_CARTRIDGE),
        N64("n64", MediaFormat.GAME_CARTRIDGE),
        GAME_CUBE("game cube", MediaFormat.GAME_DISC), WII("wii", MediaFormat.GAME_DISC),
        WIIU("wiiu", MediaFormat.GAME_DISC),
        NIN_SWITCH("switch", MediaFormat.GAME_CARTRIDGE), GAME_BOY("Game Boy", MediaFormat.GAME_CARTRIDGE),
        GAME_BOY_COLOR("game boy color", MediaFormat.GAME_CARTRIDGE),
        GAME_BOY_ADV("game boy advanced", MediaFormat.GAME_CARTRIDGE), NIN_DS("ds", MediaFormat.GAME_CARTRIDGE),
        NIN_3DS("3ds", MediaFormat.GAME_CARTRIDGE),
        SEGA_GENESIS("genesis", MediaFormat.GAME_CARTRIDGE);

        @Getter
        private String value;

        @Getter
        private MediaFormat format;
    }

    @Getter
    private String console;

    public VideoGame(String title, Console console) {
        super(title, MediaCategory.VIDEO_GAME, console.getFormat());
        this.setConsole(console);
    }

    private void setConsole(Console console) {
        this.console = console.getValue();
    }
}
