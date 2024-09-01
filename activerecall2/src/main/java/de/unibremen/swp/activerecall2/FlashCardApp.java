package de.unibremen.swp.activerecall2;
import java.sql.SQLException;
import de.unibremen.swp.activerecall2.controller.*;
import de.unibremen.swp.activerecall2.logic.*;
import de.unibremen.swp.activerecall2.persistence.DbService;

/**
 * Main class for the flash card learning program
 * @author Ömer Faruk Büyükbas
 */
public class FlashCardApp {

    /** Name of the application*/
    public static final String APP_NAME = "ActiveRecall2";
    /** Version of the application*/
    public static final String VERSION = "1.0";

    /**
     * Main method of the class it creates required objects and calls relevant methods to run the program
     * @param args the command-line parameters (unused)
     */
    public static void main(final String[] args) {
        try {
            final DbService db= new DbService();
            db.insertSampleData();
            
            final CategoriesLogic categoriesLogic= new CategoriesLogic(db);
            final FlashCardsLogic flashCardsLogic = new FlashCardsLogic(db);
            final GlossaryLogic glossaryLogic= new GlossaryLogic(db);
            final FlashBoxesLogic flashBoxesLogic= new FlashBoxesLogic(db);
            final LearningsLogic learningsLogic = new LearningsLogic(db);
            final LearningExercisesSelectionLogic learningExercisesSelectionLogic = new LearningExercisesSelectionLogic(db);
            final LearningExercisesLogic learningExercisesLogic = new LearningExercisesLogic(db);
            final MainController mainController = new MainController();
            final CategoriesController categoriesController = new CategoriesController(categoriesLogic);

            final FlashCardsController flashCardsController= new FlashCardsController(flashCardsLogic);
            flashCardsController.setCategoriesLogic(categoriesLogic);

            final GlossaryController glossaryController = new GlossaryController(glossaryLogic);
            glossaryController.setCategoriesLogic(categoriesLogic);

            final FlashBoxesController flashBoxesController= new FlashBoxesController(flashBoxesLogic);
            flashBoxesController.setCategoriesLogic(categoriesLogic);
            flashBoxesController.setFlashCardsLogic(flashCardsLogic);

            final LearningController learningController = new LearningController(learningsLogic);
            learningController.setFlashCardsLogic(flashCardsLogic);
            learningController.setFlashBoxesLogic(flashBoxesLogic);

            final  LearningExercisesController learningExercisesController = new LearningExercisesController(learningExercisesLogic);
            learningExercisesController.setFlashCardsLogic(flashCardsLogic);
            learningExercisesController.setLearningsLogic(learningsLogic);

            final LearningExercisesSelectionController learningExercisesSelectionController = new LearningExercisesSelectionController(learningExercisesSelectionLogic);
            learningExercisesSelectionController.setLearningExercisesController(learningExercisesController);
            learningExercisesSelectionController.setLearningsLogic(learningsLogic);

            mainController.setCategoriesController(categoriesController);
            mainController.setFlashCardsController(flashCardsController);
            mainController.setGlossaryController(glossaryController);
            mainController.setFlashBoxesController(flashBoxesController);
            mainController.setLearningController(learningController);
            mainController.setLearningExercisesSelectionController(learningExercisesSelectionController);
            mainController.startApplication();
        }
        catch (SQLException e) {
            System.out.println("Db initialization error");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (final Exception e) {
            System.err.printf("Something went wrong: %s", e.getMessage());
            e.printStackTrace();
        }
    }
}

