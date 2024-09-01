package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a flashcard link list box view in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashCardLinkListBoxView extends FlashCardLink {

    /**
     * Let flashcard link display in a list box.
     * @param link a link to put in the flashcard link list box.
     */
    public FlashCardLinkListBoxView(FlashCardLink link) {
        super(link);
    }

    /**
     * @return a String text containing link term, flashCardId and the question of the flashcard from which a link will be created.
     */
    @Override
    public String toString() {
        return String.format("%-20s | %6s | %-30s",getTerm(),getTargetFlashCardId(), getTargetFlashCard().getQuestion());
    }
}
