package de.unibremen.swp.activerecall2.controller;

import de.unibremen.swp.activerecall2.gui.GlossaryWindow;
import de.unibremen.swp.activerecall2.logic.CategoriesLogic;
import de.unibremen.swp.activerecall2.logic.GlossaryLogic;
import de.unibremen.swp.activerecall2.model.Category;
import de.unibremen.swp.activerecall2.model.FlashCard;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * This class serves as logical connector between CategoriesLogic, GlossaryLogic and
 * GlossaryWindow classes. It handles their communication.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class GlossaryController {

    private GlossaryWindow glossaryWindow;

    private final GlossaryLogic glossaryLogic;

    private CategoriesLogic categoriesLogic;

    /**
     * The constructor of this class
     * @param logic logic is the GlossaryLogic
     */
    public GlossaryController(final GlossaryLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Logic must not be null!");
        }
        glossaryLogic = logic;
    }

    /**
     * It sets the logic of the categories
     * @param categoriesLogic categoriesLogic is the logic of the categories
     */
    public void setCategoriesLogic(CategoriesLogic categoriesLogic) {
        this.categoriesLogic = categoriesLogic;
    }

    /**
     * It adds glossary to GUI
     */
    public void manageGlossary() {
        try {
            if(glossaryWindow == null) {
                glossaryWindow = new GlossaryWindow(String.format("%s", "Glossary" ), this);
            }
            glossaryWindow.setVisible(true);
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(glossaryWindow, ex.getMessage(), "manageGlossary:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It loads glossary in GUI
     * @param orderby orderby is the way to sort glossary
     * @param filter filter is the way to filter glossary
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadFlashCards(String orderby, String filter) throws SQLException {
        List<FlashCard> flashCards;
        flashCards = glossaryLogic.getAll(orderby,filter);
        glossaryWindow.fillFlashCards(flashCards);
    }

    /**
     * It loads categories in GUI
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadCategories() throws SQLException {
        List<Category> categories= categoriesLogic.getAll();
        glossaryWindow.fillCategories(categories);
    }

}
