package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a flashcard list box view in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashCardListBoxView extends  FlashCard {
    /**
     * Let flashcard display in a list box.
     * @param flashCard a flashcard to put in the flashcard list box.
     */

    public FlashCardListBoxView(FlashCard flashCard) {
        super(flashCard.getFlashCardId(), flashCard.getQuestion(), flashCard.getAnswer(), flashCard.getKeywords(), flashCard.getCategory());
    }

    /**
     * @return a String text containing a flashcard's flashCardId, categoryName and question.
     */
    @Override
    public String toString() {
        return String.format("%6d | %-30s | %s", getFlashCardId(), getCategory().getCategoryName(), getQuestion());
    }
}
