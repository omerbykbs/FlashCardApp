package de.unibremen.swp.activerecall2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the structure of a flashcard box in the data model.
 *
 * @author Ömer Faruk Büyükbas, Oguzhan Aydin, Kamil Uyanik
 *
 * Note: We have changed flashBox as card box in gui so all terms with flashbox
 * should be consiedered as card box
 */
public class FlashBox {
    /** Unique ID for a flashcard box. */
    private int flashBoxId;
    /** Name of a flashcard box. */
    private String flashBoxName;
    /** List of categories inside the flashcard box */
    private List<FlashBoxDetail> flashBoxDetailList;

    /**
     * Create a flashcard box with
     * @param flashBoxId a flashcard box ID that is unique for any flashcard box
     * @param flashBoxName a flashcard box name
     */
    public FlashBox(int flashBoxId, String flashBoxName) {
        this.flashBoxId = flashBoxId;
        this.flashBoxName = flashBoxName;
        flashBoxDetailList= new ArrayList<>();
    }
    /**
     * Create a flashcard box with
     * @param flashBoxName a flashcard box name
     */
    public FlashBox(String flashBoxName) {
        this.flashBoxName = flashBoxName;
        flashBoxDetailList= new ArrayList<>();
    }
    /**
     * Create a flashcard box with
     * @param flashBoxId a flashcard box ID that is unique for any flashcard box
     * @param flashBoxName a flashcard box name
     * @param flashBoxDetailList a list for detail of flashcard box that contains categories in that box
     */
    public FlashBox(int flashBoxId, String flashBoxName, List<FlashBoxDetail> flashBoxDetailList) {
        this.flashBoxId = flashBoxId;
        this.flashBoxName = flashBoxName;
        this.flashBoxDetailList = flashBoxDetailList;
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
     * @return flashBoxName
     */
    public String getFlashBoxName() {
        return flashBoxName;
    }
    /**
     * @param flashBoxName flashBoxName to set
     */
    public void setFlashBoxName(String flashBoxName) {
        this.flashBoxName = flashBoxName;
    }
    /**
     * @return flashBoxDetailList
     */
    public List<FlashBoxDetail> getFlashBoxDetailList() {
        return flashBoxDetailList;
    }
    /**
     * @param flashBoxDetailList flashBoxDetailList to set
     */
    public void setFlashBoxDetailList(List<FlashBoxDetail> flashBoxDetailList) {
        this.flashBoxDetailList = flashBoxDetailList;
    }

    /**
     * @return a String Text containing flashBoxId and flashBoxName for a flashcard box
     */
    @Override
    public String toString() {
        return "FlashBox{" +
                "flashBoxId=" + flashBoxId +
                ", flashBoxName='" + flashBoxName + '\'' +
                '}';
    }
}
