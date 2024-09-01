package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a flashcard box's detail list box view in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashBoxDetailListBoxView extends  FlashBoxDetail {
    /**
     * Let flashcard box's detail display in a list box.
     * @param flashBoxDetail a flashcard box's detail to put in the flashcard box's detail list box.
     */
    public FlashBoxDetailListBoxView(FlashBoxDetail flashBoxDetail) {
        super(flashBoxDetail.getFlashBoxDetailId(), flashBoxDetail.getFlashBox(), flashBoxDetail.getCategory() );
    }
    /**
     * @return a String text containing categoryName for a flashcard box as detail.
     */
    @Override
    public String toString() {
        return String.format("%-30s", getCategory().getCategoryName());
    }
}
