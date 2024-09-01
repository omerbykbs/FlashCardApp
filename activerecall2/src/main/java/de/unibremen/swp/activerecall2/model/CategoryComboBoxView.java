package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a category combo box view in the data model.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */

public class CategoryComboBoxView extends de.unibremen.swp.activerecall2.model.Category {
    /**
     * Let category display in a combo box.
     * @param category a category to put in the category combo box.
     */
    public CategoryComboBoxView(de.unibremen.swp.activerecall2.model.Category category) {
        super(category.getCategoryId(), category.getCategoryName());
    }
    /**
     * @return a String text containing a category's categoryName.
     */
    @Override
    public String toString() {
        return getCategoryName();
    }
}
