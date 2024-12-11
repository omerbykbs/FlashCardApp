package de.unibremen.swp.activerecall2.controller;

import de.unibremen.swp.activerecall2.gui.LearningsWindow;
import de.unibremen.swp.activerecall2.logic.FlashBoxesLogic;
import de.unibremen.swp.activerecall2.logic.FlashCardsLogic;
import de.unibremen.swp.activerecall2.logic.LearningsLogic;
import de.unibremen.swp.activerecall2.model.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * This class serves as logical connector between LearningsLogic, FlashBoxesLogic,
 * FlashCardsLogic and LearningsWindow classes. It handles their communication.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class LearningController {

    private LearningsWindow learningsWindow;
    private final LearningsLogic learningsLogic;
    private FlashBoxesLogic flashBoxesLogic;
    private FlashCardsLogic flashCardsLogic;

    /**
     * The constructor of this class
     * @param logic logic is the LearningsLogic
     */
    public LearningController(final LearningsLogic logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Logic must not be null!");
        }
        learningsLogic = logic;
    }

    /**
     * It sets the logic of the flashboxes
     * @param flashBoxesLogic flashBoxesLogic is the logic of the flashboxes
     */
    public void setFlashBoxesLogic(FlashBoxesLogic flashBoxesLogic) {
        this.flashBoxesLogic = flashBoxesLogic;
    }

    /**
     * It sets the logic of the flashcards
     *
     * @param flashCardsLogic flashCardsLogic is the logic of the flashcards
     */
    public void setFlashCardsLogic(FlashCardsLogic flashCardsLogic) {
        this.flashCardsLogic = flashCardsLogic;
    }

    /**
     * It adds learning process to GUI
     */
    public void manageLearnings() {
        try {
            if (learningsWindow == null) {
                learningsWindow = new LearningsWindow(String.format("%s", "Learnings" ), this);
            }
            learningsWindow.setVisible(true);
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(learningsWindow, ex.getMessage(), "manageLearnings:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It checks if the given learning title exists
     * @param title title is the titel of the learning to check
     * @return true, if the given a learning with given title exists
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasLearningTitle(String title) throws SQLException {
        return learningsLogic.hasTitle(title);
    }

    /**
     * It adds new learning
     * @param learning learning is the new learning to add
     * @return 1, if learning successfully added
     *         0, if learning cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addLearning(Learning learning) throws SQLException {
        if (learningsLogic.add(learning) == 1) {
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It updates the given learning
     * @param learning learning is the learning to update
     * @return 1, if learning successfully updated
     *         0, if learning cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int updateLearning(Learning learning) throws SQLException {
        if (learningsLogic.update(learning) == 1) {
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It deletes the given learning
     * @param id id is the ID of the learning to delete
     * @return 1, if the learning successfully deleted
     *         0, if the learning cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteLearning(int id) throws SQLException {
        if (learningsLogic.delete(id) == 1) {
            return 1;
        } else {
            return  0;
        }
    }

    /**
     * It gets the learning with given ID
     * @param id id is the ID of the learning to get
     * @return learning
     * @throws SQLException if there is a problem with SQL connection
     */
    public Learning getLearning(int id) throws SQLException {
        return  learningsLogic.get(id);
    }

    /**
     * It adds learning details
     * @param learningId learningId is the ID of the learning
     * @param flashBoxId flashBoxId is the ID of the flashbox
     * @param random random represent if the flashcards in learning shuffled or not
     * @return size of the flashcard lists, if learning details successfully added
     *         0, if learning details cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addAllLearningDetailsFor(int learningId, int flashBoxId, boolean random) throws SQLException {
        return learningsLogic.addAllLearningDetailsFor(learningId, flashBoxId, random);
    }

    /**
     * It deletes learning details
     * @param learningId learningId is the ID of the learning to delete details
     * @return 1, if learning details successfully deleted
     *         0, if learning details cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int removeAllLearningDetailsFor(int learningId) throws SQLException {
        return learningsLogic.removeAllLearningDetailsFor(learningId);
    }

    /**
     * It checks the given learning, if it has learning details or not
     * @param learningId learningId is the ID of the learning to check
     * @return 1, if given learning has details
     *         0, if given learning has no details
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasLearningDetails(int learningId) throws SQLException {
        return learningsLogic.hasLearningDetails(learningId);
    }

    /**
     * It loads learnings in GUI
     * @param status status is the status of the learning
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadLearnings(LearningStatus status) throws SQLException {
        List<Learning> learnings= learningsLogic.getAllLearnings(status);
        learningsWindow.fillLearnings(learnings);
    }

    /**
     * It loads flashboxes combo box in GUI
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadFlashBoxesComboBox() throws SQLException {
        List<FlashBox> learnings= flashBoxesLogic.getAllFlashBoxes();
        learningsWindow.fillFlashBoxesComboBox(learnings);
    }

    /**
     * It loads learning details in GUI
     * @param learningId learningId is the ID of the learning to load its details
     * @throws SQLException if there is a problem with SQL connection
     */
    public void loadLearningDetails(int learningId) throws SQLException {
        List<LearningDetail> learnings= learningsLogic.getAllLearningDetails(learningId);
        learningsWindow.fillLearningDetails(learnings);
    }

    /**
     * It checks if the learning has started or not
     * @param id id is the ID of the learning
     * @return true, if learning has started
     *         false, if learning has not started
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean isLearningStarted(int id) throws SQLException {
        return learningsLogic.isLearningsStarted(id);
    }

}
