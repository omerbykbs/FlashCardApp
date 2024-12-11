package de.unibremen.swp.activerecall2.controller;

import de.unibremen.swp.activerecall2.gui.LearningExercisesWindow;
import de.unibremen.swp.activerecall2.logic.FlashCardsLogic;
import de.unibremen.swp.activerecall2.logic.LearningExercisesLogic;
import de.unibremen.swp.activerecall2.logic.LearningsLogic;
import de.unibremen.swp.activerecall2.model.FlashCardLink;
import de.unibremen.swp.activerecall2.model.Learning;
import de.unibremen.swp.activerecall2.model.LearningDetail;
import de.unibremen.swp.activerecall2.model.Level;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * This class serves as logical connector between LearningExercisesLogic, FlashCardsLogic,
 * LearningsLogic and LearningExercisesWindow classes. It handles their communication.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class LearningExercisesController {

    private LearningExercisesWindow learningExercisesWindow;

    private final LearningExercisesLogic learningExercisesLogic;
    private FlashCardsLogic flashCardsLogic;
    private LearningsLogic learningsLogic;

    /**
     * The constructor of this class
     * @param logic logic is the LearningExercisesLogic
     */
    public LearningExercisesController(final LearningExercisesLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Logic must not be null!");
        }
        learningExercisesLogic = logic;
    }

    /**
     * It sets the logic of the flashcards
     * @param flashCardsLogic flashCardsLogic is the logic of the flashcards
     */
    public void setFlashCardsLogic(FlashCardsLogic flashCardsLogic) {
        this.flashCardsLogic = flashCardsLogic;
    }

    /**
     * It gets the logic of the learning
     * @return learningsLogic
     */
    public LearningsLogic getLearningsLogic() {
        return learningsLogic;
    }

    /**
     * It sets the logic of the learning
     * @param learningsLogic learningLogic is the logic of the learning
     */
    public void setLearningsLogic(LearningsLogic learningsLogic) {
        this.learningsLogic = learningsLogic;
    }

    /**
     * It adds learning exercises in GUI
     * @param learning learning is the learning
     */
    public void manageLearningExercises(Learning learning) {
        try {
            if (learningExercisesWindow == null) {
                learningExercisesWindow = new LearningExercisesWindow(String.format("%s", "Learning Exercises" ), this, learning);
            } else {
                learningExercisesWindow.setLearning(learning);
            }
            learningExercisesWindow.setVisible(true);
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(learningExercisesWindow, ex.getMessage(), "manageLearningExercises:Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It updates the learning details
     * @param learningDetail learningDetail is the learning details to update
     * @return 1, if learning details successfully updated
     *         0, if learning details cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int updateLearningDetail(LearningDetail learningDetail) throws SQLException {
        if (learningExercisesLogic.updateLearningDetail(learningDetail) == 1) {
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It gets the level
     * @param levelId levelId is the ID of the level
     * @return level
     */
    public Level getLevel(int levelId) {
        return learningExercisesLogic.getLevel(levelId);
    }

    /**
     * It loads learning details in to memory
     * @param learningId learningId is the ID of the learning to load its details in to memory
     * @return 1, if the learning details successfully loaded in to memory
     *         0, if the learning details cannot be loaded in to memory
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<LearningDetail> loadLearningDetailsToMemory(int learningId) throws SQLException {
        return learningExercisesLogic.getAllAvailableLearningDetails(learningId);
    }

    /**
     * It loads the links of the flashcard in GUI
     * @param flashCardId flashCardId is the ID of the flashcard to load its links
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadFlashCardLinks(int flashCardId) throws SQLException {
        List<FlashCardLink> flashCardLinks= flashCardsLogic.getFlashCardLinksByFlashCard(flashCardId);
        learningExercisesWindow.fillFlashCardLinks(flashCardLinks);
    }
}
