package models;

/**
 * The registrable interface forces the classes to forces implemented classes to have id and name fields and gives it
 * the getters for the fields.
 *
 * Also, because it is registrable, it is
 */
public interface Registrable {

    /**
     * Getter for the field
     * @return
     */
    Integer getId();

    /**
     * Setter for the field ID
     * @param id
     */
    void setId(Integer id);

    /**
     * Getter for the field name
     * @return
     */
    String getName();

    /**
     * Setter for the field name.
     * @param name
     */
    void setName(String name);

    /**
     * To record the class to a file
     */
    void commitInstance();
}
