package de.unibremen.swp.activerecall2.model;

import java.util.List;

/**
 * This class provides the structure of a category in the data model.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 */
public class Category {

    /** Unique ID number for a category. */
    private int categoryId;
    /** Name of a category. */
    private String categoryName;
    /** List of flashcards that a category contains. */
    private List<FlashCard> flashCards;
    /**
     * Create a category
     */
    public Category() {
    }
    /**
     * Create a category with
     * @param categoryName a category name
     */
    public Category( String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Create a category with
     * @param categoryId a category ID that is unique for any flashcard
     * @param categoryName a category name
     */
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    /**
     * @return categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    /**
     * @return categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    /**
     * @return a list of containing flashCards
     */
    public List<FlashCard> getFlashCards() {
        return flashCards;
    }
    /**
     * @param flashCards list of containing flashcards to set
     */
    public void setFlashCards(List<FlashCard> flashCards) {
        this.flashCards = flashCards;
    }

    /**
     * @return a String Text containing categoryId and categoryName for a category
     */
    @Override
    public String toString() {
        return "Category{" +
                "CategoryId=" + categoryId +
                ", CategoryName='" + categoryName + '\'' +
                '}';
    }
}

