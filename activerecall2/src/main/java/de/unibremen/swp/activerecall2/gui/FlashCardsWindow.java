package de.unibremen.swp.activerecall2.gui;

import de.unibremen.swp.activerecall2.controller.FlashCardsController;
import de.unibremen.swp.activerecall2.model.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Stack;

/**
 * This class creates the Flash Cards window of the Application.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */

public class FlashCardsWindow extends JFrame {
    /**
     * Width of the Flash Cards Window
     */

    private static final int DEFAULT_WIDTH = 1190;

    /**
     * Height of the Flash Cards Window
     */
    private static final int DEFAULT_HEIGHT = 750;

    /**
     * Flash Card Controller
     */
    private final transient FlashCardsController flashCardsController;

    /**
     * Options for the filter to select.
     */
    public final static String ORDERBY_FLASHCARDID = "order by flashCardId";
    public final static String ORDERBY_CATEGORYID = "order by flashCards.categoryId";
    public final static String ORDERBY_CATEGORYID_QUESTION = "order by flashCards.categoryId, question";
    public final static String ORDERBY_CATEGORYNAME_QUESTION = "order by categoryName, question";
    public final static String ORDERBY_QUESTION = "order by question";

    /**
     * label Flash Card ID
     */
    private JLabel lblFlashCardId;
    /**
     * label Categories
     */
    private JLabel lblCategories;
    /**
     * Combobox Categories
     */
    private JComboBox<Category> cmbCategories;
    /**
     * Radio Button Show Selected Categories
     */
    private JRadioButton rdbShowSelectedCategories;
    /**
     * Radio Button Show All
     */
    private JRadioButton rdbShowAll;
    /**
     * Label Question
     */
    private JLabel lblQuestion;
    /**
     * Label Answer
     */
    private JLabel lblAnswer;
    /**
     * Label Keywords
     */
    private JLabel lblKeywords;
    /**
     * Text Field Flash Card ID
     */
    private JTextField txtFlashCardId;
    /**
     * Text Area Question
     */
    private JTextArea txtQuestion;
    /**
     * Text Area Answer
     */
    private JTextArea txtAnswer;
    /**
     * Text Field Keywords
     */
    private JTextField txtKeywords;
    /**
     * List Flashcards
     */
    private JList<FlashCard> lstFlashCards;
    /**
     * Scroll pane flash cards
     */
    private JScrollPane scrollFlashCards;
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
     * label Link Term
     */
    private JLabel lblLinkTerm;
    /**
     * Text Field Link Term
     */
    private JTextField txtLinkTerm;
    /**
     * Label Link Flash Card ID
     */
    private JLabel lblLinkFlashCardId;
    /**
     * Text Field Link Flash Card ID
     */
    private JTextField txtLinkFlashCardId;
    /**
     *  List Flashcard Links
     */
    private JList<FlashCardLink> lstFlashCardLinks;
    /**
     * Scroll Pane Links
     */
    private JScrollPane scrollLinks;
    /**
     * Button Back
     */
    private JButton btnBack;
    /**
     * Button Add Link
     */
    private JButton btnAddLink;
    /**
     * Button Remove Link
     */
    private JButton btnRemoveLink;
    /**
     * Button to visit link
     */
    private JButton btnVisit;
    /**
     * Panel Flash Card
     */

    private JPanel panelFlashCard;
    /**
     * Panel Main
     */
    private JPanel panelMain;
    /**
     * Panel Link
     */
    private JPanel panelLink;
    /**
     * Flash Cards Window
     */
    private FlashCardsWindow self;

    private String SORTBY;

    private Stack<FlashCard> stackFlashCards;

    /**
     * Constructor of the Class , creates the Flash Cards Window.
     * @Param title is the title of the Application that comes from MainProg.
     * @Param pController is a FlashCards Controller
     */
    public FlashCardsWindow(final String title, final FlashCardsController pController) {
        super(title);
        self = this;
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        flashCardsController = pController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createItems();
        SORTBY = ORDERBY_FLASHCARDID;
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setResizable(false);
        stackFlashCards = new Stack<>();
        btnBack.setEnabled(false);
    }

    /**
     * Creates the Flash Cards Window Items and set their sizes.
     */
    private void createItems() {

        /**
         * Creates the Label FlasCard ID, Label Categories, Label Question, Label Answer, Label Keywords, Combobox Categories and set their dimensions.
         */

        lblFlashCardId = new JLabel("Flash Card Id");
        lblFlashCardId.setPreferredSize(new Dimension(100, 25));

        lblCategories = new JLabel("Categories");
        lblCategories.setPreferredSize(new Dimension(100, 25));

        lblQuestion = new JLabel("Question");
        lblQuestion.setPreferredSize(new Dimension(700, 25));

        lblAnswer = new JLabel("Answer");
        lblAnswer.setPreferredSize(new Dimension(700, 25));

        lblKeywords = new JLabel("Keywords");
        lblKeywords.setPreferredSize(new Dimension(700, 25));

        cmbCategories = new JComboBox<>();
        cmbCategories.setPreferredSize(new Dimension(290, 25));


        /**
         * Creates the Radio Buttons "Show Selected Categories" and set its dimensions.
         */
        rdbShowSelectedCategories = new JRadioButton("Show Selected Categories");
        rdbShowSelectedCategories.setPreferredSize(new Dimension(200, 25));
        /**
         * Defines the action when "Show Selected Categories" is selected.
         */
        rdbShowSelectedCategories.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    reloadFlashCardList();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }

            }
        });

        /**
         * Creates the Radio Button "Show All" and set its dimensions.
         */
        rdbShowAll = new JRadioButton("Show All");
        rdbShowAll.setPreferredSize(new Dimension(100, 25));
        rdbShowAll.setSelected(true);

        /**
         * Defines the action when "Show All" is selected.
         */
        rdbShowAll.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    reloadFlashCardList();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
        });
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(rdbShowAll);
        radioButtonGroup.add(rdbShowSelectedCategories);


        /**
         * Creates the text Field Flash Card ID
         */
        txtFlashCardId = new JTextField();
        txtFlashCardId.setPreferredSize(new Dimension(600, 25));
        txtFlashCardId.setEditable(false);
        /**
         * Creates the text Area Question
         */
        txtQuestion = new JTextArea();
        txtQuestion.setPreferredSize(new Dimension(700, 100));
        /**
         * Creates the text Area Answer
         */
        txtAnswer = new JTextArea();
        txtAnswer.setPreferredSize(new Dimension(700, 100));

        /**
         * Creates the text Field Keywords
         */
        txtKeywords = new JTextField();
        txtKeywords.setPreferredSize(new Dimension(700, 25));

        /**
         * Creates the Button Clear
         */
        btnClear = new JButton("Clear");
        btnClear.setPreferredSize(new Dimension(80, 25));
        /**
         * Defines the action when the clear button is clicked.
         */
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    txtFlashCardId.setText("");
                    txtQuestion.setText("");
                    txtAnswer.setText("");
                    flashCardsController.loadCategories();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
        });

        /**
         * Creates the Button Add
         */
        btnAdd = new JButton("Add");
        btnAdd.setPreferredSize(new Dimension(80, 25));
        /**
         * Defines the action when the Add button is clicked and gives the possible errors with the corresponding message.
         */
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (txtQuestion.getText().trim().isEmpty() && txtAnswer.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Question and answer can't be empty!");
                    } else if (txtQuestion.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Question can't be empty!");
                    } else if (txtAnswer.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Answer can't be empty!");
                    } else if (cmbCategories.getSelectedIndex() < 1) {
                        JOptionPane.showMessageDialog(self, "Select a category!");
                    } else {
                        Category category = (Category) cmbCategories.getSelectedItem();
                        FlashCard flashCard = new FlashCard(txtQuestion.getText().trim(), txtAnswer.getText().trim(), txtKeywords.getText().trim(), category);
                        if (flashCardsController.addFlashCard(flashCard) != 1) {
                            JOptionPane.showMessageDialog(self, "Flash card could not be added!");
                        } else {
                            if (rdbShowSelectedCategories.isSelected()) {
                                flashCardsController.loadFlashCards(category, SORTBY);
                            } else {
                                flashCardsController.loadFlashCards(null, SORTBY);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
        });

        /**
         * Creates the Button Update
         */
        btnUpdate = new JButton("Update");
        btnUpdate.setPreferredSize(new Dimension(80, 25));

        /**
         * Defines the action when the Update button is clicked and gives the possible errors with the corresponding message.
         */
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtFlashCardId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Select a flash card!");
                    } else if (txtQuestion.getText().trim().isEmpty() && txtAnswer.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Question and answer can't be empty!");
                    } else if (txtQuestion.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Question can't be empty!");
                    } else if (txtAnswer.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Answer can't be empty!");
                    } else if (cmbCategories.getSelectedIndex() < 1) {
                        JOptionPane.showMessageDialog(self, "Select a category!");
                    } else {
                        Category category = (Category) cmbCategories.getSelectedItem();
                        FlashCard flashCard = new FlashCard(Integer.valueOf(txtFlashCardId.getText()), txtQuestion.getText().trim(), txtAnswer.getText().trim(), txtKeywords.getText().trim(), category);
                        if (flashCardsController.updateFlashCard(flashCard) != 1) {
                            JOptionPane.showMessageDialog(self, "Flash card could not be updated!");
                        } else {
                            if (rdbShowSelectedCategories.isSelected()) {
                                flashCardsController.loadFlashCards(category, SORTBY);
                            } else {
                                flashCardsController.loadFlashCards(null, SORTBY);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
        });

        /**
         * Creates the Button Delete
         */
        btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(new Dimension(80, 25));

        /**
         * Defines the action when the Delete button is clicked and gives the possible errors with the corresponding message.
         */
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtFlashCardId.getText().trim().isEmpty()) {
                        int id = Integer.valueOf(txtFlashCardId.getText());
                        int categoryId = flashCardsController.getCategoryIdOfFlashCard(id);
                        if (flashCardsController.flashCardHasRelatedRecords(categoryId)) {
                            JOptionPane.showMessageDialog(self, "Flash card can't be deleted!\nFlash card is added into a flash box.");
                        } else {
                            int result = JOptionPane.showConfirmDialog(self, "Are you sure you want to delete?", "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                            if (result == JOptionPane.OK_OPTION) {
                                //int id = Integer.valueOf(txtFlashCardId.getText());
                                if (flashCardsController.deleteFlashCard(id) != 1) {
                                    JOptionPane.showMessageDialog(self, "Flash card could not be deleted!");
                                } else {
                                    if (rdbShowSelectedCategories.isSelected()) {
                                        Category category = (Category) cmbCategories.getSelectedItem();
                                        flashCardsController.loadFlashCards(category, SORTBY);
                                    } else {
                                        flashCardsController.loadFlashCards(null, SORTBY);
                                    }
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(self, "Select a flash card!");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }

            }
        });
        /**
         * Creates the Button Delete
         */
        btnClose = new JButton("Close");
        btnClose.setPreferredSize(new Dimension(80, 25));

        /**
         * Defines the action when the Close button is clicked.
         */
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();

            }
        });

        lstFlashCards = new JList<>();
        scrollFlashCards = new JScrollPane(lstFlashCards);
        scrollFlashCards.setPreferredSize(new Dimension(700, 200));
        lstFlashCards.setFont(new Font("Courier New", Font.PLAIN, 11));
        lstFlashCards.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    if (lstFlashCards.getSelectedIndex() >= 0) {
                        FlashCard flashCard = lstFlashCards.getSelectedValue();
                        showFlashCard(flashCard);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                }
            }
        });

        /**
         * Creates the Label Link Term.
         */
        lblLinkTerm = new JLabel("Link Term");
        lblLinkTerm.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the Label Link FlashCard Id.
         */
        lblLinkFlashCardId = new JLabel("Target Card Id");
        lblLinkFlashCardId.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the Tet Field Link Term.
         */
        txtLinkTerm = new JTextField();
        txtLinkTerm.setPreferredSize(new Dimension(270, 25));

        /**
         * Creates the Tet Field Flash Card ID.
         */
        txtLinkFlashCardId = new JTextField();
        txtLinkFlashCardId.setPreferredSize(new Dimension(270, 25));

        /**
         * Creates the Flash Card List.
         */
        lstFlashCardLinks = new JList<>();

        /**
         * Creates the Scroll pane for Flash Card List..
         */
        scrollLinks = new JScrollPane(lstFlashCardLinks);
        scrollLinks.setPreferredSize(new Dimension(375, 500));
        scrollLinks.setFont(new Font("Courier New", Font.PLAIN, 11));

        /**
         * Creates the Button Back.
         */
        btnBack = new JButton("Back");
        btnBack.setPreferredSize(new Dimension(70, 25));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!stackFlashCards.empty()) {
                        FlashCard flashCard = stackFlashCards.pop();
                        showFlashCard(flashCard);
                        if (stackFlashCards.empty()) {
                            btnBack.setEnabled(false);
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        /**
         * Creates the Button Add Link.
         */
        btnAddLink = new JButton("Add Link");
        btnAddLink.setPreferredSize(new Dimension(90, 25));

        /**
         * Defines the action when the Add Link button is clicked and gives the possible errors with the corresponding message.
         */
        btnAddLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!flashCardsController.isFlashCardToLinkCardId(Integer.parseInt(txtLinkFlashCardId.getText().trim()))) {
                        JOptionPane.showMessageDialog(self, "There is no flash card with entered target card id!\nPlease enter an existing card id!");
                    } else if (!txtQuestion.getText().contains(txtLinkTerm.getText().trim()) && !txtAnswer.getText().contains(txtLinkTerm.getText().trim())) {
                        JOptionPane.showMessageDialog(self, "Link term can't be found in question or answer!");
                    } else if (txtFlashCardId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Select a flash card!");
                    } else if (txtLinkTerm.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Link term can't be empty!");
                    } else if (txtLinkFlashCardId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Target flash card id can't be empty!");
                    } else if (txtFlashCardId.getText().trim().equals(txtLinkFlashCardId.getText().trim())) {
                        JOptionPane.showMessageDialog(self, "Target flash card id can't be same with source flash card id!");
                    } else {
                        Category category = (Category) cmbCategories.getSelectedItem();
                        int linkFlashCardId = Integer.parseInt(txtLinkFlashCardId.getText().trim());
                        int flashCardId = Integer.parseInt(txtFlashCardId.getText());
                        String term = txtLinkTerm.getText();
                        if (!flashCardsController.hasTermTargetFlashCard(flashCardId, term, linkFlashCardId)) {
                            FlashCardLink flashCardLink = new FlashCardLink(flashCardId, term, linkFlashCardId);
                            if (flashCardsController.addFlashCardLink(flashCardLink) != 1) {
                                JOptionPane.showMessageDialog(self, "Flash card link could not be added!");
                            } else {
                                flashCardsController.loadFlashCardLinks(flashCardId);
                            }
                        } else {
                            JOptionPane.showMessageDialog(self, "This link is already added!");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(self, "Enter a number for target flash card id");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates the Button Remove Link.
         */
        btnRemoveLink = new JButton("Remove Link");
        btnRemoveLink.setPreferredSize(new Dimension(110, 25));
        /**
         * Defines the action when the Add Link button is clicked and gives the possible errors with the corresponding message.
         */
        btnRemoveLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtFlashCardId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "First, select a flash card!");
                    } else if (lstFlashCardLinks.getSelectedIndex() < 0) {
                        JOptionPane.showMessageDialog(self, "Select a flash card link to remove!");
                    } else {
                        int result = JOptionPane.showConfirmDialog(self, "Are you sure you want to delete?", "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            FlashCardLink flashCardLink = lstFlashCardLinks.getSelectedValue();
                            if (flashCardsController.deleteFlashCardLink(flashCardLink.getFlashCardLinkId()) != 1) {
                                JOptionPane.showMessageDialog(self, "Flash card link could not be deleted!");
                            } else {
                                flashCardsController.loadFlashCardLinks(flashCardLink.getFlashCardId());
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

        btnVisit = new JButton("Visit");
        btnVisit.setPreferredSize(new Dimension(90, 25));
        btnVisit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtFlashCardId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "First, select a flash card!");
                    } else if (lstFlashCardLinks.getSelectedIndex() < 0) {
                        JOptionPane.showMessageDialog(self, "Select a flash card link to visit!");
                    } else {
                        btnBack.setEnabled(true);
                        FlashCardLink flashCardLink = lstFlashCardLinks.getSelectedValue();
                        stackFlashCards.push(flashCardLink.getFlashCard());
                        showFlashCard(flashCardLink.getTargetFlashCard());
                    }
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates the panel and set its border and size.
         */
        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(1140, 700));
        panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Creates the Flash Card panel and set its border and size.
         */
        panelFlashCard = new JPanel();
        panelFlashCard.setPreferredSize(new Dimension(730, 650));
        //panelFlashCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //panelFlashCard.setBackground(Color.getHSBColor(0.45f,0.4f,1.0f));

        /**
         * Creates the Link panel and set its border and size.
         */
        panelLink = new JPanel();
        panelLink.setPreferredSize(new Dimension(390, 650));
        //panelLink.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //panelLink.setBackground(Color.getHSBColor(0.53f, 0.3f, 1.0f));

        /**
         * Adds items to the panel according to the following order.
         */
        panelFlashCard.setLayout(new FlowLayout());
        panelFlashCard.add(lblFlashCardId);
        panelFlashCard.add(txtFlashCardId);
        panelFlashCard.add(lblCategories);
        panelFlashCard.add(cmbCategories);
        panelFlashCard.add(rdbShowSelectedCategories);
        panelFlashCard.add(rdbShowAll);
        panelFlashCard.add(lblQuestion);
        panelFlashCard.add(txtQuestion);
        panelFlashCard.add(lblAnswer);
        panelFlashCard.add(txtAnswer);
        panelFlashCard.add(lblKeywords);
        panelFlashCard.add(txtKeywords);
        panelFlashCard.add(scrollFlashCards);
        panelFlashCard.add(btnClear);
        panelFlashCard.add(btnAdd);
        panelFlashCard.add(btnUpdate);
        panelFlashCard.add(btnDelete);
        panelFlashCard.add(btnClose);


        /**
         * Adds items to the Link panel according to the following order.
         */
        panelLink.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelLink.add(lblLinkTerm);
        panelLink.add(txtLinkTerm);
        panelLink.add(lblLinkFlashCardId);
        panelLink.add(txtLinkFlashCardId);
        panelLink.add(btnBack);
        panelLink.add(btnAddLink);
        panelLink.add(btnRemoveLink);
        panelLink.add(btnVisit);
        panelLink.add(scrollLinks);

        panelMain.setLayout(new FlowLayout());
        panelMain.add(panelFlashCard);
        panelMain.add(panelLink);

        this.setLayout(new FlowLayout());
        this.add(panelMain);

        /**
         * Defines the action when the Flash Card Window is opened.
         */
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    flashCardsController.loadCategories();
                    flashCardsController.loadFlashCards(null, SORTBY);
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
                    flashCardsController.loadCategories();
                    if (lstFlashCards.getSelectedIndex() >= 0) {
                        FlashCard flashCard = lstFlashCards.getSelectedValue();
                        selectComboBoxCategory(flashCard.getCategoryId());
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
     * Reloads the flash card list when ShowSelectedCategories or ShowAll is selected.
     */
    private void reloadFlashCardList() throws SQLException {
        if (rdbShowSelectedCategories.isSelected()) {
            Category category = (Category) cmbCategories.getSelectedItem();
            flashCardsController.loadFlashCards(category, SORTBY);

        } else if (rdbShowAll.isSelected()) {
            flashCardsController.loadFlashCards(null, SORTBY);
        }
    }

    /**
     * Fills the flash card list when the Flash Cards Window is opened.
     * @param flashCards  flash card list
     */
    public void fillFlashCards(List<FlashCard> flashCards) {
        DefaultListModel<FlashCard> model = new DefaultListModel<>();
        for (FlashCard flashCard : flashCards) {
            model.addElement(new FlashCardListBoxView(flashCard));
        }
        lstFlashCards.setModel(model);
    }

    public void showFlashCard(FlashCard flashCard) throws SQLException {
        txtFlashCardId.setText(flashCard.getFlashCardId() + "");
        txtQuestion.setText(flashCard.getQuestion());
        txtAnswer.setText(flashCard.getAnswer());
        txtKeywords.setText(flashCard.getKeywords());
        selectComboBoxCategory(flashCard.getCategoryId());
        flashCardsController.loadFlashCardLinks(flashCard.getFlashCardId());
    }

    /**
     * Fills the flash card links list when the Flash Cards Window is opened.
     * @param flashCardLinks  flash card links list
     */
    public void fillFlashCardLinks(List<FlashCardLink> flashCardLinks) {
        DefaultListModel<FlashCardLink> model = new DefaultListModel<>();
        for (FlashCardLink flashCardLink : flashCardLinks) {
            model.addElement(new FlashCardLinkListBoxView(flashCardLink));
        }
        lstFlashCardLinks.setModel(model);
    }

    /**
     * Fills the categories list when the Flash Cards Window is opened.
     * @param categories categories list
     */
    public void fillCategories(List<Category> categories) {
        DefaultComboBoxModel<Category> model = new DefaultComboBoxModel<>();
        Category select = new CategoryComboBoxView(new Category(0, "Select a category"));
        model.addElement(select);
        for (Category category : categories) {
            model.addElement(new CategoryComboBoxView(category));
        }
        cmbCategories.setModel(model);
    }

    /**
     * Filters the getting flash cards in the list with category id.
     * @param id category id
     */
    public void selectComboBoxCategory(int id) {
        DefaultComboBoxModel<Category> model = (DefaultComboBoxModel<Category>) cmbCategories.getModel();
        if (model != null && model.getSize() > 0) {
            for (int i = 0; i < model.getSize(); i++) {
                if (model.getElementAt(i).getCategoryId() == id) {
                    model.setSelectedItem(model.getElementAt(i));
                }
            }
        }
    }
}
