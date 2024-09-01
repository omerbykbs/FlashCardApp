package de.unibremen.swp.activerecall2.logic;

import de.unibremen.swp.activerecall2.model.Category;
import de.unibremen.swp.activerecall2.persistence.DbService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * This class checks the logic of the matters that are related to category like add,
 * delete and update.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class CategoriesLogic {

    private final DbService db;

    /**
     * The constructor of this class
     * @param db db is the database service
     */
    public CategoriesLogic(DbService db) {
        this.db= db;
    }

    /**
     * It adds new category
     * @param category category is the category to add
     * @return 1, if the category successfully added
     *         0, if the category cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int add(Category category) throws SQLException {
        return db.categoryAdd(category);
    }

    /**
     * It updates the given category
     * @param category category is the category to update
     * @return 1, if category successfully updated
     *         0, if category cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int update(Category category) throws SQLException {
        return db.categoryUpdate(category);
    }

    /**
     * It deletes the given category
     * @param id id is the ID of the category to delete
     * @return 1, if category successfully deleted
     *         0, if category cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int delete(int id) throws SQLException {
        return db.categoryDelete(id);
    }

    /**
     * It gets the list of all categories
     * @return the list of all categories
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<Category> getAll() throws SQLException {
        return db.categoryGetAll();
    }

    /**
     * It gets the given category
     * @param id id is the ID of the category to get
     * @return category
     * @throws SQLException if there is a problem with SQL connection
     */
    public Category get(int id) throws SQLException {
        return db.categoryGet(id);
    }

    /**
     * It checks if the given category name already exists
     * @param categoryName categoryName is the name of the category to check
     * @return true, if the given category name already exists
     *         false, if the given category name not exists
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasCategory(String categoryName) throws SQLException {
        return db.hasCategory(categoryName);
    }

    /**
     * It checks if the given category has related records
     * @param id id is the ID of the category to check
     * @return true, if the category has related records
     *         false, if the category has not related records
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean categoryHasRelatedRecords(int id) throws SQLException {
        return db.categoryHasRelatedRecords(id);
    }
}


