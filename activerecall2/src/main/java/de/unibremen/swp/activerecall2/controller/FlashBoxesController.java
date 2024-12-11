package de.unibremen.swp.activerecall2.controller;

import de.unibremen.swp.activerecall2.gui.FlashBoxesWindow;
import de.unibremen.swp.activerecall2.logic.CategoriesLogic;
import de.unibremen.swp.activerecall2.logic.FlashBoxesLogic;
import de.unibremen.swp.activerecall2.logic.FlashCardsLogic;
import de.unibremen.swp.activerecall2.model.Category;
import de.unibremen.swp.activerecall2.model.FlashBox;
import de.unibremen.swp.activerecall2.model.FlashBoxDetail;
import de.unibremen.swp.activerecall2.model.FlashCard;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * This class serves as logical connector between FlashBoxesLogic, CategoriesLogic,
 * FlashCardsLogic and FlashBoxesWindow classes. It handles their communication.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class FlashBoxesController {

    private FlashBoxesWindow flashBoxesWindow;

    private final FlashBoxesLogic flashBoxesLogic;

    private CategoriesLogic categoriesLogic;

    private FlashCardsLogic flashCardsLogic;

    /**
     * The constructor of this class
     * @param logic logic is the FlashBoxesLogic
     */
    public FlashBoxesController(final FlashBoxesLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Logic must not be null!");
        }
        flashBoxesLogic = logic;
    }

    /**
     * It sets CategoriesLogic.
     * @param categoriesLogic categoriesLogic is CategoriesLogic to set
     */
    public void setCategoriesLogic(CategoriesLogic categoriesLogic) {
        this.categoriesLogic = categoriesLogic;
    }

    /**
     * @param flashCardsLogic flashCardsLogic is FlashCardsLogic to set
     */
    public void setFlashCardsLogic(FlashCardsLogic flashCardsLogic) {
        this.flashCardsLogic = flashCardsLogic;
    }

    /**
     * It adds flash boxes to GUI
     */
    public void manageFlashBoxes() {
        try {
            if (flashBoxesWindow == null) {
                flashBoxesWindow = new FlashBoxesWindow(String.format("%s", "Card Boxes"), this);
            }
            flashBoxesWindow.setVisible(true);
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(flashBoxesWindow, ex.getMessage(), "manageFlashBoxes:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It checks if given flash box name already exists or not
     * @param flashBoxName flashBoxName is the flash box name to check if already
     *                     exists or not
     * @return True, if given flash box name already exists
     *         False, if given flash box name not exists
     * @throws SQLException if there is a problem with SQL connection
     */

    public boolean hasFlashBox(String flashBoxName) throws SQLException {
        return flashBoxesLogic.hasFlashBox(flashBoxName);
    }

    /**
     * It checks if given category has any flashcards or not
     * @param categoryId categoryId is the ID of the category to check
     * @return True, if category has flashcards
     *         False, if category has no flashcards
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasCategoryFlashCards(int categoryId) throws SQLException {
        List<FlashCard> flashCards= flashCardsLogic.getFlashCardsByCategory(categoryId);
        return  flashCards!=null && flashCards.size()>0;
    }

    /**
     * It adds new flash box
     * @param flashBox flashBox is the new flash box to add
     * @return 1, if flash box successfully added
     *         0, if flash box cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addFlashBox(FlashBox flashBox) throws SQLException {
        if (flashBoxesLogic.addFlashBox(flashBox) == 1) {
            loadFlashBoxes();
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * It updates the given flash box
     * @param flashBox flashBox is the flash box to update
     * @return 1, if flash box successfully updated
     *         0, if flash box cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int updateFlashBox(FlashBox flashBox) throws SQLException {
        if (flashBoxesLogic.updateFlashBox(flashBox) == 1) {
            loadFlashBoxes();
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * It deletes the given flash box
     * @param id id is the ID of the flash box to delete
     * @return 1, if flash box successfully deleted
     *         0, if flash box cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteFlashBox(int id) throws SQLException {
        if (flashBoxesLogic.deleteFlashBox(id) == 1) {
            loadFlashBoxes();
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * It checks if the given flash box already has the given category
     * @param flashBoxId flashBoxId is the ID of the flash box
     * @param categoryId categoryId is the ID of the category
     * @return True, if the flash box already has the category
     *         False, if the flash box has not the category
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasFlashBoxDetail(int flashBoxId, int categoryId) throws SQLException {
        return flashBoxesLogic.hasFlashBoxDetail(flashBoxId, categoryId);
    }

    /**
     * It adds flash box detail
     * @param flashBoxDetail flashBoxDetail is the flash box detail to add
     * @return 1, if the flash box detail successfully added
     *         0, if the flash box detail cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addFlashBoxDetail(FlashBoxDetail flashBoxDetail) throws SQLException {
        if (flashBoxesLogic.addFlashBoxDetail(flashBoxDetail) == 1) {
            loadFlashBoxDetails(flashBoxDetail.getFlashBoxId());
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * It deletes flash box details
     * @param id id is the flash box detail to delete
     * @return 1, if the flash box detail successfully deleted
     *         0, if the flash box detail cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteFlashBoxDetail(int id) throws SQLException {
        FlashBoxDetail flashBoxDetail = flashBoxesLogic.getFlashBoxDetail(id);
        if (flashBoxesLogic.deleteFlashBoxDetail(id) == 1) {
            loadFlashBoxDetails(flashBoxDetail.getFlashBoxId());
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * It loads categories in to combo box
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadCategoriesComboBox() throws SQLException {
        List<Category> categories = categoriesLogic.getAll();
        flashBoxesWindow.fillCategoriesComboBox(categories);
    }

    /**
     * It loads flash boxes in to GUI
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadFlashBoxes() throws SQLException {
        List<FlashBox> flashBoxes = flashBoxesLogic.getAllFlashBoxes();
        flashBoxesWindow.fillFlashBoxes(flashBoxes);
    }

    /**
     * It loads flash box details in to GUI
     * @param flashBoxId flashBoxId is the flash box of details to load
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadFlashBoxDetails(int flashBoxId) throws SQLException {
        List<FlashBoxDetail> flashBoxDetails = flashBoxesLogic.getAllFlashBoxDetails(flashBoxId);
        flashBoxesWindow.fillFlashBoxDetails(flashBoxDetails);
    }

    /**
     * It checks if the flash box has related records
     * @param id id is a flash box id
     * @return true if the flash box has related records
     *         false if the flash box doesn't have related records
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean flashBoxHasRelatedRecords(int id) throws SQLException {
        return flashBoxesLogic.flashBoxHasRelatedRecords(id);
    }

    /**
     * It loads flashcards by category in to GUI
     * @param categoryId categoryId is the ID of the category to load in GUI with flashcards
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadFlashCards(int categoryId) throws SQLException {
        List<FlashCard> flashCards;
        Category category = categoriesLogic.get(categoryId);
        flashCards = flashCardsLogic.getFlashCardsByCategory(category);
        flashBoxesWindow.fillFlashCards(flashCards);
    }

}
