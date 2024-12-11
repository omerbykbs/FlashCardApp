package de.unibremen.swp.activerecall2.gui;

import de.unibremen.swp.activerecall2.controller.GlossaryController;
import de.unibremen.swp.activerecall2.model.Category;
import de.unibremen.swp.activerecall2.model.CategoryComboBoxView;
import de.unibremen.swp.activerecall2.model.FlashCard;
import de.unibremen.swp.activerecall2.model.FlashCardListBoxView;

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

/**
 * This class creates the Gloassary window of the Application.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class GlossaryWindow extends JFrame {

    /**
     * Width of the Glossary Window
     */
    private static final int DEFAULT_WIDTH = 800;
    /**
     * Height of the Glossary Window
     */
    private static final int DEFAULT_HEIGHT = 750;

    /**
     * Glossary Contoller
     */
    private final transient GlossaryController glossaryController;

    /**
     * Options for the filter to select.
     */
    public final static String ORDERBY_FLASHCARDID= "order by flashCardId";
    public final static String ORDERBY_CATEGORYID= "order by flashCards.categoryId";
    public final static String ORDERBY_CATEGORYID_QUESTION= "order by flashCards.categoryId, question";
    public final static String ORDERBY_CATEGORYNAME_QUESTION= "order by categoryName, question";
    public final static String ORDERBY_QUESTION= "order by question";


    /**
     * Label Flash Card ID
     */
    private JLabel lblFlashCardId;
    /**
     * Label Categories
     */
    private  JLabel lblCategories;
    /**
     * Combo Box Categories
     */
    private  JComboBox<Category> cmbCategories;
    /**
     * Radio Button Show Selected Categories
     */
    private JRadioButton rdbShowSelectedCategories;
    /**
     * Radio Button Show All
     */
    private JRadioButton rdbShowAll;
    /**
     * Label Sort By
     */
    private  JLabel lblSortBy;
    /**
     * Combobox Sort by
     */
    private  JComboBox<String> cmbSortBy;
    /**
     * Label Search
     */
    private JLabel lblSearch;
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
     * Text Field Card ID
     */
    private JTextField txtFlashCardId;
    /**
     * Text Field Search
     */
    private  JTextField txtSearch;
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
    private  JTextField txtKeywords;
    /**
     * Combobox Search by
     */
    private  JComboBox<String> cmbSearchBy;
    /**
     * Button Search by
     */
    private  JButton btnSearchBy;
    /**
     * Flash cards list
     */
    private JList<FlashCard> lstFlashCards;
    /**
     * Flash Cards Scroll pane
     */
    private JScrollPane scrollFlashCards;

    /**
     * Button close
     */
    private  JButton btnClose;
    /**
     * Panel
     */
    private  JPanel panel;
    /**
     * Glossary window
     */
    private GlossaryWindow self;

    private  String SORTBY;

    /**
     * Constructor of the Class , creates the Glossary Window.
     * @Param title is the title of the Application that comes from MainProg.
     * @Param pController is a GlossaryController
     */

    public GlossaryWindow(final String title, final GlossaryController pController) {
        super(title);
        self=this;
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        glossaryController = pController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createItems();

        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        SORTBY= ORDERBY_FLASHCARDID;
    }
    /**
     * Create the Glossary Window Items and set their sizes.
     */

    private void createItems()
    {

        /**
         * Creates the Label FlasCard ID, Label Categories , Combobox , and set their sizes.
         */

        lblFlashCardId= new JLabel("Flash Card Id");
        lblFlashCardId.setPreferredSize(new Dimension(100,25));

        lblCategories= new JLabel("Categories");
        lblCategories.setPreferredSize(new Dimension(100,25));

        cmbCategories = new JComboBox<>();
        cmbCategories.setPreferredSize(new Dimension(600,25));

        /**
         * Creates the Combobox and defines the action when its clicked.
         */
        cmbCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    reloadFlashCardList();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n"+ ex.getMessage());
                }
            }
        });

        /**
         * Creates the Radio Buttons "Show Selected Categories" and set its dimensions.
         */
        rdbShowSelectedCategories = new JRadioButton("Show Selected Categories");
        rdbShowSelectedCategories.setPreferredSize(new Dimension(200,25));
        /**
         * Defines the action when "Show Selected Categories" is selected.
         */
        rdbShowSelectedCategories.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    reloadFlashCardList();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n"+ ex.getMessage());
                }
            }
        });
        /**
         * Creates the Radio Button "Show All" and set its dimensions.
         */
        rdbShowAll = new JRadioButton("Show All");
        rdbShowAll.setPreferredSize(new Dimension(100,25));
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
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n"+ ex.getMessage());
                }
            }
        });

        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(rdbShowAll);
        radioButtonGroup.add(rdbShowSelectedCategories);

        /**
         * Creates the label Sort by and set its dimensions.
         */

        lblSortBy= new JLabel("Sort By");
        lblSortBy.setPreferredSize(new Dimension(115,25));
        lblSortBy.setHorizontalAlignment(JLabel.RIGHT);

        /**
         * Creates the combobox Sort by and set its dimensions.
         */
        cmbSortBy = new JComboBox<>();
        cmbSortBy.setPreferredSize(new Dimension(305,25));

        /**
         * Defines the sort options.
         */
        DefaultComboBoxModel<String> sortmodel = new DefaultComboBoxModel<>();
        sortmodel.addElement(ORDERBY_FLASHCARDID);
        sortmodel.addElement(ORDERBY_CATEGORYID);
        sortmodel.addElement(ORDERBY_CATEGORYID_QUESTION);
        sortmodel.addElement(ORDERBY_CATEGORYNAME_QUESTION);
        sortmodel.addElement(ORDERBY_QUESTION);
        cmbSortBy.setModel(sortmodel);
        cmbSortBy.setSelectedIndex(0);

        /**
         * Defines the action when the sort option selected.
         */
        cmbSortBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if( cmbSortBy.getSelectedIndex()>=0) {
                        SORTBY= (String) cmbSortBy.getSelectedItem();
                        reloadFlashCardList();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n"+ ex.getMessage());
                }
            }
        });

        /**
         * Creates the labels according to the following order.
         */

        lblSearch = new JLabel("Search");
        lblSearch.setPreferredSize(new Dimension(85,25));


        lblQuestion = new JLabel("Question");
        lblQuestion.setPreferredSize(new Dimension(700,25));


        lblAnswer = new JLabel("Answer");
        lblAnswer.setPreferredSize(new Dimension(700,25));

        lblKeywords = new JLabel("Keywords");
        lblKeywords.setPreferredSize(new Dimension(700,25));

        /**
         * Creates the text fields according to the following order.
         */
        txtFlashCardId= new JTextField();
        txtFlashCardId.setPreferredSize(new Dimension(600,25));
        txtFlashCardId.setEditable(false);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(300,25));


        /**
         * Creates the combobox and adds its elements.
         */
        cmbSearchBy= new JComboBox<>();
        cmbSearchBy.setPreferredSize(new Dimension(200,25));
        DefaultComboBoxModel<String> searchByModel = new DefaultComboBoxModel<>();
        searchByModel.addElement("Search By Question");
        searchByModel.addElement("Search By Keywords");
        cmbSearchBy.setModel(searchByModel);

        /**
         * Creates the button Search and set its dimensions.
         */
        btnSearchBy= new JButton("Search");
        btnSearchBy.setPreferredSize(new Dimension(100,25));

        /**
         * Defines the action  when the search by button is clicked.
         */
        btnSearchBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    reloadFlashCardList();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n"+ ex.getMessage());
                }
            }
        });
        /**
         * Creates the Text areas for Question and Answer and set their sizes.
         */
        txtQuestion = new JTextArea();
        txtQuestion.setPreferredSize(new Dimension(700,100));
        txtQuestion.setEditable(false);

        txtAnswer = new JTextArea();
        txtAnswer.setPreferredSize(new Dimension(700,100));
        txtAnswer.setEditable(false);

        /**
         * Creates the Text Fields for Keywords and set its sizes.
         */
        txtKeywords = new JTextField();
        txtKeywords.setPreferredSize(new Dimension(700,25));


        /**
         * Creates the scrollable flash card list, set its font and set its size.
         */
        lstFlashCards = new JList<>();
        scrollFlashCards = new JScrollPane(lstFlashCards);
        scrollFlashCards.setPreferredSize(new Dimension(700,200));
        lstFlashCards.setFont(new Font("Courier New", Font.PLAIN, 11));

        /**
         * Defines the actions on flash card list.
         */
        lstFlashCards.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(lstFlashCards.getSelectedIndex()>=0)
                {
                    FlashCard flashCard= lstFlashCards.getSelectedValue();

                    txtFlashCardId.setText(flashCard.getFlashCardId()+"");
                    txtQuestion.setText(flashCard.getQuestion());
                    txtAnswer.setText(flashCard.getAnswer());
                    txtKeywords.setText(flashCard.getKeywords());

                    selectComboBoxCategory(flashCard.getCategoryId());
                }
            }
        });

        /**
         * Creates the close button and defines the action when its clicked.
         */
        btnClose= new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });

        /**
         * Creates the panel and set its border and size.
         */
        panel= new JPanel();
        panel.setPreferredSize(new Dimension(750,690));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Adds items to the panel according to the following order.
         */
        panel.setLayout(new FlowLayout());
        panel.add(lblFlashCardId);
        panel.add(txtFlashCardId);
        panel.add(lblCategories);
        panel.add(cmbCategories);
        panel.add(rdbShowSelectedCategories);
        panel.add(rdbShowAll);
        panel.add(lblSortBy);
        panel.add(cmbSortBy);
        panel.add(lblSearch);
        panel.add(txtSearch);
        panel.add(cmbSearchBy);
        panel.add(btnSearchBy);
        panel.add(lblQuestion);
        panel.add(txtQuestion);
        panel.add(lblAnswer);
        panel.add(txtAnswer);
        panel.add(lblKeywords);
        panel.add(txtKeywords);
        panel.add(scrollFlashCards);
        panel.add(btnClose);

        this.setLayout(new FlowLayout());
        this.add(panel);

        /**
         * Defines the action when the Glossary window is opened.
         */
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    glossaryController.loadCategories();
                    reloadFlashCardList();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n"+ ex.getMessage());
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
                    glossaryController.loadCategories();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n"+ ex.getMessage());
                }
            }
            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    /**
     * Filters and shows the selected categories and defines the search option (Question / Keyword).
     * @Returns filtered String.
     */
    private String buildFilterString() {
        String filterString= "where 1=1 ";
        if( rdbShowSelectedCategories.isSelected()) {
            Category category =(Category)cmbCategories.getSelectedItem();
            filterString=filterString+ "and flashcards.categoryId="+category.getCategoryId();
        }
        if( cmbSearchBy.getSelectedIndex()==0) {
            String[] words= txtSearch.getText().split("[,; ]");
            for(String word: words) {
                if( !word.isEmpty()) {
                    filterString=filterString + " and question like '%" +word + "%' ";
                }
            }
        } else if ( cmbSearchBy.getSelectedIndex()==1) {
            String[] words= txtSearch.getText().split("[,; ]");
            for(String word: words) {
                if( !word.isEmpty()) {
                    filterString=filterString + " and keywords like '%" +word + "%' ";
                }
            }
        }
        System.out.println(filterString);
        return filterString;
    }
    /**
     * Reloads the flash card list when some changes took place at the list.
     */
    private void reloadFlashCardList() throws SQLException {
        String filterString= buildFilterString();
        glossaryController.loadFlashCards(SORTBY,filterString);
    }

    /**
     * Fills the flash card list when the glossary window is opened.
     * @param flashCards  flash card list
     */
    public void fillFlashCards(java.util.List<FlashCard> flashCards)
    {
        DefaultListModel<FlashCard> model= new DefaultListModel<>();
        for( FlashCard flashCard : flashCards)
        {
            model.addElement(new FlashCardListBoxView(flashCard));
        }
        lstFlashCards.setModel(model);
    }
    /**
     * Fills the categories list when the glossary window is opened.
     * @param categories categories list
     */
    public void fillCategories(List<Category> categories)
    {
        DefaultComboBoxModel<Category> model= new DefaultComboBoxModel<>();
        for( Category category : categories)
        {
            model.addElement(new CategoryComboBoxView(category));
        }
        cmbCategories.setModel(model);
    }

    /**
     * Filters the getting flash cards in the list with category id.
     * @param id category id
     */
    public void selectComboBoxCategory(int id)
    {
        DefaultComboBoxModel<Category> model=(DefaultComboBoxModel<Category>) cmbCategories.getModel();
        if( model!=null && model.getSize()>0)
        {
            for(int i=0; i< model.getSize(); i++)
            {
                if( model.getElementAt(i).getCategoryId()==id)
                {
                    model.setSelectedItem(model.getElementAt(i));
                }
            }
        }
    }
}
