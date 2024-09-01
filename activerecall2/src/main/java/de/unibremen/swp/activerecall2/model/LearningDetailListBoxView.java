package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a learning detail list box in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class LearningDetailListBoxView extends  LearningDetail {

    /**
     * The constructor of this class
     * @param learningDetail learningDetail is the learning detail
     */
    public LearningDetailListBoxView(LearningDetail learningDetail) {
        super(learningDetail.getLearningDetailId(), learningDetail.getAnswer(), learningDetail.getLevelId(),
                learningDetail.getLearning(), learningDetail.getFlashCard());
    }

    /**
     * @return a String Text containing learningDetailId, flashCardId, categoryName and question
     * for a learning detail list box
     */
    @Override
    public String toString() {
        return String.format("Id:%6s | %6d | %-30s | %-30s ",getLearningDetailId(), getFlashCardId(), getFlashCard().getCategory().getCategoryName(), getFlashCard().getQuestion());
    }
}
