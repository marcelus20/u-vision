package controller;

/**
 * This interface describe the menu options of the Application system
 */
public interface Application {
    /**
     * Allows user to look for titles
     */
    void searchForTitles();

    /**
     * Allows user to look for Customers
     */
    void searchForCustomers();

    /**
     * Allows user to look for rents
     */
    void searchRents();

    /**
     * Allows user to add new Titles
     */
    void addNewTitles();

    /**
     * Allows user to add new members to the system.
     */
    void addNewCustomers();

    /**
     * Allow users to update customers from the system.
     */
    void updateCustomers();

    /**
     * Allows user to record a rent to the system.
     */
    void performARent();

    /**
     * Allows user to return a rent
     */
    void performARentReturn();

    /**
     * the properties of the implemented classes updater.
     */
    void updateFields();
}
