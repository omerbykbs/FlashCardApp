package de.unibremen.swp.activerecall2.logic;
import de.unibremen.swp.activerecall2.model.FlashCard;
import de.unibremen.swp.activerecall2.persistence.DbService;

import java.sql.SQLException;
import java.util.List;

/**
 * This class checks the logic of the matters that are related to glossary.
 *
 * @author Ömer Faruk Büyükbas
 */
public class GlossaryLogic {

    private final DbService db;

    /**
     * The constructor of this class
     * @param db db is the database service
     */
    public GlossaryLogic(DbService db) {
        this.db= db;
    }

    /**
     * It gets the list of all flashcards by a specific order and filter
     * @param orderby orderby is the order
     * @param filter filter is the filter
     * @return the list of all flashcards by a specific order and filter
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<FlashCard> getAll(String orderby,String filter) throws SQLException {
        return db.flashCardGetAll(orderby,filter);
    }

    /**
     * It gets the given flashcard
     * @param id id is the ID of the flashcard to get
     * @return flashcard
     * @throws SQLException if there is a problem with SQL connection
     */
    public FlashCard get(int id) throws SQLException {
        return db.flashCardGet(id);
    }
}
