package de.unibremen.swp.activerecall2.controller;

import de.unibremen.swp.activerecall2.FlashCardApp;
import de.unibremen.swp.activerecall2.gui.MainWindow;
import javax.swing.*;


/**
 * This class serves as logical connector between CategoriesController, FlashcardsController,
 * GlossaryController, FlashBoxesController, LearningController,
 * LearningExercisesSelectionController and MainWindow classes. It handles their communication.
 *
 * @author Ömer Faruk Büyükbas
 */
public class MainController {

    private MainWindow mainWindow;
    private CategoriesController categoriesController;

    private  FlashCardsController flashCardsController;

    private  GlossaryController glossaryController;

    private  FlashBoxesController flashBoxesController;

    private  LearningController learningController;

    private  LearningExercisesSelectionController learningExercisesSelectionController;

    /**
     * The constructor of this class
     */
    public MainController() {}

    /**
     * It sets the controller of the categories
     * @param categoriesController categoriesController is the controller of the categories
     */
    public void setCategoriesController(CategoriesController categoriesController) {
        this.categoriesController = categoriesController;
    }

    /**
     * It sets the controller of the flashcards
     * @param flashCardsController flashCardsController is the controller of the flashcards
     */
    public void setFlashCardsController(FlashCardsController flashCardsController) {
        this.flashCardsController = flashCardsController;
    }

    /**
     * It sets the controller of the glossary
     * @param glossaryController glossaryController is the controller of the glossary
     */
    public void setGlossaryController(GlossaryController glossaryController) {
        this.glossaryController = glossaryController;
    }

    /**
     * It sets the controller of the flashboxes
     * @param flashBoxesController flashBoxesController is the controller of the flashboxes
     */
    public void setFlashBoxesController(FlashBoxesController flashBoxesController) {
        this.flashBoxesController = flashBoxesController;
    }

    /**
     * It sets the controller of the learning
     * @param learningController learningController is the controller of the learning
     */
    public void setLearningController(LearningController learningController) {
        this.learningController = learningController;
    }

    /**
     * It sets the controller of the learning exercises selection
     * @param learningExercisesSelectionController learningExercisesSelectionController is the
     *                                             controller of the learning exercises selection
     */
    public void setLearningExercisesSelectionController(LearningExercisesSelectionController learningExercisesSelectionController) {
        this.learningExercisesSelectionController = learningExercisesSelectionController;
    }

    /**
     * It adds categories to GUI
     */
    public void manageCategories() {
        try {
            categoriesController.manageCategories();
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(mainWindow, ex.getMessage(), "manageCategories:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It adds flashcards to GUI
     */
    public void manageFlashCards() {
        try {
            flashCardsController.manageFlashCards();
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(mainWindow, ex.getMessage(), "manageFlashCards:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It adds glossary to GUI
     */
    public void manageGlossary() {
        try {
            glossaryController.manageGlossary();
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(mainWindow, ex.getMessage(), "manageGlossary:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It adds flashboxes to GUi
     */
    public void manageFlashBoxes() {
        try {
            flashBoxesController.manageFlashBoxes();
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(mainWindow, ex.getMessage(), "manageFlashBoxes:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It adds learnings to GUI
     */
    public void manageLearnings() {
        try {
            learningController.manageLearnings();
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(mainWindow, ex.getMessage(), "manageLearnings:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It adds learning exercises selection to GUI
     */
    public void manageLearningExercisesSelections() {
        try {
            learningExercisesSelectionController.manageLearningExercisesSelections();
        } catch (final IllegalStateException ex) {
            JOptionPane.showMessageDialog(mainWindow, ex.getMessage(), "manageLearningExercisesSelections:Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * It creates the main window
     */
    public void createAndDisplayMainWindow() {
        mainWindow = new MainWindow(String.format("%s (%s)", FlashCardApp.APP_NAME, FlashCardApp.VERSION), this);
        mainWindow.setVisible(true);
    }

    /**
     * It starts the application
     */
    public void startApplication() {
        SwingUtilities.invokeLater(this::createAndDisplayMainWindow);
    }

}