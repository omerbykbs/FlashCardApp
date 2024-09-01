package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a learning detail in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class LearningDetail {

    /** Unique ID number for a learning detail */
    private int learningDetailId;

    /** Unique ID number for a learning */
    private int learningId;

    /** Unique ID number for a flashcard */
    private int flashCardId;

    /** The answer */
    private String answer;

    /** Unique ID number for a level */
    private int levelId;

    private Learning learning;
    private FlashCard flashCard;
    private Level level;

    /**
     * The constructor of this class
     * @param learningId learningId is the ID of the learning
     * @param flashCardId flashCardId is the ID of the flashcard
     * @param answer answer is the answer
     * @param level level is the level
     */
    public LearningDetail(int learningId, int flashCardId, String answer, int level) {
        this.learningDetailId = learningDetailId;
        this.learningId = learningId;
        this.flashCardId = flashCardId;
        this.answer = answer;
        this.levelId = level;
    }

    /**
     * The constructor of this class
     * @param learningDetailId learningDetailId is the ID of learning detail
     * @param learningId learningId is the ID of the learning
     * @param flashCardId flashCardId is the ID of the flashcard
     * @param answer answer is the answer
     * @param level level is the level
     */
    public LearningDetail(int learningDetailId, int learningId, int flashCardId, String answer, int level) {
        this.learningDetailId = learningDetailId;
        this.learningId = learningId;
        this.flashCardId = flashCardId;
        this.answer = answer;
        this.levelId = level;
    }

    /**
     * The constructor of this class
     * @param learningDetailId learningDetailId is the ID of learning detail
     * @param answer answer is the answer
     * @param level level is the level
     * @param learning learning is the learning
     * @param flashCard flashCard is the flashcard
     */
    public LearningDetail(int learningDetailId, String answer, int level, Learning learning, FlashCard flashCard) {
        this.learningDetailId = learningDetailId;
        this.learningId= learning.getLearningId();
        this.flashCardId = flashCard.getFlashCardId();
        this.answer = answer;
        this.levelId = level;
        this.learning = learning;
        this.flashCard = flashCard;
    }

    /**
     * It gets the ID of learning detail
     * @return learningDetailId
     */
    public int getLearningDetailId() {
        return learningDetailId;
    }

    public void setLearningDetailId(int learningDetailId) {
        this.learningDetailId = learningDetailId;
    }

    /**
     * It gets the ID of the learning
     * @return learningId
     */
    public int getLearningId() {
        return learningId;
    }

    public void setLearningId(int learningId) {
        this.learningId = learningId;
    }

    /**
     * It gets the ID of the flashcard
     * @return flashCardId
     */
    public int getFlashCardId() {
        return flashCardId;
    }

    /**
     * It sets the ID of the flashcard
     * @param flashCardId flashCardId is the ID of the flashcard to set
     */
    public void setFlashCardId(int flashCardId) {
        this.flashCardId = flashCardId;
    }

    /**
     * It gets the answer
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * It gets the ID of the level
     * @return levelId
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * It sets the ID of the level
     * @param levelId levelId is the ID of the level to set
     */
    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    /**
     * It gets the level
     * @return level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * It sets the level
     * @param level level is the level to set
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * It gets the learning
     * @return learning
     */
    public Learning getLearning() {
        return learning;
    }

    /**
     * It sets the learning
     * @param learning learning is the learning to set
     */
    public void setLearning(Learning learning) {
        this.learning = learning;
    }

    /**
     * It gets the flashcard
     * @return flashcard
     */
    public FlashCard getFlashCard() {
        return flashCard;
    }

    /**
     * It sets the flashcard
     * @param flashCard flashCard is the flashcard to set
     */
    public void setFlashCard(FlashCard flashCard) {
        this.flashCard = flashCard;
    }

    /**
     * @return a String Text containing learningDetailId, learningId, flashCardId, answer
     * and level for a learning detail
     */
    @Override
    public String toString() {
        return "LearningDetail{" +
                "learningDetailId=" + learningDetailId +
                ", learningId=" + learningId +
                ", flashCardId=" + flashCardId +
                ", answer='" + answer + '\'' +
                ", level=" + levelId +
                '}';
    }
}

