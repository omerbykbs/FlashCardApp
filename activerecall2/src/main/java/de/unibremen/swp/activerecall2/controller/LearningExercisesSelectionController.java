package de.unibremen.swp.activerecall2.controller;

import de.unibremen.swp.activerecall2.gui.LearningExercisesSelectionWindow;
import de.unibremen.swp.activerecall2.logic.LearningExercisesSelectionLogic;
import de.unibremen.swp.activerecall2.logic.LearningsLogic;
import de.unibremen.swp.activerecall2.model.Learning;
import de.unibremen.swp.activerecall2.model.LearningStatus;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * This class serves as logical connector between LearningsLogic, LearningExercisesController,
 * LearningExercisesSelectionLogic and LearningExercisesSelectionWindow classes. It handles
 * their communication.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class LearningExercisesSelectionController {

    private LearningExercisesSelectionWindow learningExercisesSelectionWindow;

    private LearningExercisesController learningExercisesController;

    private final LearningExercisesSelectionLogic learningExercisesSelectionLogic;

    private LearningsLogic learningsLogic;

    /**
     * The constructor of this class
     * @param logic logic is the LearningExercisesSelectionLogic
     */
    public LearningExercisesSelectionController(final LearningExercisesSelectionLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Logic must not be null!");
        }
        learningExercisesSelectionLogic = logic;
    }

    /**
     * It sets the controller of the learning exercises
     * @param learningExercisesController learningExercisesController is the controller of the
     *                                    learning exercises
     */
    public void setLearningExercisesController(LearningExercisesController learningExercisesController) {
        this.learningExercisesController = learningExercisesController;
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
     * @param learningsLogic learningsLogic is the logic of the learning
     */
    public void setLearningsLogic(LearningsLogic learningsLogic) {
        this.learningsLogic = learningsLogic;
    }

    /**
     * It adds learning exercises selection to GUI
     */
    public void manageLearningExercisesSelections() {
        try {
            if (learningExercisesSelectionWindow == null) {
                learningExercisesSelectionWindow = new LearningExercisesSelectionWindow (String.format("%s", "Learning Exercises Selection" ), this, learningExercisesController);
            }
            learningExercisesSelectionWindow.setVisible(true);
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(learningExercisesSelectionWindow, ex.getMessage(), "manageLearningExercisesSelections:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It adds learning exercises to GUI
     * @param learning learning is the learning to add its exercises to GUI
     */
    public void manageLearningExercises(Learning learning) {
        try {
            learningExercisesController.manageLearningExercises(learning);
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(learningExercisesSelectionWindow, ex.getMessage(), "manageLearningExercisesSelections:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It loads learning
     * @param status status is the status of the learning
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadLearnings(LearningStatus status) throws SQLException {
        List<Learning> learnings = learningExercisesSelectionLogic.getAllLearnings(status);
        learningExercisesSelectionWindow.fillLearnings(learnings);
    }

}
