package de.unibremen.swp.activerecall2.controller;

import de.unibremen.swp.activerecall2.gui.FlashCardsWindow;
import de.unibremen.swp.activerecall2.logic.CategoriesLogic;
import de.unibremen.swp.activerecall2.logic.FlashCardsLogic;
import de.unibremen.swp.activerecall2.model.Category;
import de.unibremen.swp.activerecall2.model.FlashCard;
import de.unibremen.swp.activerecall2.model.FlashCardLink;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * This class serves as logical connector between CategoriesLogic, FlashCardsLogic
 * and FlashCardsWindow classes. It handles their communication.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class FlashCardsController {

    private FlashCardsWindow flashCardsWindow;

    private final FlashCardsLogic flashCardsLogic;

    private CategoriesLogic categoriesLogic;

    /**
     * The constructor of this class
     * @param logic logic is the FlashCardLogic
     */
    public FlashCardsController(final FlashCardsLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Logic must not be null!");
        }
        flashCardsLogic = logic;
    }

    /**
     * @return categoriesLogic
     */
    public CategoriesLogic getCategoriesLogic() {
        return categoriesLogic;
    }

    /**
     * It sets CategoriesLogic
     * @param categoriesLogic categoriesLogic is the CategoriesLogic to set
     */
    public void setCategoriesLogic(CategoriesLogic categoriesLogic) {
        this.categoriesLogic = categoriesLogic;
    }

    /**
     * It adds flashcards to GUI
     */
    public void manageFlashCards() {
        try {
            //  final List<String> candidates = businessLogic.addStudi();
            if( flashCardsWindow==null)
            {
                flashCardsWindow = new FlashCardsWindow(String.format("%s", "Flash Cards" ), this);
            }
            flashCardsWindow.setVisible(true);

        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(flashCardsWindow, ex.getMessage(), "manageFlashCards:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It adds new flashcard
     * @param flashCard flashcard is the new flashcard to add
     * @return 1, if flashcard successfully added
     *         0, if flashcard cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addFlashCard(FlashCard flashCard) throws SQLException {
        if (flashCardsLogic.add(flashCard) == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * It updates the given flashcard
     * @param flashCard flashcard is the flashcard to update
     * @return 1, if flashcard successfully updated
     *         0, if flashcard cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int updateFlashCard(FlashCard flashCard) throws SQLException {
        if (flashCardsLogic.update(flashCard) == 1) {
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It deletes the given flashcard
     * @param id id is the ID of the flashcard to delete
     * @return 1, if flashcard successfully deleted
     *         0, if flashcard cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteFlashCard(int id) throws SQLException {
        if ( flashCardsLogic.delete(id) == 1) {
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It adds new link.
     * @param flashCardLink flashCardLink is the new link to add
     * @return 1, if link successfully added
     *         0, if link cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addFlashCardLink(FlashCardLink flashCardLink) throws SQLException {
        if (flashCardsLogic.addLink(flashCardLink) == 1) {
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It deletes link.
     * @param id id is ID of the link to delete
     * @return 1, if link successfully deleted
     *         0, if link cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteFlashCardLink(int id) throws SQLException {
        if (flashCardsLogic.deleteLink(id) == 1) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * It loads flashcards in to GUI.
     * @param category category is the category of the flashcards to load
     * @param orderby orderby is the way of the order the flashcards
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadFlashCards(Category category,String orderby) throws SQLException {
        List<FlashCard> flashCards;
        if(category != null) {
            flashCards= flashCardsLogic.getFlashCardsByCategory(category);
        } else {
            flashCards= flashCardsLogic.getAll(orderby);
        }
        flashCardsWindow.fillFlashCards(flashCards);
    }

    /**
     * It loads categories in to GUI.
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadCategories() throws SQLException {
        List<Category> categories= categoriesLogic.getAll();
        flashCardsWindow.fillCategories(categories);
    }

    /**
     * It loads links in to GUI.
     * @param flashCardId flashCardId is the ID of the flashcard to load its links
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadFlashCardLinks(int flashCardId) throws SQLException {
        List<FlashCardLink> flashCardLinks= flashCardsLogic.getFlashCardLinksByFlashCard(flashCardId);
        flashCardsWindow.fillFlashCardLinks(flashCardLinks);
    }

    /**
     * Checks if there is an existing target flashcard for Link
     * @param flashCardId flashcardId is the id of the flashcard to which a link will be added
     * @param term term is a Link Term
     * @param targetFlashCardId targetFlashCardId is the id of the flashcard to which is the target card for link
     * @return true if there is an available target flashcard
     *         false if there is no available target flashcard
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasTermTargetFlashCard(int flashCardId, String term,int targetFlashCardId) throws SQLException {
        return flashCardsLogic.hasTermTargetFlashCard(flashCardId,term,targetFlashCardId);
    }

    /**
     * It checks if the flashcard has related records
     * @param id id is a flashcard id
     * @return true, if the flashcard has related records
     *         false, if the flashcard doesn't have related records
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean flashCardHasRelatedRecords(int id) throws SQLException {
        return flashCardsLogic.flashCardHasRelatedRecords(id);
    }

    /**
     * It gets the ID of a specific flashcard's category
     * @param id id is the ID of flashcard to get its category ID
     * @return ID of the flashcard's category
     * @throws SQLException if there is a problem with SQL connection
     */
    public int getCategoryIdOfFlashCard(int id) throws SQLException {
        return flashCardsLogic.getCategoryIdOfFlashCard(id);
    }

    /**
     * It checks if the given flashcard has any link
     * @param id id is the ID of flashcard
     * @return true, if the flashcard has link or links
     *         false, if the flashcard has no link
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean isFlashCardToLinkCardId(int id) throws SQLException {
        return flashCardsLogic.isFlashCardToLinkCardId(id);
    }

}
