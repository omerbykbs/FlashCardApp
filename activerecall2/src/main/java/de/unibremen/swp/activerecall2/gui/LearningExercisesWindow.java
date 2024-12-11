package de.unibremen.swp.activerecall2.gui;

import de.unibremen.swp.activerecall2.controller.LearningExercisesController;
import de.unibremen.swp.activerecall2.model.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;

/**
 * This class creates the Learning Exercises window of the Application.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class LearningExercisesWindow extends JFrame {
    /**
     * Width of the Learning Exercises Window
     */
    private static final int DEFAULT_WIDTH = 1000;
    /**
     * Height of the Learning Exercises Window
     */
    private static final int DEFAULT_HEIGHT = 700;

    /**
     * Learning Exercises Window
     */
    private  LearningExercisesWindow self;

    /**
     * Learning Exercises Controller
     */
    private final transient LearningExercisesController learningExercisesController;

    /**
     * Label Learning Title
     */
    private JLabel lblLearningTitle;
    /**
     * Label FlashBox Name
     */
    private JLabel lblFlashBoxName;
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
     * Label Levels
     */
    private JLabel lblLevel;
    /**
     * Label Links
     */
    private  JLabel lblFlashCardLinks;
    /**
     * Text Field Learning Title
     */
    private JTextField txtLearningTitle;
    /**
     * Text Field Flash Box Name
     */
    private JTextField txtFlashBoxName;
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
     * Text Field Level
     */
    private JTextField txtLevel;
    /**
     * Flash Cards Links List
     */
    private JList<FlashCardLink> lstFlashCardLinks;
    /**
     * Links Scroll Pane
     */
    private JScrollPane scrollLinks;
    /**
     * Button Show Answer
     */
    private JButton btnShowAnswer;
    /**
     * Radio Button Correct
     */
    private JRadioButton rdbCorrect;
    /**
     * Radio Button Wrong
     */
    private JRadioButton rdbWrong;
    /**
     * Radio Button Group
     */
    private ButtonGroup radioButtonGroup;
    /**
     * Button Next
     */
    private JButton btnNext;
    /**
     * Label Current Question Number
     */
    private JLabel lblCurrentQuestionNumber;
    /**
     * Panel Question Answer
     */
    private JPanel panelQuestionAnswer;
    /**
     * Main Panel
     */
    private JPanel panelMain;
    /**
     * Buttons PAnel
     */
    private JPanel panelButtons;
    /**
     * Learning
     */
    private Learning learning;
    /**
     * Learning Details List
     */
    private List<LearningDetail> learningDetailList;
    /**
     * Current Question Index
     */
    private int currentQuestionIndex;

    /**
     * Constructor of the Class , creates the Learning Exercises Window.
     * @param title is the title of the Application that comes from MainProg.
     * @param pController is a Learning Exercises Controller
     * @param learning Learning
     * @throws SQLException if there is a problem at connecting to database
     */

    public LearningExercisesWindow(final String title, final LearningExercisesController pController, Learning learning) throws SQLException {
        super(title);
        self = this;
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        learningExercisesController = pController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createItems();
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.learning = learning;
        learningDetailList = learningExercisesController.loadLearningDetailsToMemory(learning.getLearningId());
        txtLearningTitle.setText(learning.getTitle());
        txtFlashBoxName.setText(learning.getFlashBox().getFlashBoxName());
        currentQuestionIndex = resumeQuestion();
    }

    /**
     * Create the Learning Exercises Window Items and set their sizes.
     */

    private void createItems() {

        /**
         * Creates the label Learning Title and set its dimensions.
         */
        lblLearningTitle = new JLabel("Learning Title");
        lblLearningTitle.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the label Card Box Name and set its dimensions.
         */
        lblFlashBoxName = new JLabel("Card Box Name");
        lblFlashBoxName.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the label Question and set its dimensions.
         */
        lblQuestion = new JLabel("Question");
        lblQuestion.setPreferredSize(new Dimension(700, 25));

        /**
         * Creates the label Answer and set its dimensions.
         */
        lblAnswer = new JLabel("Answer");
        lblAnswer.setPreferredSize(new Dimension(700, 25));

        /**
         * Creates the label Kewords and set its dimensions.
         */
        lblKeywords = new JLabel("Keywords");
        lblKeywords.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the label Level and set its dimensions.
         */
        lblLevel = new JLabel("Level");
        lblLevel.setPreferredSize(new Dimension(100, 25));

        /**
         * Creates the label Flash Card Links and set its dimensions.
         */
        lblFlashCardLinks = new JLabel("Flash Card Links");
        lblFlashCardLinks.setPreferredSize(new Dimension(700, 25));

        /**
         * Creates the Text Field Learning Title and set its dimensions.
         */
        txtLearningTitle = new JTextField();
        txtLearningTitle.setPreferredSize(new Dimension(600, 25));
        txtLearningTitle.setEditable(false);

        /**
         * Creates the Text Field Flash Box Name and set its dimensions.
         */
        txtFlashBoxName = new JTextField();
        txtFlashBoxName.setPreferredSize(new Dimension(600, 25));
        txtFlashBoxName.setEditable(false);

        /**
         * Creates the Question Text Area and set its dimensions.
         */
        txtQuestion = new JTextArea();
        txtQuestion.setPreferredSize(new Dimension(700, 150));
        txtQuestion.setEditable(false);

        /**
         * Creates the Answer Text Area and set its dimensions.
         */
        txtAnswer = new JTextArea();
        txtAnswer.setPreferredSize(new Dimension(700, 100));
        txtAnswer.setEditable(false);

        /**
         * Creates the Text Field Keywords and set its dimensions.
         */
        txtKeywords = new JTextField();
        txtKeywords.setPreferredSize(new Dimension(600, 25));
        txtKeywords.setEditable(false);

        /**
         * Creates the Text Field Level and set its dimensions.
         */
        txtLevel = new JTextField();
        txtLevel.setPreferredSize(new Dimension(600, 25));
        txtLevel.setEditable(false);

        /**
         * Creates the Flash Card Links List.
         */
        lstFlashCardLinks = new JList<>();

        /**
         * CCreates the Flash Card Links List scroll pane.
         */
        scrollLinks = new JScrollPane(lstFlashCardLinks);
        scrollLinks.setPreferredSize(new Dimension(700, 150));
        scrollLinks.setFont(new Font("Courier New", Font.PLAIN, 11));

        /**
         * Creates  Button Show Answer and set its dimensions.
         */
        btnShowAnswer = new JButton("Show Answer");
        btnShowAnswer.setPreferredSize(new Dimension(120, 25));

        /**
         * Defines the action when show answer is clicked.
         */
        btnShowAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LearningDetail learningDetail = learningDetailList.get(currentQuestionIndex);
                txtAnswer.setText(learningDetail.getFlashCard().getAnswer());
            }
        });

        /**
         * Creates the radio Button Correct and set its dimensions.
         */
        rdbCorrect = new JRadioButton("Correct");
        rdbCorrect.setPreferredSize(new Dimension(120, 25));

        /**
         * Defines the action when radio Button Correct is selected.
         */

        rdbCorrect.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            }
        });

        /**
         * Creates the radio Button Wrong and set its dimensions.
         */
        rdbWrong = new JRadioButton("Wrong");
        rdbWrong.setPreferredSize(new Dimension(120, 25));

        /**
         * Defines the action when radio Button Wrong is selected.
         */
        rdbWrong.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

            }
        });

        /**
         * Creates the radio Button Group.
         */
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(rdbCorrect);
        radioButtonGroup.add(rdbWrong);

        /**
         * Creates the Button Next.
         */
        btnNext = new JButton("Next");
        btnNext.setPreferredSize(new Dimension(120, 25));

        /**
         * Defines the action when Next Button is clicked.
         */
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!rdbCorrect.isSelected() && !rdbWrong.isSelected()) {
                        JOptionPane.showMessageDialog(self, "Select first correct or wrong!");
                    } else if (currentQuestionIndex < learningDetailList.size() ) {
                        LearningDetail currentLearningDetail= learningDetailList.get(currentQuestionIndex);
                        if (rdbCorrect.isSelected()) {
                            if(!currentLearningDetail.getLevel().getName().equals("completed")) {
                                currentLearningDetail.setLevelId(currentLearningDetail.getLevelId()+1);
                                currentLearningDetail.setLevel(learningExercisesController.getLevel(currentLearningDetail.getLevelId()));
                                learningExercisesController.updateLearningDetail(currentLearningDetail);
                            }
                        }
                        boolean completed = false;
                        if (currentQuestionIndex == learningDetailList.size()-1 ) {
                            learning.setResumeLearningDetailId(0);
                            learningExercisesController.getLearningsLogic().update(learning);
                            completed = learningExercisesController.getLearningsLogic().updateLearningIfCompleted(learning);
                            }
                        if (completed) {
                            JOptionPane.showMessageDialog(self, "This learning is completed!\nYou can study another learning!");
                            self.dispose();
                        } else {
                            currentQuestionIndex++;
                            showCurrentQuestion();
                            radioButtonGroup.clearSelection();
                        }
                    }
                }
                catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates Label Crrent Question Number and set its dimensions.
         */
        lblCurrentQuestionNumber = new JLabel();
        lblCurrentQuestionNumber.setPreferredSize(new Dimension(90, 25));

        /**
         * Creates Question Answer Panel and set its size.
         */
        panelQuestionAnswer = new JPanel();
        panelQuestionAnswer.setPreferredSize(new Dimension(750, 630));
        panelQuestionAnswer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Creates the Buttons Panel and set its size.
         */
        panelButtons = new JPanel();
        panelButtons.setPreferredSize(new Dimension(150, 630));
        panelButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Creates the main Panel and set its size.
         */
        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(950, 650));
        panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Adds items to the Question Answer panel according to the following order.
         */
        panelQuestionAnswer.setLayout(new FlowLayout());
        panelQuestionAnswer.add(lblLearningTitle);
        panelQuestionAnswer.add(txtLearningTitle);
        panelQuestionAnswer.add(lblFlashBoxName);
        panelQuestionAnswer.add(txtFlashBoxName);
        panelQuestionAnswer.add(lblQuestion);
        panelQuestionAnswer.add(txtQuestion);
        panelQuestionAnswer.add(lblAnswer);
        panelQuestionAnswer.add(txtAnswer);
        panelQuestionAnswer.add(lblKeywords);
        panelQuestionAnswer.add(txtKeywords);
        panelQuestionAnswer.add(lblLevel);
        panelQuestionAnswer.add(txtLevel);
        panelQuestionAnswer.add(lblFlashCardLinks);
        panelQuestionAnswer.add(scrollLinks);

        /**
         * Adds items to the Buttons panel according to the following order.
         */
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(btnShowAnswer);
        panelButtons.add(rdbCorrect);
        panelButtons.add(rdbWrong);
        panelButtons.add(btnNext);
        panelButtons.add(lblCurrentQuestionNumber);

        /**
         * Adds panels to the Main panel according to the following order.
         */
        panelMain.setLayout(new FlowLayout());
        panelMain.add(panelQuestionAnswer);
        panelMain.add(panelButtons);

        this.setLayout(new FlowLayout());
        this.add(panelMain);


        /**
         * Defines the action when the Learning Exercises selection Window is opened.
         */
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    learningExercisesController.getLearningsLogic().updateLearningIfCompleted(learning);
                    showCurrentQuestion();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    if (currentQuestionIndex < learningDetailList.size()) {
                        LearningDetail learningDetail = learningDetailList.get(currentQuestionIndex);
                        learning.setResumeLearningDetailId(learningDetail.getLearningDetailId());
                        learningExercisesController.getLearningsLogic().update(learning);
                        System.out.println("Current LearningDetailId Saved");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    public Learning getLearning() {
        return learning;
    }

    /**
     * Learning setter
     * @param learning
     */
    public void setLearning(Learning learning) throws SQLException {
        this.learning = learning;
        learningDetailList= learningExercisesController.loadLearningDetailsToMemory(learning.getLearningId());
        System.out.println("LearningDetailList Size: " + learningDetailList.size());
        txtLearningTitle.setText(learning.getTitle());
        txtFlashBoxName.setText(learning.getFlashBox().getFlashBoxName());
        currentQuestionIndex=resumeQuestion();
        showCurrentQuestion();
    }

    /**
     * Finds the next showable question and return it's index to assignt it to currentQuestionIndex
     * @return i integer value of index of the question, if there is still a question to resume,
     *         0 otherwise
     */
    public int resumeQuestion() {
        for (int i = 0; i < learningDetailList.size(); i++) {
            System.out.println(learning.getResumeLearningDetailId() + " " + learningDetailList.get(i).getLearningDetailId());
            if (learning.getResumeLearningDetailId() == learningDetailList.get(i).getLearningDetailId()) {
                return i;
            }
        }
        return 0;
    }
    /**
     * Shows the Current Question.
     */
    public void showCurrentQuestion() throws SQLException {
        if (currentQuestionIndex < learningDetailList.size()) {
            lblCurrentQuestionNumber.setText("Number:" + "\n" + (currentQuestionIndex+1) +"/"+ learningDetailList.size());
            LearningDetail learningDetail = learningDetailList.get(currentQuestionIndex);
            FlashCard flashCard = learningDetail.getFlashCard();
            txtQuestion.setText(flashCard.getQuestion());
            txtKeywords.setText(flashCard.getKeywords());
            txtLevel.setText(learningDetail.getLevelId()+" - "+ learningDetail.getLevel().getName());
            txtAnswer.setText("");
            learningExercisesController.loadFlashCardLinks(learningDetail.getFlashCardId());
        } else {
            lblCurrentQuestionNumber.setText("Number: 0/0");
            txtQuestion.setText("");
            txtKeywords.setText("");
            txtLevel.setText("");
            txtAnswer.setText("");
            System.out.println("There is no question in this learning");
            JOptionPane.showMessageDialog(this,"No available question now!\nYou can study later.");
            self.dispose();
        }
        btnNext.setEnabled(currentQuestionIndex != learningDetailList.size());
    }

    /**
     * Fills the Flash Cards Links list when the Learning Exercises window is opened.
     * @param flashCardLinks  flash card links list
     */
    public void fillFlashCardLinks(List<FlashCardLink> flashCardLinks) {
        DefaultListModel<FlashCardLink> model = new DefaultListModel<>();
        for (FlashCardLink flashCardLink : flashCardLinks) {
            model.addElement(new FlashCardLinkListBoxView(flashCardLink));
        }
        lstFlashCardLinks.setModel(model);
    }
}
