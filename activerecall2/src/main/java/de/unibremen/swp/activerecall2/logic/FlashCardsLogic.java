package de.unibremen.swp.activerecall2.logic;

import de.unibremen.swp.activerecall2.model.Category;
import de.unibremen.swp.activerecall2.model.FlashCard;
import de.unibremen.swp.activerecall2.model.FlashCardLink;
import de.unibremen.swp.activerecall2.persistence.DbService;

import java.sql.SQLException;
import java.util.List;

/**
 * This class checks the logic of the matters that are related to flashcards like add,
 * delete and update.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashCardsLogic {

    private final DbService db;

    /**
     * The constructor of this class
     * @param db db is the database service
     */
    public FlashCardsLogic(DbService db) {
        this.db= db;
    }

    /**
     * It adds new flashcard
     * @param flashCard flashCard is the flashcard to add
     * @return 1, if flashcard successfully added
     *         0, if flashcard cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int add(FlashCard flashCard) throws SQLException {
        return db.flashCardAdd(flashCard);
    }

    /**
     * It updates the given flashcard
     * @param flashCard flashCard is the flashcard to update
     * @return 1, if flashcard successfully updated
     *         0, if flashcard cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int update(FlashCard flashCard) throws SQLException {
        return db.flashCardUpdate(flashCard);
    }

    /**
     * It deletes the given flashcard
     * @param id id is the ID of the flashcard to delete
     * @return 1, if flashcard successfully deleted
     *         0, if flashcard cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int delete(int id) throws SQLException {
        return db.flashCardDelete(id);
    }

    /**
     * It gets the list of all flashcards by a specific order
     * @param orderby orderby is the way to order flashcards
     * @return the list of all flashcards by a specific order
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<FlashCard> getAll(String orderby) throws SQLException {
        return db.flashCardGetAll(orderby);
    }

    /**
     * It gets the list of all flashcards of the given category
     * @param category category is the category to get its flashcards
     * @return the list of all flashcard of the given category
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<FlashCard> getFlashCardsByCategory(Category category) throws SQLException {
        return db.flashCardGetByCategory(category);
    }

    /**
     * It gets the list of all flashcards of the given category
     * @param categoryId categoryId is the ID of the category to get its flashcards
     * @return the list of all flashcard of the given category
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<FlashCard> getFlashCardsByCategory(int categoryId) throws SQLException {
        return db.flashCardGetByCategory(categoryId);
    }

    /**
     * It gets the ID of the category the given flashcard is in
     * @param id id is the ID of the flashcard
     * @return the ID of the category
     * @throws SQLException if there is a problem with SQL connection
     */
    public int getCategoryIdOfFlashCard(int id) throws SQLException {
        return db.getCategoryIdOfFlashCard(id);
    }

    /**
     * It gets the flashcard
     * @param id id is the ID of the flashcard to get
     * @return flashcard
     * @throws SQLException if there is a problem with SQL connection
     */
    public FlashCard get(int id) throws SQLException {
        return db.flashCardGet(id);
    }

    /**
     * It adds a new link
     * @param flashCardLink flashCardLink is the link to add
     * @return 1, if link successfully added
     *         0, if link cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addLink(FlashCardLink flashCardLink) throws SQLException {
        return db.flashCardLinkAdd(flashCardLink);
    }

    /**
     * It deletes the given flashcard link
     * @param id id is the ID of the flashcard link
     * @return 1, if link successfully deleted
     *         0, if link cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteLink(int id) throws SQLException {
        return db.flashCardLinkDelete(id);
    }

    /**
     * It gets the list of all flashcard links of the given flashcard has
     * @param flashCardId flashCardId is the ID of the flashcard
     * @return the list of all flashcard link of the flashcard has
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<FlashCardLink> getFlashCardLinksByFlashCard(int flashCardId) throws SQLException {
        return db.flashCardLinkGetAllByFlashCard(flashCardId);
    }

    /**
     * It checks if the given flashcard a term and target flashcard
     * @param flashCardId flashCardId is the ID of the flashcard to check
     * @param term term is the term
     * @param targetFlashCardId targetFlashCardId is the ID of the target flashcard
     * @return true, if the flashcard has term and target flashcard
     *         false, if the flashcard has no term and target flashcard
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasTermTargetFlashCard(int flashCardId, String term, int targetFlashCardId) throws SQLException {
        return db.hasTermTargetFlashCard(flashCardId,term,targetFlashCardId);
    }

    /**
     * It checks if the given flashcard has related records
     * @param id id is the ID of the flashcard to check
     * @return true, if the flashcard has related records
     *         false, if the flashcard has no related records
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean flashCardHasRelatedRecords(int id) throws SQLException {
        return db.flashCardHasRelatedRecords(id);
    }

    /**
     * It checks if the given target flashcard id is valid
     * @param id id of the target flashcard to check
     * @return true, if the given target flashcard id is valid
     *         false, if the given target flashcard is not valid
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean isFlashCardToLinkCardId(int id) throws SQLException {
        List<FlashCard> flashCardList = db.flashCardGetAll("","");
        for (FlashCard flashCard : flashCardList) {
            if (flashCard.getFlashCardId() == id) {
                return true;
            }
        }
        return false;
    }
}

