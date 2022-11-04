package com.kellum.MovieCatalogue.exceptions;

import com.kellum.MovieCatalogue.model.Media.MediaCategory;

public class MediaNotFoundException extends RuntimeException {
    public MediaNotFoundException(MediaCategory media, String identifier) {
        super("Couldn't find " + media.getValue() + " with " + identifier);
    }
}
