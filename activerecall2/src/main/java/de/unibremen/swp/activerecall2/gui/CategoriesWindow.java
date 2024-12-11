package de.unibremen.swp.activerecall2.gui;

import de.unibremen.swp.activerecall2.controller.CategoriesController;
import de.unibremen.swp.activerecall2.model.Category;
import de.unibremen.swp.activerecall2.model.CategoryComboBoxView;

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
 * This class creates the Categories window of the Application.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class CategoriesWindow extends JFrame {
    /**
     * Width of the Categories Window
     */
    private static final int DEFAULT_WIDTH = 500;
    /**
     * Height of the Categories Window
     */
    private static final int DEFAULT_HEIGHT = 500;
    /**
     * Categories Controller
     */
    private final transient CategoriesController categoriesController;

    /**
     * Label Category ID
     */
    private JLabel lblCategoryId;
    /**
     * Label Category Name
     */
    private JLabel lblCategeryName;
    /**
     * Text Field Category ID
     */
    private JTextField txtCategoryId;
    /**
     * Text Field Category Name
     */
    private JTextField txtCategoryName;
    /**
     * List of the Categories
     */
    private JList<Category> lstCategories;
    /**
     * Scroll Pane Categories
     */
    private JScrollPane scrollCategory;
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
     * Categories PAnel
     */
    private JPanel panelCategories;
    /**
     * Buttons Panel
     */
    private JPanel panelButtons;
    /**
     * Main Panel
     */
    private JPanel panelMain;
    /**
     * Categories Window
     */
    private CategoriesWindow self;

    /**
     * Constructor of the Class , creates the Categories Window of the application.
     * @Param title is the title of the Application that comes from MainProg.
     * @Param pController is a Categories Controller
     */
    public CategoriesWindow(final String title, final CategoriesController pController) {
        super(title);
        self = this;
        if (pController == null) {
            throw new IllegalArgumentException("Controller must not be null!");
        }
        categoriesController = pController;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createItems();
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }
    /**
     * Create the Category Window Items and set their sizes.
     */
    private void createItems() {
        /**
         * Creates the Label Category ID, Label Name , Text Field ID , Text Field Name and set their sizes.
         */
        lblCategoryId = new JLabel("Category Id");
        lblCategoryId.setPreferredSize(new Dimension(100, 25));

        lblCategeryName = new JLabel("Category Name");
        lblCategeryName.setPreferredSize(new Dimension(100, 25));

        txtCategoryId = new JTextField();
        txtCategoryId.setPreferredSize(new Dimension(230, 25));
        txtCategoryId.setEditable(false);

        txtCategoryName = new JTextField();
        txtCategoryName.setPreferredSize(new Dimension(230, 25));

        /**
         * Creates the Button Clear , set its size and defines the action when the clear button is clicked.
         */
        btnClear = new JButton("Clear");
        btnClear.setPreferredSize(new Dimension(80, 25));
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCategoryId.setText("");
                txtCategoryName.setText("");
            }
        });
        /**
         * Defines the action when the Add button is clicked and gives the possible errors with the corresponding message .
         */
        btnAdd = new JButton("Add");
        btnAdd.setPreferredSize(new Dimension(80, 25));
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtCategoryName.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Category name cannot be empty!");
                    } else {
                        if (!categoriesController.hasCategory(txtCategoryName.getText())) {
                            Category category = new Category(txtCategoryName.getText());
                            if (categoriesController.addCategory(category) != 1) {
                                JOptionPane.showMessageDialog(self, "Category could not be added!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(self, "Category name exists!");
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
         * Creates the Button Update, set its size.
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
                    if (txtCategoryName.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(self, "Category name cannot be empty!");
                    } else {
                        Category category = new Category(Integer.valueOf(txtCategoryId.getText()), txtCategoryName.getText());
                        if (categoriesController.updateCategory(category) != 1) {
                            JOptionPane.showMessageDialog(self, "Category could not be updated!");
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
         * Creates the Button Delete, set its size.
         */
        btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(new Dimension(80, 25));
        /**
         * Defines the action when the Delete button is clicked and gives the possible errors with the corresponding message..
         */
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtCategoryId.getText().trim().equals("")) {
                        int id = Integer.valueOf(txtCategoryId.getText());
                        if (categoriesController.categoryHasRelatedRecords(id)) {
                            JOptionPane.showMessageDialog(self, "You cannot delete this category!\nThe category has flash cards or is attached to a card box!");
                        }
                        else {
                            int result = JOptionPane.showConfirmDialog(self, "Are you sure you want to delete?", "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                            if (result == JOptionPane.OK_OPTION) {
                                if (categoriesController.deleteCategory(id) != 1) {
                                    JOptionPane.showMessageDialog(self, "Category could not be deleted!");
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(self, "Select a category!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(self, "Sql Error!\n\n" + ex.getMessage());
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(self, exp.getClass().getName() + ": Error : " + exp.getMessage());
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
         * Creates the list of the scrollable categories set its size.
         */
        lstCategories = new JList<>();
        scrollCategory = new JScrollPane(lstCategories);
        scrollCategory.setPreferredSize(new Dimension(330, 300));
        /**
         * Defines the action when a Category has been selected from the Categorys list.
         */
        lstCategories.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (lstCategories.getSelectedIndex() >= 0) {
                    Category category = lstCategories.getSelectedValue();
                    txtCategoryId.setText(category.getCategoryId() + "");
                    txtCategoryName.setText(category.getCategoryName());
                }
            }
        });
        /**
         * Creates the Categories Panel and set its size.
         */
        panelCategories = new JPanel();
        panelCategories.setPreferredSize(new Dimension(350, 400));
        //panelCategories.setBorder(BorderFactory.createLineBorder(Color.RED));

        /**
         * Creates the Buttons Panel and set its size.
         */
        panelButtons = new JPanel();
        panelButtons.setPreferredSize(new Dimension(100, 400));
        //panelButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Creates the Main Panel and set its size.
         */
        panelMain = new JPanel();
        panelMain.setPreferredSize(new Dimension(470, 420));
        panelMain.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        /**
         * Adds buttons to the Category Panel with the following order.
         */
        panelCategories.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelCategories.add(lblCategoryId);
        panelCategories.add(txtCategoryId);
        panelCategories.add(lblCategeryName);
        panelCategories.add(txtCategoryName);
        panelCategories.add(scrollCategory);
        /**
         * Adds buttons to the Button Panel with the following order.
         */
        panelButtons.setLayout(new FlowLayout());
        panelButtons.add(btnClear);
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClose);

        /**
         * Sets the layout and adds the button and category panels to the main panel.
         */
        panelMain.setLayout(new FlowLayout());
        panelMain.add(panelCategories);
        panelMain.add(panelButtons);

        this.setLayout(new FlowLayout());
        this.add(panelMain);

        /**
         * Defines the action when the category window is opened.
         */
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    categoriesController.loadCategories();
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
                    categoriesController.loadCategories();
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
     * Fills the categories list when the categories window is opened.
     */
    public void fillCategories(List<Category> categories) {
        DefaultListModel<Category> model = new DefaultListModel<>();
        for (Category category : categories) {
            model.addElement(new CategoryComboBoxView(category));
        }
        lstCategories.setModel(model);
    }
}

