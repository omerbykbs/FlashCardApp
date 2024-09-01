package de.unibremen.swp.activerecall2.model;


import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the structure of a flashcard in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashCard {

    /** Unique ID number for a flashcard. */
    private int flashCardId;

    /** Unique ID number for a category. */
    private int categoryId;

    /** Question (front page) of a flashcard. */
    private String question;

    /** Answer (back page) of a flashcard. */
    private String answer;

    /** Containing keywords of a flashcard. */
    private String keywords;

    /** Category of a flashcard. */
    private Category category;

    /** List of links that a flashcard contains. */
    private List<FlashCardLink> links;

    /**
     * Create a list of links for a flashcard
     */
    public FlashCard() {
        links= new ArrayList<>();
    }

    /**
     * Create a flashcard with
     * @param question a question to learn
     * @param answer an answer of the question to check
     * @param categoryId a category ID to assign the flashcard to a category
     */
    public FlashCard(String question, String answer, int categoryId) {
        this.question = question;
        this.answer = answer;
        this.category = null;
        this.categoryId= categoryId;
    }
    /**
     * Create a flashcard with
     * @param question a question to learn
     * @param answer an answer of the question to check
     * @param keywords added keywords to flashcard to help keeping in mind
     * @param categoryId a category ID to assign the flashcard to a category
     */

    public FlashCard(String question, String answer, String keywords, int categoryId) {
        this.question = question;
        this.answer = answer;
        this.keywords= keywords;
        this.category = null;
        this.categoryId= categoryId;
    }
    /**
     * Create a flashcard with
     * @param question a question to learn
     * @param answer an answer of the question to check
     * @param category a category to assign the flashcard
     */

    public FlashCard(String question, String answer, Category category) {
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.categoryId= category.getCategoryId();
    }

    /**
     * Create a flashcard with
     * @param question a question to learn
     * @param answer an answer of the question to check
     * @param keywords added keywords to flashcard to help keeping in mind
     * @param category a category to assign the flashcard
     */
    public FlashCard(String question, String answer, String keywords,  Category category) {
        this.question = question;
        this.answer = answer;
        this.keywords= keywords;
        this.category = category;
        this.categoryId= category.getCategoryId();
    }
    /**
     * Create a flashcard with
     * @param flashCardId a flashcard ID that is unique for any flashcard
     * @param question a question to learn
     * @param answer an answer of the question to check
     * @param categoryId a category ID to assign the flashcard to a category
     */

    public FlashCard(int flashCardId, String question, String answer, int categoryId) {
        this.flashCardId = flashCardId;
        this.question = question;
        this.answer = answer;
        this.category = null;
        this.categoryId= categoryId;
    }

    /**
     * Create a flashcard with
     * @param flashCardId a flashcard ID that is unique for any flashcard
     * @param question a question to learn
     * @param answer an answer of the question to check
     * @param keywords added keywords to flashcard to help keeping in mind
     * @param categoryId a category ID to assign the flashcard to a category
     */
    public FlashCard(int flashCardId, String question, String answer, String keywords, int categoryId) {
        this.flashCardId = flashCardId;
        this.question = question;
        this.answer = answer;
        this.keywords= keywords;
        this.category = null;
        this.categoryId= categoryId;
    }
    /**
     * Create a flashcard with
     * @param flashCardId a flashcard ID that is unique for any flashcard
     * @param question a question to learn
     * @param answer an answer of the question to check
     * @param category a category to assign the flashcard
     */

    public FlashCard(int flashCardId, String question, String answer, Category category) {
        this.flashCardId = flashCardId;
        this.question = question;
        this.answer = answer;
        this.category = category;
        this.categoryId= category.getCategoryId();
    }

    /**
     * Create a flashcard with
     * @param flashCardId a flashcard ID that is unique for any flashcard
     * @param question a question to learn
     * @param answer an answer of the question to check
     * @param keywords added keywords to flashcard to help keeping in mind
     * @param category a category to assign the flashcard
     */
    public FlashCard(int flashCardId, String question, String answer, String keywords, Category category) {
        this.flashCardId = flashCardId;
        this.question = question;
        this.answer = answer;
        this.keywords= keywords;
        this.category = category;
        this.categoryId= category.getCategoryId();
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
     * @return categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category category to set
     */
    public void setCategory(Category category) {
        this.category = category;
        this.categoryId= category.getCategoryId();
    }

    /**
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * @param keywords keywords to set
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * @return a String Text containing flashCardId, categoryId, question, answer and keywords for a flashcard
     */
    @Override
    public String toString() {
        return "FlashCard{" +
                "flashCardId=" + flashCardId +
                ", categoryId='" + categoryId + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}

