/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.titles;

import api.Tools;
import models.media.Media;

/**
 * Blueprint of a Boxset Title. It extends Title class
 */
public class BoxSet extends Title{

    /**
     * Attribute Season of the Boxset
     */
    private Integer season;

    /**
     * Complete Constructor - takes all parameters
     * @param id
     * @param name
     * @param year
     * @param media
     * @param available
     * @param season
     */
    public BoxSet(String id, String name, String year, String media, String available,String season) {
        super(Integer.parseInt(id), name, year, Media.valueOf(media), Boolean.valueOf(available));
        this.season = Integer.parseInt(season);
    }


    /**
     * Constructor of 5 parameters - Default Media is DVD for boxsets
     * @param id
     * @param name
     * @param year
     * @param available
     * @param season
     */
    public BoxSet(String id, String name, String year, String available,String season) {
        super(Integer.parseInt(id), name, year, Media.DVD, Boolean.valueOf(available));
        this.season = Integer.parseInt(season);
    }


    /**
     * Constructor of 4 parameters (All parameters are strings), season default is 1
     * @param id
     * @param name
     * @param year
     * @param available
     */
    public BoxSet(String id, String name, String year, String available) {
        super(Integer.parseInt(id), name, year, Media.DVD, Boolean.valueOf(available));
        season = getDefaultSeason();
    }

    /**
     * Constructor of 4 parameters - default season is 1
     * @param name
     * @param year
     * @param media
     * @param available
     */
    public BoxSet(String name, String year, Media media, Boolean available) {
        super(name, year, media, available);
        season = getDefaultSeason();
    }

    /**
     * returns the value of 1 as a default value for season
     * @return 1
     */
    private Integer getDefaultSeason() {
        return 1;
    }

    /**
     * Saves the return of the toString to a file
     */
    @Override
    public void commitInstance() {
        Tools.fileWriter(toString(), directory, id+"");
    }

    /**
     * returns a formatted string to save content of this object to a file
     * @return
     */
    @Override
    public String toString() {
        return  this.getClass().getName()+"\n"+
                id +"\n"+
                name +"\n"+
                year +"\n"+
                media +"\n"+
                available+"\n"+
                season;
    }
}
