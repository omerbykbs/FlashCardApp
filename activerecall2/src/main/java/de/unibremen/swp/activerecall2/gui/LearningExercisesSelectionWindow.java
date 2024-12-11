package de.unibremen.swp.activerecall2.gui;

import de.unibremen.swp.activerecall2.controller.LearningExercisesController;
import de.unibremen.swp.activerecall2.controller.LearningExercisesSelectionController;
import de.unibremen.swp.activerecall2.model.Learning;
import de.unibremen.swp.activerecall2.model.LearningDetail;
import de.unibremen.swp.activerecall2.model.LearningListBoxView;
import de.unibremen.swp.activerecall2.model.LearningStatus;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class creates the Learning Exercises selection window of the Application.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class LearningExercisesSelectionWindow extends JFrame {

    /**
     * Width of the Learning Exercises selection Window
     */
    private static final int DEFAULT_WIDTH = 600;
    /**
     * Height of the Learning Exercises selection Window
     */
    private static final int DEFAULT_HEIGHT = 700;
    /**
     * LearningStatus
     */
    private LearningStatus showLearningStatus;
    /**
     * Learning Exercises Selection Controller
     */
    private final transient LearningExercisesSelectionController learningExercisesSelectionController;
    /**
     * Learning Exercises Controller
     */
    private LearningExercisesController learningExercisesController;
    /**
     * Label Show
     */
    private JLabel lblShow;
    /**
     * Combobox Show
     */
    private JComboBox<String> cmbShow;
    /**
     * Learnings List
     */
    private JList<Learning> lstLearnings;
    /**
     * Learnings scroll pane
     */
    private JScrollPane scrollLearnings;
    /**
     * Start Button
     */
    private JButton btnStart;
    /**
     * Cancel Button
     */
    private JButton btnCancel;
    /**
     * Main Panel
     */
    private JPanel panelMain;
    /**
     * Learning Exercises Selection Window
     */
    private LearningExercisesSelectionWindow self;
    /**
     * Current Learning
     */
    private Learning currentLearning;


    /**
     * Constructor of the Class , creates the Learning Exercises Selection Window.
     * @Param title is the title of the Application that comes from MainProg.
     * @Param pController is a Learning Exercises Selection Controller
     * @param learningExercisesController learning Exercises Controller
     */
    public LearningExercisesSelectionWindow(final String title, final LearningExercisesSelectionController pController, final LearningExercisesController learningExercisesController) {
        super(title);
        self = this;
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        learningExercisesSelectionController = pController;
        this.learningExercisesController = learningExercisesController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createItems();
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        showLearningStatus = LearningStatus.ALL;
        currentLearning = null;
    }

    public LearningExercisesController getLearningExercisesController() {
        return learningExercisesController;
    }

    public void setLearningExercisesController(LearningExercisesController learningExercisesController) {
        this.learningExercisesController = learningExercisesController;
    }

    /**
     * Create the Learning Exercises Selection Window Items and set their sizes.
     */

    private void createItems() {

        /**
         * Creates the Label Show, Combobox adds its elements , and set their sizes.
         */
        lblShow = new JLabel("Show");
        lblShow.setPreferredSize(new Dimension(60, 25));

        cmbShow = new JComboBox<>();
        cmbShow.setPreferredSize(new Dimension(470, 25));
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("ALL");
        model.addElement("PREPARED");
        model.addElement("STARTED");
        model.addElement("COMPLETED");
        cmbShow.setModel(model);

        /**
         * Creates the Combobox and defines the action when its clicked.
         */
        cmbShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cmbShow.getSelectedIndex() >= 0) {
                        String learningStatus = (String) cmbShow.getSelectedItem();
                        if (learningStatus.equals("ALL")) {
                            showLearningStatus = LearningStatus.ALL;
                        } else if (learningStatus.equals("PREPARED")) {
                            showLearningStatus = LearningStatus.PREPARED;
                        } else if (learningStatus.equals("STARTED")) {
                            showLearningStatus = LearningStatus.STARTED;
                        } else if (learningStatus.equals("COMPLETED")) {
                            showLearningStatus = LearningStatus.COMPLETED;
                        }
                        learningExercisesSelectionController.loadLearnings(showLearningStatus);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
        });

        /**
         * Creates the List Learnings and sets its Dimensions.
         */
        lstLearnings = new JList<>();
        //lstLearnings.setPreferredSize(new Dimension(500, 500));
        scrollLearnings = new JScrollPane(lstLearnings);
        scrollLearnings.setPreferredSize(new Dimension(500, 500));

        /**
         * Creates the List Learnings and defines the action when its clicked.
         */
        lstLearnings.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });

        /**
         * Creates the Start Button and sets its Dimensions.
         */
        btnStart = new JButton("Start");
        btnStart.setPreferredSize(new Dimension(80, 25));

        /**
         * Creates the Start Button and defines the action when its clicked.
         */
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (lstLearnings.getSelectedIndex() >= 0) {
                        currentLearning = lstLearnings.getSelectedValue();
                        if (!learningExercisesSelectionController.getLearningsLogic().hasLearningDetails(currentLearning.getLearningId())) {
                            JOptionPane.showMessageDialog(self, "You should add flash cards in this learning to start exercise it!");
                        } else {
                            if (currentLearning.getCompletionDate() != null) {
                                JOptionPane.showMessageDialog(self, "This learning is finished!\nPlease study another learning!");
                            } else if (currentLearning.getStartDate() != null && resumeQuestion() >= learningExercisesController.loadLearningDetailsToMemory(currentLearning.getLearningId()).size()) {
                                JOptionPane.showMessageDialog(self, "There is no available level to study!\nPlease wait until next level.");
                            } else if (!learningExercisesController.getLearningsLogic().updateLearningIfCompleted(currentLearning)) {
                                self.dispose();
                                if (currentLearning.getStartDate() == null || currentLearning.getStartDate().isEmpty()) {
                                    currentLearning.setStartDate(LocalDateTime.now().toString());
                                    learningExercisesSelectionController.getLearningsLogic().update(currentLearning);
                                }
                                learningExercisesSelectionController.manageLearningExercises(currentLearning);
                            } else {
                                JOptionPane.showMessageDialog(self,"This learning is completed!\nYou can study another learning.");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(self, "You must select a learning to start/continue exercise");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        /**
         * Creates the Cancel Button and sets its Dimensions.
         */
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(80, 25));

        /**
         * Creates the Cancel Button and defines the action when its clicked.
         */
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });

        /**
         * Creates the panel and set its border and size.
         */
        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(550, 650));
        panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Adds items to the panel according to the following order.
         */
        panelMain.setLayout(new FlowLayout());
        panelMain.add(lblShow);
        panelMain.add(cmbShow);
        panelMain.add(scrollLearnings);
        panelMain.add(btnStart);
        panelMain.add(btnCancel);
        this.setLayout(new FlowLayout());
        this.add(panelMain);


        /**
         * Defines the action when the Learning Exercises selection Window is opened.
         */
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    learningExercisesSelectionController.loadLearnings(showLearningStatus);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
            @Override
            public void windowClosing(WindowEvent e) {
            }
            @Override
            public void windowClosed(WindowEvent e) {
            }
            @Override
            public void windowIconified(WindowEvent e) {
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
            }
            @Override
            public void windowActivated(WindowEvent e) {
                try {
                    learningExercisesSelectionController.loadLearnings(showLearningStatus);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
    /**
     * Fills the Learnings list when the Learning Exercises Selection window is opened.
     * @param learnings  flash card list
     */
    public void fillLearnings(List<Learning> learnings) {
        DefaultListModel<Learning> model = new DefaultListModel<>();
        for (Learning learning : learnings) {
            model.addElement(new LearningListBoxView(learning));
        }
        lstLearnings.setModel(model);
    }

    public int resumeQuestion() throws SQLException {
        List<LearningDetail> learningDetailsList = learningExercisesController.loadLearningDetailsToMemory(currentLearning.getLearningId());
        for (int i = 0; i < learningDetailsList.size(); i++) {
            System.out.println(currentLearning.getResumeLearningDetailId() + " " + learningDetailsList.get(i).getLearningDetailId());
            if (currentLearning.getResumeLearningDetailId() == learningDetailsList.get(i).getLearningDetailId()) {
                return i;
            }
        }
        return 0;
    }
}

