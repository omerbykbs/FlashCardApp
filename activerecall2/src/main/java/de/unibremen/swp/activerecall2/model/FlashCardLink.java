package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a flashcard link in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashCardLink {
    /** Unique ID number for a flashcard link. */
    private int flashCardLinkId;

    /** Unique ID number for a flashcard. */
    private int flashCardId;

    /** Link term for a link */
    private String term;

    /** Unique ID number of a flashcard that is target card of link. */
    private int targetFlashCardId;

    /** A flashcard from which a link will be created. */
    private FlashCard flashCard;

    /** A flashcard that is the target of the link. */
    private FlashCard targetFlashCard;

    /**
     * Create a link with
     * @param linkId a link ID that identifies every link unique
     * @param flashCardId a flashcard ID that is unique for any flashcard
     * @param term a term for link
     * @param targetFlashCardId a target flashcard ID that is unique for any flashcard
     */
    public FlashCardLink(int linkId, int flashCardId, String term, int targetFlashCardId) {
        this.flashCardLinkId = linkId;
        this.term = term;
        this.flashCardId= flashCardId;
        this.targetFlashCardId = targetFlashCardId;
    }

    /**
     * Create a link with
     * @param flashCardId a flashcard ID that is unique for any flashcard
     * @param term a term for link
     * @param targetFlashCardId a target flashcard ID that is unique for any flashcard
     */
    public FlashCardLink(int flashCardId, String term, int targetFlashCardId) {
        this.term = term;
        this.flashCardId= flashCardId;
        this.targetFlashCardId = targetFlashCardId;
    }

    /**
     * Create a link with
     * @param linkId a link ID that identifies every link unique
     * @param flashCard a flashcard from which a link will be created
     * @param term a term for link
     * @param targetFlashCard a flashcard that is the target of the link
     */
    public FlashCardLink(int linkId, FlashCard flashCard, String term, FlashCard targetFlashCard) {
        this.flashCardLinkId = linkId;
        this.flashCardId= flashCard.getFlashCardId();
        this.term = term;
        this.targetFlashCardId = targetFlashCard.getFlashCardId();
        this.flashCard= flashCard;
        this.targetFlashCard = targetFlashCard;
    }

    /**
     * Create a link with
     * @param flashCard a flashcard from which a link will be created
     * @param term a term for link
     * @param targetFlashCard a flashcard that is the target of the link
     */
    public FlashCardLink(FlashCard flashCard, String term, FlashCard targetFlashCard) {
        this.flashCardId= flashCard.getFlashCardId();
        this.term = term;
        this.targetFlashCardId = targetFlashCard.getFlashCardId();
        this.flashCard= flashCard;
        this.targetFlashCard = targetFlashCard;

    }

    /**
     * Create a link with
     * @param flashCardLink a flashcard link
     */
    public FlashCardLink(FlashCardLink flashCardLink) {
        this.flashCardLinkId = flashCardLink.getFlashCardLinkId();
        this.flashCardId= flashCardLink.getFlashCardId();
        this.term = flashCardLink.getTerm();
        this.targetFlashCardId = flashCardLink.getTargetFlashCardId();
        this.flashCard= flashCardLink.getFlashCard();
        this.targetFlashCard = flashCardLink.getTargetFlashCard();
    }

    /**
     * @return flashCardLinkId
     */
    public int getFlashCardLinkId() {
        return flashCardLinkId;
    }

    /**
     * @param flashCardLinkId flashCardLinkId to set
     */
    public void setFlashCardLinkId(int flashCardLinkId) {
        this.flashCardLinkId = flashCardLinkId;
    }

    /**
     * @return flashCardId
     */
    public int getFlashCardId() {
        return flashCardId;
    }

    /**
     * @param flashCardId flashCardId to set
     */
    public void setFlashCardId(int flashCardId) {
        this.flashCardId = flashCardId;
    }

    /**
     * @return term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @param term term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return targetFlashCardId
     */
    public int getTargetFlashCardId() {
        return targetFlashCardId;
    }

    /**
     * @param targetFlashCardId targetFlashCardId to set
     */
    public void setTargetFlashCardId(int targetFlashCardId) {
        this.targetFlashCardId = targetFlashCardId;
    }

    /**
     * @return flashCard
     */
    public FlashCard getFlashCard() {
        return flashCard;
    }

    /**
     * @param flashCard flashCard to set
     */
    public void setFlashCard(FlashCard flashCard) {
        this.flashCard = flashCard;
    }

    /**
     * @return targetFlashCard
     */
    public FlashCard getTargetFlashCard() {
        return targetFlashCard;
    }

    /**
     * @param targetFlashCard targetFlashCard to set
     */
    public void setTargetFlashCard(FlashCard targetFlashCard) {
        this.targetFlashCard = targetFlashCard;
    }


}

