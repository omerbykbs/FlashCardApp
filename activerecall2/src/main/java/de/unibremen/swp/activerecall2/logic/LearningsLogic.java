package de.unibremen.swp.activerecall2.logic;

import de.unibremen.swp.activerecall2.model.FlashCard;
import de.unibremen.swp.activerecall2.model.Learning;
import de.unibremen.swp.activerecall2.model.LearningDetail;
import de.unibremen.swp.activerecall2.model.LearningStatus;
import de.unibremen.swp.activerecall2.persistence.DbService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * This class checks the logic of the matters that are related to learning like add,
 * delete and update.
 *
 * @author Ömer Faruk Büyükbas
 */
public class LearningsLogic {

    private final DbService db;

    /**
     * The constructor of this class
     * @param db db is the database service
     */
    public  LearningsLogic(DbService db) {
        this.db= db;
    }

    /**
     * It adds new learning
     * @param learning learning is the learning to add
     * @return 1, if learning successfully added
     *         0, if learning cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int add(Learning learning) throws SQLException {
        return db.learningAdd(learning);
    }

    /**
     * It updates the given learning
     * @param learning learning is the learning to update
     * @return 1, if learning successfully updated
     *         0, if learning cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int update(Learning learning) throws SQLException {
        return db.learningUpdate(learning);
    }

    /**
     * It deletes the given learning
     * @param id id is the ID of the learning to delete
     * @return 1, if learning successfully deleted
     *         0, if learning cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int delete(int id) throws SQLException {
        return db.learningDelete(id);
    }

    /**
     * It adds learning details for a given flashbox
     * @param learningId learningId is the ID of the learning
     * @param flashBoxId flashBoxId is the ID of the flashbox
     * @param random random is if the flashbox shuffled or not
     * @return the size of the list of flashcards, if learning has detail
     *         0, if learning has no detail
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addAllLearningDetailsFor(int learningId, int flashBoxId,boolean random) throws SQLException {
        if (!db.hasLearningDetails(learningId)) {
            List<FlashCard> flashCards;
            if (!random) {
                flashCards= db.flashBoxGetAllFlashCards(flashBoxId,"order by question");
            } else {
                flashCards= db.flashBoxGetAllFlashCards(flashBoxId);
                Collections.shuffle(flashCards);
            }
            LearningDetail learningDetail;
            for (FlashCard flashCard : flashCards) {
                learningDetail = new LearningDetail(learningId, flashCard.getFlashCardId(), "", 1);
                db.learningDetailsAdd(learningDetail);
            }
            return flashCards.size();
        }
        else {
            return 0;
        }
    }

    /**
     * It deletes all details for the given learning
     * @param learningId learningId is the ID of the learning
     * @return 1, if details successfully deleted
     *         0, if details cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int removeAllLearningDetailsFor(int learningId) throws SQLException {
        return db.learningDetailsRemoveAllFor(learningId);
    }

    /**
     * It gets the list of all learnings with given status
     * @param status status is the status
     * @return list of all learning with given status
     *         null, if status is false
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

    /**
     * It gets the list of all learning details of the given learning
     * @param learningId learningId is the ID of the learning
     * @return the list of all learning details of the given learning
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<LearningDetail> getAllLearningDetails(int learningId) throws SQLException {
        return db.learningDetailsGetAll(learningId);
    }

    /**
     * It gets the given learning
     * @param id id is the ID of the learning
     * @return learning
     * @throws SQLException if there is a problem with SQL connection
     */
    public Learning get(int id) throws SQLException {
        return db.learningGet(id);
    }

    /**
     * It checks if the given learning title already exists or not
     * @param title is the title to check
     * @return true, if the given title already exists
     *         false, if the given title not exists
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasTitle(String title) throws SQLException {
        return db.hasLearningTitle(title);
    }

    /**
     * It checks if the given learning has detail or not
     * @param learningId learningId is the ID of the learning
     * @return true, if given learning has detail
     *         false, if given learning has no detail
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasLearningDetails(int learningId) throws SQLException {
        return db.hasLearningDetails(learningId);
    }

    /**
     * It checks if the update process of the given learning is completed or not
     * @param learning learning is the learning to check
     * @return true, if update is completed
     *         false, if update is not completed
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean updateLearningIfCompleted(Learning learning) throws SQLException {
        if (learning.getCompletionDate() == null || learning.getCompletionDate().isEmpty()) {
            List<LearningDetail> learningDetails=  db.learningDetailsGetAllLevelLessThanOrEqual(learning.getLearningId(),3);
            if (learningDetails.size() == 0) {
                learning.setCompletionDate(LocalDateTime.now().toString());
                learning.setResumeLearningDetailId(0);
                db.learningUpdate(learning);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * It checks if the given learning has started
     * @param id id is the ID of the learning
     * @return true, if learning has started
     *         false, if learning has not started
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean isLearningsStarted(int id) throws SQLException {
        List<Learning> learningsList = db.learningGetAllStatusStarted();
        for (Learning learning: learningsList) {
            if (learning.getLearningId() == id) {
                return true;
            }
        }
        return false;
    }
}

