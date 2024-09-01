package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a flashcard box list box view in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashBoxListBoxView extends  FlashBox{
    /**
     * Let flashcard box/s display in a list box.
     * @param flashBox a flashcard box to put in the flashcard box list.
     */
    public FlashBoxListBoxView(FlashBox flashBox) {
        super(flashBox.getFlashBoxId(), flashBox.getFlashBoxName());
    }
    /**
     * @return a String text containing a flashcard box's flashBoxId and flashBoxName.
     */
    @Override
    public String toString() {
        return String.format("%6d | %-30s", getFlashBoxId(), getFlashBoxName());
    }
}
