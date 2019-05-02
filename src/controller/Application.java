package controller;

/**
 * This interface describe the menu options of the Application system
 */
public interface Application {
    /**
     * Allows user to look for titles
     */
    void lookForTitles();

    /**
     * Allows user to look for Customers
     */
    void lookForMembers();

    /**
     * Allows user to look for rents
     */
    void lookForRents();

    /**
     * Allows user to add new Titles
     */
    void addTitles();

    /**
     * Allows user to add new members to the system.
     */
    void addMembers();

    /**
     * Allow users to update customers from the system.
     */
    void updateMembers();

    /**
     * Allows user to record a rent to the system.
     */
    void recordARent();

    /**
     * Allows user to return a rent
     */
    void returnARent();

    /**
     * the properties of the implemented classes updater.
     */
    void updateFields();
}
