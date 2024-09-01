package de.unibremen.swp.activerecall2.logic;

import de.unibremen.swp.activerecall2.model.Learning;
import de.unibremen.swp.activerecall2.model.LearningStatus;
import de.unibremen.swp.activerecall2.persistence.DbService;

import java.sql.SQLException;
import java.util.List;

/**
 * This class checks the logic of the matters that are related to learning exercises selection.
 *
 * @author Ömer Faruk Büyükbas
 */
public class LearningExercisesSelectionLogic {

    private final DbService db;

    /**
     * The constructor of this class
     * @param db db is the database service
     */
    public  LearningExercisesSelectionLogic(DbService db) {
        this.db= db;
    }

    /**
     * It gets the list of all learnings with given status
     * @param status status is the status
     * @return the list of all learnings with given status
     *         null, if the status is false
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<Learning> getAllLearnings(LearningStatus status) throws SQLException {
        if (status == LearningStatus.ALL) {
            return db.learningGetAll();
        } else if (status == LearningStatus.PREPARED) {
            return db.learningGetAllStatusPrepared();
        } else if (status == LearningStatus.STARTED) {
            return db.learningGetAllStatusStarted();
        } else if (status == LearningStatus.COMPLETED) {
            return db.learningGetAllStatusCompleted();
        } else {
            return null;
        }
    }
}

