/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.titles;

import static api.Tools.*;
import models.media.Media;

/**
 * Blueprint of a film object. It extends Title. It has got a director attribute
 */
public class Film extends Title {


    private String director;

    /**
     * Complete Constructor - takes all parameters
     * @param id
     * @param name
     * @param year
     * @param media
     * @param available
     * @param director
     */
    public Film(String id, String name, String year, String media, String available,String director) {
        super(Integer.parseInt(id), name, year, Media.valueOf(media), Boolean.valueOf(available));
        this.director = director;
    }

    /**
     * Constructor of 4 parameters
     * @param id
     * @param name
     * @param year
     * @param available
     * @param director
     */
    public Film(String id, String name, String year, String available,String director) {
        super(Integer.parseInt(id), name, year, Media.BLUE_RAY, Boolean.valueOf(available));
        this.director = director;
    }

    /**
     * Constructor of 4 parameters  - ID is generated automatically - super constructor takes no ID.
     * @param name
     * @param year
     * @param media
     * @param available
     * @param director
     */
    public Film(String name, String year, Media media, Boolean available,String director) {
        super(name, year, media, available);
        this.director = director;
    }

    /**
     * takes the toString contents of this object and saves into a file
     */
    @Override
    public void commitInstance() {
        fileWriter(toString(), directory, id+"");
    }

    /**
     * Takes the fields and format them in a way to be saved into a file
     * @return
     */
    @Override
    public String toString() {
        return getClass().getName() +"\n"+
                id + "\n"+
                name + "\n"+
                year + "\n"+
                media + "\n"+
                available+"\n"+
                director;
    }
}
