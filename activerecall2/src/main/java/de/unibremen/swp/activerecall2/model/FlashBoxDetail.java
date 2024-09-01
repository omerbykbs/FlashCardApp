package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of flashcard box's detail in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashBoxDetail {
    /** Unique ID for a flashcard box's detail */
    private int flashBoxDetailId;
    /** Unique ID for a flashcard box */
    private int flashBoxId;
    /** Unique ID for a category */
    private int categoryId;
    /** A flashcard box */
    private FlashBox flashBox;
    /** A category */
    private Category category;

    /**
     * Create a flashcard box detail with
     * @param flashBoxId a flashcard box ID that is unique for any flashcard box
     * @param categoryId a category ID that is unique for any category
     */
    public FlashBoxDetail(int flashBoxId, int categoryId) {
        this.flashBoxId = flashBoxId;
        this.categoryId = categoryId;
    }
    /**
     * Create a flashcard box detail with
     * @param flashBoxDetailId  a flashcard box detail ID that is unique for any flashcard box detail
     * @param flashBoxId a flashcard box ID that is unique for any flashcard box
     * @param categoryId a category ID that is unique for any category
     */
    public FlashBoxDetail(int flashBoxDetailId, int flashBoxId, int categoryId) {
        this.flashBoxDetailId = flashBoxDetailId;
        this.flashBoxId = flashBoxId;
        this.categoryId = categoryId;
    }
    /**
     * Create a flashcard box detail with
     * @param flashBoxDetailId a flashcard box detail ID that is unique for any flashcard box detail
     * @param flashBox a flashcard box
     * @param category a category
     */

    public FlashBoxDetail(int flashBoxDetailId, FlashBox flashBox, Category category) {
        this.flashBoxDetailId = flashBoxDetailId;
        this.flashBox = flashBox;
        this.category = category;
        this.flashBoxId= flashBox.getFlashBoxId();
        this.categoryId= category.getCategoryId();
    }
    /**
     * @return flashBoxDetailId
     */
    public int getFlashBoxDetailId() {
        return flashBoxDetailId;
    }

    /**
     * @param flashBoxDetailId flashBoxDetailId to set
     */
    public void setFlashBoxDetailId(int flashBoxDetailId) {
        this.flashBoxDetailId = flashBoxDetailId;
    }

    /**
     * @return flashBoxId
     */
    public int getFlashBoxId() {
        return flashBoxId;
    }

    /**
     * @param flashBoxId flashBoxId to set
     */
    public void setFlashBoxId(int flashBoxId) {
        this.flashBoxId = flashBoxId;
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
     * @return flashBox
     */
    public FlashBox getFlashBox() {
        return flashBox;
    }

    /**
     * @param flashBox flashBox to set
     */
    public void setFlashBox(FlashBox flashBox) {
        this.flashBox = flashBox;
    }

    /**
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return a String Text containing flashBoxDetailId, flashBoxId and categoryId for a flashcard box's detail
     */
    @Override
    public String toString() {

        return "FlashBoxDetail{" +
                "flashBoxDetailId=" + flashBoxDetailId +
                ", flashBoxId=" + flashBoxId +
                ", categoryId=" + categoryId +
                '}';
    }
}

