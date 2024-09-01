package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a learning list box view in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class LearningListBoxView extends Learning{

    /**
     * The constructor of this class
     * @param learning learning is the learning
     */
    public LearningListBoxView(Learning learning) {
        super(learning.getLearningId(), learning.getTitle(), learning.getCreationDate(), learning.getStartDate(), learning.getCompletionDate(), learning.getFlashBox());
        setResumeLearningDetailId(learning.getResumeLearningDetailId());
    }

    /**
     * @return a String Text containing learningId, title, flashBoxName and status for a learning list box view
     */
    @Override
    public String toString() {
        String titlePart = getTitle().length() <50 ? getTitle() : getTitle().substring(0,50)+"...";
        String flashBoxNamePart= getFlashBox().getFlashBoxName().length() <50? getFlashBox().getFlashBoxName() : getFlashBox().getFlashBoxName().substring(0,50)+"...";
        return String.format("%6d | %-20s | %-30s | Status: %6s", getLearningId(), titlePart, flashBoxNamePart, getStatus() );
    }
}