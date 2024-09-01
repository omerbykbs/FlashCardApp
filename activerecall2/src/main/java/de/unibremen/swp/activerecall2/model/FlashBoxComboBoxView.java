package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a Flash Box Combo box view in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashBoxComboBoxView extends  FlashBox {

    /**
     * Let Flashbox display in a combo box.
     * @param flashBox a flashBox to put in the flashBox combo box.
     */
    public FlashBoxComboBoxView(FlashBox flashBox) {
        super(flashBox.getFlashBoxId(), flashBox.getFlashBoxName());
    }

    /**
     * @return a String text containing a Flashboxes name.
     */
    @Override
    public String toString() {
        return String.format("%6d | %-30s", getFlashBoxId(), getFlashBoxName());
    }
}
