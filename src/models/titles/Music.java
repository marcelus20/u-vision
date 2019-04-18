package models.titles;

import api.Tools;
import models.media.Media;

public class Music extends Title{

    public Music(Integer id, String name, String year, Media media) {
        super(id, name, year, media);
    }


    public Music(String id, String name, String year) {
        super(id, name, year);
    }

    public Music(String name, String year, Media media) {
        super(name, year, media);
    }

    @Override
    public void registerInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

    @Override
    public void updateInstance() {

    }
}
