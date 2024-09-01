package de.unibremen.swp.activerecall2.logic;

import de.unibremen.swp.activerecall2.model.Learning;
import de.unibremen.swp.activerecall2.model.LearningDetail;
import de.unibremen.swp.activerecall2.model.Level;
import de.unibremen.swp.activerecall2.persistence.DbService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class checks the logic of the matters that are related to learning exercises like add,
 * delete and update.
 *
 * @author Ömer Faruk Büyükbas
 */
public class LearningExercisesLogic {

    private final DbService db;

    /**
     * The constructor of this class
     * @param db db is the database service
     */
    public LearningExercisesLogic(DbService db) {
        this.db = db;
    }

    public int addLearningDetail(LearningDetail learningDetail) throws SQLException {
        return db.learningDetailsAdd(learningDetail);
    }

    /**
     * It updates the given learning detail
     * @param learningDetail learningDetail is the learning detail to update
     * @return 1, if the learning detail successfully updated
     *         0, if the learning detail cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int updateLearningDetail(LearningDetail learningDetail) throws SQLException {
        return db.learningDetailsUpdate(learningDetail);
    }

    /**
     * It gets the list of all learning details of the given learning
     * @param learningId learningId is the ID of the learning
     * @return the list of all learning details of the learning
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<LearningDetail> getAllAvailableLearningDetails(int learningId) throws SQLException {
        List<Level> levels = db.getLevels();
        Learning learning = db.learningGet(learningId);
        LocalDateTime startDate = LocalDateTime.parse(learning.getStartDate());
        LocalDateTime now = LocalDateTime.now();
        int maxlevelId = 1;
        System.out.println("Start Date: " + startDate.toString());
        for (Level level : levels) {
            if (startDate.plusDays(level.getDaysStart()).compareTo(now) < 0 && !level.getName().equals("completed")) {
                System.out.println("Start Date Plus level"+ level.getLevelId() + " : " + startDate.plusDays(level.getDaysStart()).toString());
                maxlevelId = level.getLevelId();
            }
        }
        return db.learningDetailsGetAllLevelLessThanOrEqual(learningId, maxlevelId);
    }

    /**
     * It gets the learning detail of the given learning
     * @param id id is the learning
     * @return learning detail
     * @throws SQLException if there is a problem with SQL connection
     */
    public LearningDetail get(int id) throws SQLException {
        return db.learningDetailsGet(id);
    }

    /**
     * It gets the given level
     * @param levelId levelId is the level to get
     * @return level
     */
    public Level getLevel(int levelId) {
        return db.getLevel(levelId);
    }

}

