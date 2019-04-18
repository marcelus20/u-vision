package models.titles;

import models.media.Media;


public abstract class Video extends Title{


    public Video(String id, String name, String year, String media) {
        super(Integer.parseInt(id), name, year, Media.valueOf(media));
    }

    public Video(String id, String name, String year) {
        super(id, name, year);
    }

    public Video(String name, String year, Media media) {
        super(name, year, media);
    }
}
