/**
 * @author: Felipe Mantovani 2017192
 * @date: 02/5/2019
 */
package models.titles;

import static api.Tools.*;
import models.media.Media;

/**
 * Live Concert blueprint. It extends Title and has an attribute called location
 */
public class LiveConcert extends Title {

    private String location;

    /**
     * Complete constructor - Takes all parameters
     * @param id
     * @param name
     * @param year
     * @param media
     * @param available
     * @param location
     */
    public LiveConcert(String id, String name, String year, String media, String available ,String location) {
        super(Integer.parseInt(id), name, year, Media.valueOf(media), Boolean.valueOf(available));
        this.location = location;
    }

    /**
     * Constructor of 5 parameters - Default media is DVD
     * @param id
     * @param name
     * @param year
     * @param available
     * @param location
     */
    public LiveConcert(String id, String name, String year, String available, String location ) {
        super(Integer.parseInt(id), name, year, Media.CD, Boolean.valueOf(available));
        this.location = location;
    }

    /**
     * Constructor of 5 parameters - super constructor takes no ID
     * @param name
     * @param year
     * @param media
     * @param available
     * @param location
     */
    public LiveConcert(String name, String year, Media media, Boolean available, String location) {
        super(name, year, media, available);
        this.location = location;
    }

    /**
     * Saves takes the toString of the instance of this class and saves to a file
     */
    @Override
    public void commitInstance() {
        fileWriter(toString(), directory, id+"");
    }

    /**
     * Returns a string of the instance of this class formatted to be recorded in a file.
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
                location;
    }

}
