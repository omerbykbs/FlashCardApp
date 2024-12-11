package de.unibremen.swp.activerecall2.gui;

import de.unibremen.swp.activerecall2.controller.LearningController;
import de.unibremen.swp.activerecall2.model.*;

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
 * This class creates the Learning Window of the Application.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class LearningsWindow extends JFrame {

    /**
     * Width of the Learning Window
     */
    private static final int DEFAULT_WIDTH = 700;

    /**
     * Height of the Learning Window
     */
    private static final int DEFAULT_HEIGHT = 700;

    /**
     * Learning Controller
     */
    private final transient LearningController learningController;

    /**
     * Label Learning ID
     */
    private JLabel lblLearningId;
    /**
     * Label Title
     */
    private JLabel lblTitle;
    /**
     * Text Field Learning ID
     */
    private JTextField txtLearningId;
    /**
     * Text Field Title
     */
    private JTextField txtTitle;
    /**
     * Learnings List
     */
    private JList<Learning> lstLearnings;
    /**
     * Learnings Scroll Pane
     */
    private JScrollPane scrollLearnings;
    /**
     * Label Flash Boxes
     */
    private JLabel lblFlashBoxes;
    /**
     * Combobox Flash Boxes
     */
    private JComboBox<FlashBox> cmbFlashBoxes;
    /**
     * Label Show
     */
    private JLabel lblShow;
    /**
     * Combobox Show
     */
    private JComboBox<String> cmbShow;
    /**
     * Learning Details List
     */
    private JList<LearningDetail> lstLearningDetails;
    /**
     * Learnings Details list Scroll Pane
     */
    private JScrollPane scrollLearningDetails;
    /**
     * Button Clear
     */
    private JButton btnClear;
    /**
     * Button Add
     */
    private JButton btnAdd;
    /**
     * Button Update
     */
    private JButton btnUpdate;
    /**
     * Button Delete
     */
    private JButton btnDelete;
    /**
     * Button Close
     */
    private JButton btnClose;
    /**
     * Label Learning Flash Cards
     */
    private JLabel lblLearningFlashCards;
    /**
     * Learnings Panel
     */
    private JPanel panelLearnings;
    /**
     * Buttons Panel
     */
    private JPanel panelButtons;
    /**
     * Main Panel
     */
    private JPanel panelMain;
    /**
     * Learnings Window
     */
    private LearningsWindow self;

    /**
     * Button Add Flash Cards To Learning
     */
    private JButton btnAddFlashCardsToLearning;
    /**
     * Button Remove Flash Cards To Learning
     */
    private JButton btnRemoveFlashCardsFromLearning;
    /**
     * Combobox Question Style
     */
    private JComboBox<String> cmbQuestionStyle;
    /**
     * Learning Status
     */
    private LearningStatus showLearningStatus;

    /**
     * Constructor of the Class , creates the Learnings Window.
     * @Param title is the title of the Application that comes from MainProg.
     * @Param pController is a Learning  Controller
     */
    public LearningsWindow(final String title, final LearningController pController) {
        super(title);
        self = this;
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        learningController = pController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createItems();
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        showLearningStatus = LearningStatus.ALL;
    }

    /**
     * Create the Learnings Windows Items and set their sizes.
     */
    private void createItems() {
        /**
         * Creates the label Learning ID and set its dimensions.
         */
        lblLearningId = new JLabel("Learning Id");
        lblLearningId.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the label Learning Title and set its dimensions.
         */
        lblTitle = new JLabel("Learning Title");
        lblTitle.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the Text Field Learning ID and set its dimensions.
         */
        txtLearningId = new JTextField();
        txtLearningId.setPreferredSize(new Dimension(380, 25));
        txtLearningId.setEditable(false);

        /**
         * Creates the Text Field Title and set its dimensions.
         */
        txtTitle = new JTextField();
        txtTitle.setPreferredSize(new Dimension(380, 25));

        /**
         * Creates the label Flash Boxes and set its dimensions.
         */
        lblFlashBoxes = new JLabel("Card Boxes");
        lblFlashBoxes.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the Combobox Flash Boxes and set its dimensions.
         */
        cmbFlashBoxes = new JComboBox<>();
        cmbFlashBoxes.setPreferredSize(new Dimension(380, 25));

        /**
         * Creates the label Show and set its dimensions.
         */
        lblShow = new JLabel("Show");
        lblShow.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the Combobox Show and set its dimensions.
         */
        cmbShow = new JComboBox<>();
        cmbShow.setPreferredSize(new Dimension(380, 25));
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("ALL");
        model.addElement("PREPARED");
        model.addElement("STARTED");
        model.addElement("COMPLETED");
        cmbShow.setModel(model);

        /**
         * Defines the action Combobox is clicked.
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
                        learningController.loadLearnings(showLearningStatus);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
        });

        /**
         * Creates the Clear Button and set its dimensions.
         */
        btnClear = new JButton("Clear");
        btnClear.setPreferredSize(new Dimension(80, 25));

        /**
         * Defines the action when Clear Button is clicked.
         */
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtLearningId.setText("");
                txtTitle.setText("");
                cmbFlashBoxes.setEnabled(true);
            }
        });

        /**
         * Creates the Add Button and set its dimensions.
         */
        btnAdd = new JButton("Add");
        btnAdd.setPreferredSize(new Dimension(80, 25));

        /**
         * Defines the action when Clear Button is clicked.
         */
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtTitle.getText().trim().isEmpty()) {
                    try {
                        if (!learningController.hasLearningTitle(txtTitle.getText())) {
                            FlashBox flashBox = (FlashBox) cmbFlashBoxes.getSelectedItem();
                            LocalDateTime creationDate = LocalDateTime.now();
                            Learning learning = new Learning(txtTitle.getText(), flashBox.getFlashBoxId(), creationDate.toString(), null, null);
                            learning.setResumeLearningDetailId(-1);
                            if (learningController.addLearning(learning) != 1) {
                                JOptionPane.showMessageDialog(self, "Learning could not be added!");
                            } else {
                                learningController.loadLearnings(showLearningStatus);
                            }
                        } else {
                            JOptionPane.showMessageDialog(self, "Learning title already exists!");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                    } catch (Exception exp) {
                        JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(self, "Learning title can't be empty!");
                }
            }
        });

        /**
         * Creates the Update Button and set its dimensions.
         */
        btnUpdate = new JButton("Update");
        btnUpdate.setPreferredSize(new Dimension(80, 25));

        /**
         * Defines the action when Update Button is clicked.
         */
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtLearningId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(self, "Select a learning to update!");
                } else if (txtTitle.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(self, "Learning title can't be empty!");
                } else {
                    try {
                        FlashBox flashBox = (FlashBox) cmbFlashBoxes.getSelectedItem();
                        Learning selectedLearning = lstLearnings.getSelectedValue();
                        Learning learning = new Learning(Integer.parseInt(txtLearningId.getText()), txtTitle.getText(), flashBox.getFlashBoxId(), selectedLearning.getCreationDate(), selectedLearning.getStartDate(), selectedLearning.getCompletionDate());
                        learning.setResumeLearningDetailId(selectedLearning.getResumeLearningDetailId());
                        if (learningController.updateLearning(learning) != 1) {
                            JOptionPane.showMessageDialog(self, "Learning could not be updated!");
                        } else {
                            learningController.loadLearnings(showLearningStatus);
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                    } catch (Exception exp) {
                        JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                    }
                }
            }
        });

        /**
         * Creates the Delete Button and set its dimensions.
         */
        btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(new Dimension(80, 25));

        /**
         * Defines the action when Delete Button is clicked.
         */
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try { // If it has learning detail, it will be blocked from deleting
                    if (txtLearningId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Select a learning to delete!");
                    } else if (learningController.hasLearningDetails(Integer.parseInt(txtLearningId.getText().trim()))) {
                        JOptionPane.showMessageDialog(self, "You can't delete this learning!\nYou should first remove all flash cards from it!");
                    } else {
                        int result = JOptionPane.showConfirmDialog(self, "Are you sure you want to delete?", "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            int id = Integer.valueOf(txtLearningId.getText());
                            if (learningController.deleteLearning(id) != 1) {
                                JOptionPane.showMessageDialog(self, "Learning could not be deleted!");
                            } else {
                                txtLearningId.setText("");
                                txtTitle.setText("");
                                cmbFlashBoxes.setSelectedIndex(0);
                                learningController.loadLearnings(showLearningStatus);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates the Close Button and set its dimensions.
         */
        btnClose = new JButton("Close");
        btnClose.setPreferredSize(new Dimension(80, 25));

        /**
         * Defines the action when Close Button is clicked.
         */
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });

        /**
         * Creates the Learnings List and set its size.
         */
        lstLearnings = new JList<>();
        scrollLearnings = new JScrollPane(lstLearnings);
        scrollLearnings.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollLearnings.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollLearnings.setPreferredSize(new Dimension(485, 175));

        /**
         * Defines the List Actions.
         */
        lstLearnings.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    if (lstLearnings.getSelectedIndex() >= 0) {
                        Learning learning = lstLearnings.getSelectedValue();
                        txtLearningId.setText(learning.getLearningId() + "");
                        txtTitle.setText(learning.getTitle());
                        selectComboBoxFlashBox(learning.getFlashBoxId());
                        learningController.loadLearningDetails(learning.getLearningId());
                        cmbFlashBoxes.setEnabled(!learningController.hasLearningDetails(learning.getLearningId()));
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates the Add Flash Cards To Learning Button and set its dimensions.
         */
        btnAddFlashCardsToLearning = new JButton("Add Flash Cards To Learning");
        btnAddFlashCardsToLearning.setPreferredSize(new Dimension(240, 25));

        /**
         * Defines the Add Flash Cards To Learning Button  is clicked.
         */
        btnAddFlashCardsToLearning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (lstLearnings.getSelectedIndex() < 0) {
                        JOptionPane.showMessageDialog(self, "Select a learning to add flash cards");
                    } else {
                        // check whether the previously selected learning cards have been added to the learning detail. if added, there is a warning
                        Learning learning = lstLearnings.getSelectedValue();
                        boolean random = cmbQuestionStyle.getSelectedIndex()==0; // if random selected else sort by question
                        if (learningController.addAllLearningDetailsFor(learning.getLearningId(), learning.getFlashBoxId(),random) > 0) {
                            learningController.loadLearningDetails(learning.getLearningId());
                        } else {
                            JOptionPane.showMessageDialog(self, "Flash cards are already added into selected card box ");
                        }
                    }
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates the Remove Flash Cards From Learning Button and set its dimensions.
         */
        btnRemoveFlashCardsFromLearning = new JButton("Remove Flash Cards From Learning");
        btnRemoveFlashCardsFromLearning.setPreferredSize(new Dimension(250, 25));

        /**
         * Defines the Remove Flash Cards From Learning Button is clicked.
         */
        btnRemoveFlashCardsFromLearning.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtLearningId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Select a learning to remove flash cards from!");
                    }
                    else {
                        if (learningController.isLearningStarted(lstLearnings.getSelectedValue().getLearningId())) {
                            JOptionPane.showMessageDialog(self, "These flash cards are already started to be learned.\nYou can't remove them from this learning!");
                        } else {
                            int result = JOptionPane.showConfirmDialog(self, "Are you sure you want to remove\nall flash cards from this learning?", "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                            if (result == JOptionPane.OK_OPTION) {
                                int learningId = Integer.valueOf(txtLearningId.getText());
                                if (learningController.removeAllLearningDetailsFor(learningId) < 1) {
                                    JOptionPane.showMessageDialog(self, "Flash cards couldn't removed from this learning!");
                                } else {
                                    Learning learning = learningController.getLearning(learningId);
                                    learning.setStartDate(null);
                                    learning.setCompletionDate(null);
                                    learning.setResumeLearningDetailId(-1);
                                    learningController.updateLearning(learning);
                                    learningController.loadLearningDetails(learningId);
                                }
                                cmbFlashBoxes.setEnabled(!learningController.hasLearningDetails(learningId));
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates the Combobox Question Style and set its dimensions.
         */
        cmbQuestionStyle = new JComboBox();
        cmbQuestionStyle.setPreferredSize(new Dimension(240, 25));
        DefaultComboBoxModel<String> modelQuestionStyle = new DefaultComboBoxModel<>();
        modelQuestionStyle.addElement("Random");
        modelQuestionStyle.addElement("Sorted By Question");
        cmbQuestionStyle.setModel(modelQuestionStyle);

        /**
         * Creates the Learnings Details List and set its dimensions.
         */
        lstLearningDetails = new JList<>();
        scrollLearningDetails = new JScrollPane(lstLearningDetails);
        scrollLearningDetails.setPreferredSize(new Dimension(480, 240));

        /**
         * Creates the Panel Learnings and set its dimensions.
         */
        panelLearnings = new JPanel();
        panelLearnings.setPreferredSize(new Dimension(500, 640));

        /**
         * Creates the Panel Buttons and set its dimensions.
         */
        panelButtons = new JPanel();
        panelButtons.setPreferredSize(new Dimension(100, 640));

        /**
         * Creates the  Main Panel and set its dimensions.
         */
        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(650, 650));
        panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Creates the  Label Learning Flash Cards and set its dimensions.
         */
        lblLearningFlashCards = new JLabel("Learning Flash Cards");
        lblLearningFlashCards.setPreferredSize(new Dimension(300, 25));

        /**
         * Adds items to the Learning panel according to the following order.
         */
        panelLearnings.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelLearnings.add(lblLearningId);
        panelLearnings.add(txtLearningId);
        panelLearnings.add(lblTitle);
        panelLearnings.add(txtTitle);
        panelLearnings.add(lblFlashBoxes);
        panelLearnings.add(cmbFlashBoxes);
        panelLearnings.add(lblShow);
        panelLearnings.add(cmbShow);
        panelLearnings.add(scrollLearnings);
        panelLearnings.add(btnAddFlashCardsToLearning);
        panelLearnings.add(cmbQuestionStyle);
        panelLearnings.add(btnRemoveFlashCardsFromLearning);
        panelLearnings.add(lblLearningFlashCards);
        panelLearnings.add(scrollLearningDetails);

        /**
         * Adds items to the Buttons panel according to the following order.
         */
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(btnClear);
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClose);

        /**
         * Adds panels to the Main Panel according to the following order.
         */
        panelMain.setLayout(new FlowLayout());
        panelMain.add(panelLearnings);
        panelMain.add(panelButtons);

        this.setLayout(new FlowLayout());
        this.add(panelMain);

        /**
         * Defines the action when the Learnings Window is opened.
         */
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    learningController.loadLearnings(showLearningStatus);
                    learningController.loadFlashBoxesComboBox();
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
                    learningController.loadFlashBoxesComboBox();
                    if (lstLearnings.getSelectedIndex() >= 0) {
                        Learning learning = lstLearnings.getSelectedValue();
                        selectComboBoxFlashBox(learning.getFlashBoxId());
                    }
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
     * Fills theFlash Boxes Combobox when the Learnings window is opened.
     * @param flashBoxes  flash boxes list
     */
    public void fillFlashBoxesComboBox(List<FlashBox> flashBoxes) {
        DefaultComboBoxModel<FlashBox> model = new DefaultComboBoxModel<>();
        for (FlashBox flashBox : flashBoxes) {
            model.addElement(new FlashBoxComboBoxView(flashBox));
        }
        cmbFlashBoxes.setModel(model);
    }

    /**
     * Fills the Learnings list when the Learnings window is opened.
     * @param learnings  flash card links list
     */
    public void fillLearnings(List<Learning> learnings) {
        DefaultListModel<Learning> model = new DefaultListModel<>();
        for (Learning learning : learnings) {
            model.addElement(new LearningListBoxView(learning));
        }
        lstLearnings.setModel(model);
    }

    /**
     * Fills the Learnings Details list when the Learnings window is opened.
     * @param learningDetails  flash card links list
     */

    public void fillLearningDetails(List<LearningDetail> learningDetails) {
        DefaultListModel<LearningDetail> model = new DefaultListModel<>();
        for (LearningDetail learningDetail : learningDetails) {
            model.addElement(new LearningDetailListBoxView(learningDetail));
        }
        lstLearningDetails.setModel(model);
    }

    public void selectComboBoxFlashBox(int id) {
        DefaultComboBoxModel<FlashBox> model = (DefaultComboBoxModel<FlashBox>) cmbFlashBoxes.getModel();
        if (model != null && model.getSize() > 0) {
            for (int i = 0; i < model.getSize(); i++) {
                if (model.getElementAt(i).getFlashBoxId() == id) {
                    model.setSelectedItem(model.getElementAt(i));
                }
            }
        }
    }
}
