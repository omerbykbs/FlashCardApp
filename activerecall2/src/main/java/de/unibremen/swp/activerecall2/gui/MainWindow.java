package de.unibremen.swp.activerecall2.gui;

import de.unibremen.swp.activerecall2.controller.MainController;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates the main window of the Application.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */

public class MainWindow extends JFrame {

    /**
     * Width of the Main Window
     */
    private static final int DEFAULT_WIDTH = 800;

    /**
     * Height of the Main Window
     */
    private static final int DEFAULT_HEIGHT = 400;
    /**
     * Main Controller
     */
    private final transient MainController mainController;
    private JPanel mainPanel;
    /**
     * Category Button
     */
    private JButton btnCategories;
    /**
     * Flasch Cards Button
     */
    private JButton btnFlashCards;
    /**
     * Flash Box Button
     */
    private JButton btnFlashboxes;
    /**
     * Glossary Button
     */
    private JButton btnGlossary;
    /**
     * Learning Button
     */
    private JButton btnLearnings;
    /**
     * Learning Exercise Button
     */
    private JButton btnLearningExercises;
    /**
     * Exit Button
     */
    private JButton btnExit;
    /**
     * Constructor of the Class , creates the Main Window.
     * @Param title is the title of the Application that comes from MainProg.
     * @Param pController is a MainController
     */

    public MainWindow(final String title, final MainController pController) {
        super(title);
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        mainController = pController;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createMenu();
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    /**
     * Creates the main menu of the Application.
     */
    private void createMenu() {
        /**
         * Creates the Categories Button.
         */
        btnCategories = new JButton("Categories");
        /**
         * Defines the action when the Categories button clicked.
         */
        btnCategories.addActionListener(e -> mainController.manageCategories());

        /**
         * Creates the Flash Cards Button.
         */
        btnFlashCards = new JButton("Flash Cards");
        /**
         * Defines the action when the Flash Card button clicked.
         */
        btnFlashCards.addActionListener(e -> mainController.manageFlashCards());

        /**
         * Creates the Glossary Button.
         */
        btnGlossary= new JButton("Glossary");
        /**
         * Defines the action when the Glossary button clicked.
         */
        btnGlossary.addActionListener(e -> mainController.manageGlossary());
        /**
         * Creates the Flash Boxes Button.
         */
        btnFlashboxes= new JButton("Card Boxes");
        /**
         * Defines the action when the Flash Boxes button clicked.
         */
        btnFlashboxes.addActionListener(e -> mainController.manageFlashBoxes());
        /**
         * Creates the Learning Button.
         */
        btnLearnings = new JButton("Learnings");
        /**
         * Defines the action when the Learning button clicked.
         */
        btnLearnings.addActionListener(e -> mainController.manageLearnings());
        /**
         * Creates the Learning Exercises Button.
         */
        btnLearningExercises = new JButton("Learning Exercises");
        /**
         * Defines the action when the Learning Exercises button clicked.
         */
        btnLearningExercises.addActionListener(e -> mainController.manageLearningExercisesSelections());
        /**
         * Creates the Exit Button.
         */
        btnExit= new JButton("Exit");
        /**
         * Defines the action when the Exit button clicked.
         */
        btnExit.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        /**
         * Sets the skeleton of the panel with grid and adds the button according to the following order.
         */
        mainPanel= new JPanel();
        mainPanel.setPreferredSize(new Dimension(500,200));
        GridLayout gridLayout= new GridLayout(3,3,5,5);
        mainPanel.setLayout( gridLayout);
        mainPanel.add(btnCategories);
        mainPanel.add(btnFlashCards);
        mainPanel.add(btnFlashboxes);
        mainPanel.add(btnGlossary);
        mainPanel.add(btnLearnings);
        mainPanel.add(btnLearningExercises);
        mainPanel.add(btnExit);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,50,50));
        this.add(mainPanel);
    }
}
