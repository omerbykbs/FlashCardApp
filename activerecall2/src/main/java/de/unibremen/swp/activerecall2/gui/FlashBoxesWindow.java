package de.unibremen.swp.activerecall2.gui;

import de.unibremen.swp.activerecall2.controller.FlashBoxesController;
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
import java.util.List;

/**
 * This class creates the Flash Boxes window of the Application.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class FlashBoxesWindow extends JFrame {
    /**
     * Width of the Flash Boxes Window
     */
    private static final int DEFAULT_WIDTH = 700;
    /**
     * Height of the Flash Boxes Window
     */
    private static final int DEFAULT_HEIGHT = 600;

    /**
     * Flash Boxes Controller
     */
    private final transient FlashBoxesController flashBoxesController;
    /**
     * Label FlashBox ID
     */
    private JLabel lblFlashBoxId;
    /**
     * Label FlashBox Name
     */
    private JLabel lblFlashBoxName;
    /**
     * Text Field FlashBox ID
     */
    private JTextField txtFlashBoxId;
    /**
     * Text Field FlashBox Name
     */
    private JTextField txtFlashBoxName;
    /**
     * Flash Boxes List
     */
    private JList<FlashBox> lstFlashBoxes;
    /**
     * FlashBoxes Scroll Pane
     */
    private JScrollPane scrollFlashBoxes;
    /**
     * Label Categories
     */
    private JLabel lblCategories;
    /**
     * Categories Combobox
     */
    private JComboBox<Category> cmbCategories;
    /**
     * Flash Box details list
     */
    private JList<FlashBoxDetail> lstFlashBoxDetails;
    /**
     * Flashbox details scroll pane
     */
    private JScrollPane scrollFlashBoxDetails;
    /**
     * Flash cards list
     */
    private JList<FlashCard> lstFlashCards;
    /**
     * Flash card list scroll pane
     */
    private JScrollPane scrollFlashCards;
    /**
     * Clear Button
     */
    private JButton btnClear;
    /**
     * Add Button
     */
    private JButton btnAdd;
    /**
     * Update Button
     */
    private JButton btnUpdate;
    /**
     * Delete Button
     */
    private JButton btnDelete;
    /**
     * Close Button
     */
    private JButton btnClose;
    /**
     * Add Category Button
     */
    private JButton btnAddCategory;
    /**
     * Remove Category Button
     */
    private JButton btnRemoveCategory;
    /**
     * Label Flash Cards
     */
    private JLabel lblFlashCards;
    /**
     * Flash Boxes Panel
     */
    private JPanel panelFlashBoxes;
    /**
     * WButtons panel
     */
    private JPanel panelButtons;
    /**
     * Main panel
     */
    private JPanel panelMain;
    /**
     * Flash Boxes Window
     */
    private FlashBoxesWindow self;
    /**
     * Constructor of the Class , creates the FlashBoxes Window.
     * @Param title is the title of the Application that comes from MainProg.
     * @Param pController is a FlashBoxController
     */
    public FlashBoxesWindow(final String title, final FlashBoxesController pController) {
        super(title);
        self = this;
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        flashBoxesController = pController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createItems();
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }
    /**
     * Creates the Flash Boxes Window Items and set their sizes.
     */
    private void createItems() {
        /**
         * Creates the Label FlashBox ID, Label Name , Text Field ID , Text Field Name and set their sizes.
         */

        lblFlashBoxId = new JLabel("Card Box Id");
        lblFlashBoxId.setPreferredSize(new Dimension(100, 25));

        lblFlashBoxName = new JLabel("Card Box Name");
        lblFlashBoxName.setPreferredSize(new Dimension(100, 25));

        txtFlashBoxId = new JTextField();
        txtFlashBoxId.setPreferredSize(new Dimension(380, 25));
        txtFlashBoxId.setEditable(false);

        txtFlashBoxName = new JTextField();
        txtFlashBoxName.setPreferredSize(new Dimension(380, 25));

        /**
         * Creates the Button Clear , set its size and defines the action when the clear button is clicked.
         */

        btnClear = new JButton("Clear");
        btnClear.setPreferredSize(new Dimension(80, 25));
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtFlashBoxId.setText("");
                txtFlashBoxName.setText("");

            }
        });
        /**
         * Creates the Button Add, set its size, defines the action when the Add button is clicked and gives the possible errors with the corresponding  .
         * message
         */
        btnAdd = new JButton("Add");
        btnAdd.setPreferredSize(new Dimension(80, 25));
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtFlashBoxName.getText().trim().isEmpty()) {
                    try {
                        if (!flashBoxesController.hasFlashBox(txtFlashBoxName.getText())) {
                            FlashBox flashBox = new FlashBox(txtFlashBoxName.getText());
                            if (flashBoxesController.addFlashBox(flashBox) != 1) {
                                JOptionPane.showMessageDialog(self, "Card box could not be added!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(self, "Card box name exists!");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                    } catch (Exception exp) {
                        JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(self, "Card box name can't be empty!");
                }

            }
        });

        /**
         * Creates the Button Update, set its size, defines the action when the Update button is clicked and
         * gives the possible errors with the corresponding message.
         */
        btnUpdate = new JButton("Update");
        btnUpdate.setPreferredSize(new Dimension(80, 25));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtFlashBoxId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(self, "Select a card box to update!");
                } else if (txtFlashBoxName.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(self, "Card box name can't be empty!");
                } else {
                    try {
                        FlashBox flashBox = new FlashBox(Integer.parseInt(txtFlashBoxId.getText()), txtFlashBoxName.getText());
                        if (flashBoxesController.updateFlashBox(flashBox) != 1) {
                            JOptionPane.showMessageDialog(self, "Card box could not be updated!");
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
         * Creates the Button Delete, set its size and defines the action when the Delete button is clicked and
         * gives the possible errors with the corresponding message..
         */
        btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(new Dimension(80, 25));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtFlashBoxId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(self, "Select a card box to delete!");
                } else {
                    try {
                        int id = Integer.valueOf(txtFlashBoxId.getText());
                        if (flashBoxesController.flashBoxHasRelatedRecords(id)) {
                            JOptionPane.showMessageDialog(self, "You can't delete this card box!\nCard box is attached to a learning!");
                        } else {
                            int result = JOptionPane.showConfirmDialog(self, "Are you sure you want to delete?", "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                            if (result == JOptionPane.OK_OPTION) {
                                //int id = Integer.valueOf(txtFlashBoxId.getText());
                                if (flashBoxesController.deleteFlashBox(id) != 1) {
                                    JOptionPane.showMessageDialog(self, "Card box could not be deleted!");
                                }
                            }
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
         * Creates the Button Close, set its size and defines the action when the Close button is clicked.
         */
        btnClose = new JButton("Close");
        btnClose.setPreferredSize(new Dimension(80, 25));
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.dispose();
            }
        });


        /**
         * Creates the list of the scrollable FlashBoxes and set its size and defines the list action , gives the possible errors.
         * with corresponding message
         */
        lstFlashBoxes = new JList<>();
        scrollFlashBoxes = new JScrollPane(lstFlashBoxes);
        scrollFlashBoxes.setPreferredSize(new Dimension(485, 200));

        lstFlashBoxes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    if (lstFlashBoxes.getSelectedIndex() >= 0) {
                        FlashBox flashBox = lstFlashBoxes.getSelectedValue();
                        txtFlashBoxId.setText(flashBox.getFlashBoxId() + "");
                        txtFlashBoxName.setText(flashBox.getFlashBoxName());
                        flashBoxesController.loadFlashBoxDetails(flashBox.getFlashBoxId());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates the label categoris and set its size.
         */
        lblCategories = new JLabel("Categories");
        lblCategories.setPreferredSize(new Dimension(90, 25));

        /**
         * Creates the categories combobox and set its size.
         */
        cmbCategories = new JComboBox<>();
        cmbCategories.setPreferredSize(new Dimension(390, 25));

        /**
         * Creates the add categorie button and set its size.
         */
        btnAddCategory = new JButton("Add");
        btnAddCategory.setPreferredSize(new Dimension(90, 25));

        /**
         * Defines the action when add category button is clicked and gives the errors with the corresponding message.
         */
        btnAddCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtFlashBoxId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(self, "Select a card box to add a category!");
                } else if (cmbCategories.getSelectedIndex() < 0) {
                    JOptionPane.showMessageDialog(self, "Select a category to add!");
                } else {
                    try {
                        int flashBoxId = Integer.parseInt(txtFlashBoxId.getText());
                        Category category = (Category) cmbCategories.getSelectedItem();
                        boolean hasFlashCards= flashBoxesController.hasCategoryFlashCards(category.getCategoryId());
                        if( hasFlashCards) {
                            if (!flashBoxesController.hasFlashBoxDetail(flashBoxId, category.getCategoryId())) {
                                FlashBoxDetail flashBoxDetail = new FlashBoxDetail(flashBoxId, category.getCategoryId());
                                if (flashBoxesController.addFlashBoxDetail(flashBoxDetail) != 1) {
                                    JOptionPane.showMessageDialog(self, "Category could not be added!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(self, "Selected card box already has the selected category!");
                            }
                        }
                        else {
                            JOptionPane.showMessageDialog(self, "Selected category doesn't have any flash cards!\nPlease first add flash cards into selected category to add!");
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
         * Creates the remove categorie button and set its size.
         */
        btnRemoveCategory = new JButton("Remove");
        btnRemoveCategory.setPreferredSize(new Dimension(90, 25));

        /**
         * Defines the action when remove category button is clicked and gives the errors with the corresponding message.
         */
        btnRemoveCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (lstFlashBoxDetails.getSelectedIndex() >= 0) {
                        int id = Integer.valueOf(txtFlashBoxId.getText());
                        if (flashBoxesController.flashBoxHasRelatedRecords(id)) {
                            JOptionPane.showMessageDialog(self, "You can't remove this category from card box!\nThe category is attached to a learning!");
                        } else {
                            int result = JOptionPane.showConfirmDialog(self, "Are you sure you want to delete?", "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                            if (result == JOptionPane.OK_OPTION) {
                                FlashBoxDetail flashBoxDetail = lstFlashBoxDetails.getSelectedValue();
                                if (flashBoxesController.deleteFlashBoxDetail(flashBoxDetail.getFlashBoxDetailId()) != 1) {
                                    JOptionPane.showMessageDialog(self, "Category couldn't be deleted!");
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(self, "Select a category to remove from card box!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }

        });
        /**
         * Creates the scrollable flash box details list and set its dimensions.
         */
        lstFlashBoxDetails = new JList<>();
        scrollFlashBoxDetails = new JScrollPane(lstFlashBoxDetails);
        scrollFlashBoxDetails.setPreferredSize(new Dimension(185, 200));
        /**
         * Defines the list actions and gives the errors with the corresponding message.
         */
        lstFlashBoxDetails.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    if (lstFlashBoxDetails.getSelectedIndex() >= 0) {
                        FlashBoxDetail flashBoxDetail = lstFlashBoxDetails.getSelectedValue();
                        flashBoxesController.loadFlashCards(flashBoxDetail.getCategoryId());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
                }
            }
        });

        /**
         * Creates the scrollable flash cards list and set its dimensions.
         */
        lstFlashCards = new JList<>();
        scrollFlashCards = new JScrollPane(lstFlashCards);
        scrollFlashCards.setPreferredSize(new Dimension(295, 200));

        /**
         * Creates the flash boxes panel and set its size.
         */
        panelFlashBoxes = new JPanel();
        panelFlashBoxes.setPreferredSize(new Dimension(500, 540));

        /**
         * Creates the buttons panel and set its size.
         */
        panelButtons = new JPanel();
        panelButtons.setPreferredSize(new Dimension(100, 540));

        /**
         * Creates the main panel and set its size.
         */
        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(650, 550));
        panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Creates the label flash cards and set its dimensions.
         */
        lblFlashCards = new JLabel("Flash Cards");
        lblFlashCards.setPreferredSize(new Dimension(300, 25));

        /**
         * Adds the labels , text fields and buttons to the panel according to the following order and sets the layout.
         */
        panelFlashBoxes.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelFlashBoxes.add(lblFlashBoxId);
        panelFlashBoxes.add(txtFlashBoxId);
        panelFlashBoxes.add(lblFlashBoxName);
        panelFlashBoxes.add(txtFlashBoxName);
        panelFlashBoxes.add(scrollFlashBoxes);
        panelFlashBoxes.add(lblCategories);
        panelFlashBoxes.add(cmbCategories);
        panelFlashBoxes.add(btnAddCategory);
        panelFlashBoxes.add(btnRemoveCategory);
        panelFlashBoxes.add(lblFlashCards);
        panelFlashBoxes.add(scrollFlashBoxDetails);
        panelFlashBoxes.add(scrollFlashCards);
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(btnClear);
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClose);
        panelMain.setLayout(new FlowLayout());
        panelMain.add(panelFlashBoxes);
        panelMain.add(panelButtons);

        this.setLayout(new FlowLayout());
        this.add(panelMain);


        /**
         * Defines the action when the flash boxes window is opened.
         */
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    flashBoxesController.loadCategoriesComboBox();
                    flashBoxesController.loadFlashBoxes();
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
                    flashBoxesController.loadCategoriesComboBox();
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
     * Fills the categories combobox when the Flash Boxes window is opened.
     * @param categories  categories list
     */
    public void fillCategoriesComboBox(List<Category> categories) {
        DefaultComboBoxModel<Category> model = new DefaultComboBoxModel<>();
        for (Category category : categories) {
            model.addElement(new CategoryComboBoxView(category));
        }
        cmbCategories.setModel(model);
    }
    /**
     * Fills the flash boxes list when the Flash Boxes window is opened.
     * @param categories  categories list
     */
    public void fillFlashBoxes(List<FlashBox> categories) {
        DefaultListModel<FlashBox> model = new DefaultListModel<>();
        for (FlashBox flashBox : categories) {
            model.addElement(new FlashBoxListBoxView(flashBox));
        }
        lstFlashBoxes.setModel(model);
    }
    /**
     * Fills the flashbox details list when the Flash Boxes window is opened.
     * @param categories  categories list
     */
    public void fillFlashBoxDetails(List<FlashBoxDetail> categories) {
        DefaultListModel<FlashBoxDetail> model = new DefaultListModel<>();
        for (FlashBoxDetail flashBoxDetail : categories) {
            model.addElement(new FlashBoxDetailListBoxView(flashBoxDetail));
        }
        lstFlashBoxDetails.setModel(model);
    }
    /**
     * Fills the flash card list when the Flash Boxes window is opened.
     * @param flashCards  flash card list
     */
    public void fillFlashCards(List<FlashCard> flashCards) {
        DefaultListModel<FlashCard> model = new DefaultListModel<>();
        for (FlashCard flashCard : flashCards) {
            model.addElement(new FlashCardListBoxView(flashCard));
        }
        lstFlashCards.setModel(model);
    }
}
