package de.unibremen.swp.activerecall2.controller;

import de.unibremen.swp.activerecall2.gui.CategoriesWindow;
import de.unibremen.swp.activerecall2.logic.CategoriesLogic;
import de.unibremen.swp.activerecall2.model.Category;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * This class serves as logical connector between CategoriesLogic and CategoriesWindow classes.
 * It handles their communication.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class CategoriesController {
    /**
     * CategoriesWindow - GUI
     */
    private CategoriesWindow categoriesWindow;
    /**
     * CategoriesLogic
     */
    private final CategoriesLogic categoriesLogic;

    /**
     * The constructor of this class
     * @param logic logic is the CategoriesLogic
     */
    public CategoriesController(final CategoriesLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Logic must not be null!");
        }
        categoriesLogic = logic;
    }

    /**
     * It adds categories to GUI
     */
    public void manageCategories() {
        try {
            if (categoriesWindow == null) {
                categoriesWindow = new CategoriesWindow(String.format("%s", "Categories" ), this);
            }
            categoriesWindow.setVisible(true);
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(categoriesWindow, ex.getMessage(), "manageCategories:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It checks if given category name already exists or not
     * @param categoryName categoryName is the category name to check if already
     *                     exists or not
     * @return True, if given category name already exists
     *         False, if given category name not exists
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasCategory(String categoryName) throws SQLException {
        return categoriesLogic.hasCategory(categoryName);
    }

    /**
     * It adds new category
     * @param category category is the new category to add
     * @return 1, if the category successfully added
     *         0, if the category cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addCategory(Category category) throws SQLException {
        if ( categoriesLogic.add(category) ==1) {
            loadCategories();
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It updates the given category
     * @param category category is the category to update
     * @return 1, if the category successfully updated
     *         0, if the category cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int updateCategory(Category category) throws SQLException {
        if ( categoriesLogic.update(category) ==1) {
            loadCategories();
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It deletes given category
     * @param id id is the ID of the category to delete
     * @return  1, if the category successfully deleted
     *          0, if the category cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteCategory(int id) throws SQLException {
        if ( categoriesLogic.delete(id) ==1) {
            loadCategories();
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It checks if the category has related records
     * @param id id is a category id
     * @return true if the category has related records
     *         false if the category doesn't have related records
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean categoryHasRelatedRecords(int id) throws SQLException {
        return categoriesLogic.categoryHasRelatedRecords(id);
    }

    /**
     * It loads categories in to GUI
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadCategories() throws SQLException {
        List<Category> categories= categoriesLogic.getAll();
        categoriesWindow.fillCategories(categories);
    }

}
