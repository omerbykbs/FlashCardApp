package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a learning in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class Learning {

    /** Unique ID number for a learning */
    private int learningId;

    /** Title of a learning */
    private String title;

    /** Unique ID number for a flashbox */
    private int flashBoxId;

    /** The date of learning creation */
    private String creationDate;

    /** The date of learning start */
    private String startDate;

    /** The date of learning completion */
    private String completionDate;

    /** The status of the learning */
    private String status;

    /** ID number of learning resume */
    private int resumeLearningDetailId;
    private FlashBox flashBox;

    /**
     * The constructor of this class
     * @param title title is the title of the learning
     * @param flashBoxId flashBoxId is the ID of the flashbox
     * @param creationDate creationDate is the date of learning creation
     * @param startDate startDate is the date of learning start
     * @param completionDate completionDate is the date of learning completion
     */
    public Learning(String title, int flashBoxId, String creationDate, String startDate, String completionDate) {
        this.title = title;
        this.flashBoxId = flashBoxId;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.completionDate = completionDate;
    }

    /**
     * The constructor of this class
     * @param learningId learningId is the ID of the learning
     * @param title title is the title of the learning
     * @param flashBoxId flashBoxId is the ID of the flashbox
     * @param creationDate creationDate is the date of learning creation
     * @param startDate startDate is the date of learning start
     * @param completionDate completionDate is the date of learning completion
     */
    public Learning(int learningId, String title, int flashBoxId, String creationDate, String startDate, String completionDate) {
        this.learningId = learningId;
        this.title = title;
        this.flashBoxId = flashBoxId;
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.completionDate = completionDate;
    }

    /**
     * The constructor of this class
     * @param title title is the title of the learning
     * @param creationDate creationDate is the date of learning creation
     * @param startDate startDate is the date of learning start
     * @param completionDate completionDate is the date of learning completion
     * @param flashBox flashBox is the flashbox
     */
    public Learning(String title, String creationDate, String startDate, String completionDate, FlashBox flashBox) {
        this.title = title;
        this.flashBoxId = flashBox.getFlashBoxId();
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.completionDate = completionDate;
        this.flashBox = flashBox;
    }

    /**
     * The constructor of this class
     * @param learningId learningId is the ID of the learning
     * @param title title is the title of the learning
     * @param creationDate creationDate is the date of learning creation
     * @param startDate startDate is the date of learning start
     * @param completionDate completionDate is the date of learning completion
     * @param flashBox flashBox is the flashbox
     */
    public Learning(int learningId, String title,  String creationDate, String startDate, String completionDate, FlashBox flashBox) {
        this.learningId = learningId;
        this.title = title;
        this.flashBoxId = flashBox.getFlashBoxId();
        this.creationDate = creationDate;
        this.startDate = startDate;
        this.completionDate = completionDate;
        this.flashBox = flashBox;
    }

    /**
     * It gets the learning ID
     * @return learningId
     */
    public int getLearningId() {
        return learningId;
    }

    public void setLearningId(int learningId) {
        this.learningId = learningId;
    }

    /**
     * It gets the title of learning
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * It sets the learning title
     * @param title title is the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * It gets the flashbox ID
     * @return flashboxId
     */
    public int getFlashBoxId() {
        return flashBoxId;
    }

    public void setFlashBoxId(int flashBoxId) {
        this.flashBoxId = flashBoxId;
    }

    /**
     * It gets the creation date of the learning
     * @return creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * It gets the start date of the learning
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * It sets the start date of the learning
     * @param startDate startDate is the start date of the learning
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * It gets the completion date of the learning
     * @return completionDate
     */
    public String getCompletionDate() {
        return completionDate;
    }

    /**
     * It sets the completion date of the learning
     * @param completionDate completionDate is the completion date of the learning
     */
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * It gets the flashbox
     * @return flashbox
     */
    public FlashBox getFlashBox() {
        return flashBox;
    }

    public void setFlashBox(FlashBox flashBox) {
        this.flashBox = flashBox;
    }

    /**
     * It gets the status of the learning
     * @return status
     */
    public String getStatus() {
        if (completionDate != null && !completionDate.trim().isEmpty()) {
            return "completed";
        } else if (startDate != null && !startDate.trim().isEmpty()) {
            return "started";
        } else {
            return  "prepared";
        }
    }

    /**
     * It gets the resume learning detail ID
     * @return resumeLearningDetailId
     */
    public int getResumeLearningDetailId() {
        return resumeLearningDetailId;
    }

    /**
     * It sets the resume learning detail ID
     * @param resumeLearningDetailId resumeLearningDetailId is the resume learning detail ID
     */
    public void setResumeLearningDetailId(int resumeLearningDetailId) {
        this.resumeLearningDetailId = resumeLearningDetailId;
    }

    /**
     * @return a String Text containing learningId, title, flashBoxId, creationDate, startDate
     * and completionDate for a learning
     */
    @Override
    public String toString() {
        return "Learning{" +
                "learningId=" + learningId +
                ", title='" + title + '\'' +
                ", flashBoxId=" + flashBoxId +
                ", creationDate='" + creationDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", completionDate='" + completionDate + '\'' +
                '}';
    }
}

