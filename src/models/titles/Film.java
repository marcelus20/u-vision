package models.titles;

import api.Tools;
import models.media.Media;

public class Film extends Video {


    private String director;

    public Film(String id, String name, String year, String media, String director) {
        super(id, name, year, media);
        this.director = director;
    }

    public Film(String id, String name, String year, String director) {
        super(id, name, year);
        this.director = director;
    }

    public Film(String name, String year, Media media, String director) {
        super(name, year, media);
        this.director = director;
    }

    @Override
    public void registerInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

    @Override
    public void updateInstance() {

    }
}
