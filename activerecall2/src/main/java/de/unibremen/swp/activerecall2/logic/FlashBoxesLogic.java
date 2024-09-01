package de.unibremen.swp.activerecall2.logic;

import de.unibremen.swp.activerecall2.model.FlashBox;
import de.unibremen.swp.activerecall2.model.FlashBoxDetail;
import de.unibremen.swp.activerecall2.persistence.DbService;

import java.sql.SQLException;
import java.util.List;

/**
 * This class checks the logic of the matters that are related to flashboxes like add,
 * delete and update.
 *
 * @author Ömer Faruk Büyükbas
 */
public class FlashBoxesLogic {

    private final DbService db;

    /**
     * The constructor of this class
     * @param db db is the database service
     */
    public FlashBoxesLogic(DbService db) {
        this.db= db;
    }

    /**
     *It adds new flashbox
     * @param flashBox flashBox is the flashbox to add
     * @return 1, if flashbox successfully added
     *         0, if flashbox cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addFlashBox(FlashBox flashBox) throws SQLException {
        return db.flashBoxAdd(flashBox);
    }

    /**
     * It updates the given flashbox
     * @param flashBox flashBox is the flashbox to update
     * @return 1, if flashbox successfully updated
     *         0, if flashbox cannot be updated
     * @throws SQLException if there is a problem with SQL connection
     */
    public int updateFlashBox(FlashBox flashBox) throws SQLException {
        return db.flashBoxUpdate(flashBox);
    }

    /**
     * It deletes the give flashbox
     * @param id id is the ID of the flashbox to delete
     * @return 1, if flashbox successfully deleted
     *         0, if flashbox cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteFlashBox(int id) throws SQLException {
        return db.flashBoxDelete(id);
    }

    /**
     * It gets the list of all flashboxes
     * @return the list of all flashboxes
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<FlashBox> getAllFlashBoxes() throws SQLException {
        return db.flashBoxGetAll();
    }

    /**
     * It checks if the given flashbox name already exits
     * @param flashBoxName flashBoxName is the flashbox name to check
     * @return 1, if the given flashbox name already exists
     *         0, if the given flashbox name not exists
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasFlashBox(String flashBoxName) throws SQLException {
        return db.hasFlashBox(flashBoxName);
    }

    /**
     * It gets the list of all details of the given flashbox
     * @param flashBoxId flashBoxId is the ID of the flashbox to get details
     * @return the list of all details if the given flashbox
     * @throws SQLException if there is a problem with SQL connection
     */
    public List<FlashBoxDetail> getAllFlashBoxDetails(int flashBoxId) throws SQLException {
        return db.flashBoxDetailGetAllByFlashBox(flashBoxId);
    }

    /**
     * It gets the details of the given flashbox
     * @param id id is the ID of flashbox to get its details
     * @return flashbox detail, if it is successful
     *         null, if it is not successful
     * @throws SQLException if there is a problem with SQL connection
     */
    public FlashBoxDetail getFlashBoxDetail(int id) throws SQLException {
        return db.flashBoxDetailGet(id);
    }

    /**
     * It adds new flashbox detail
     * @param flashBoxDetail flashBoxDetail is the flashbox detail to add
     * @return 1, if flashbox detail successfully added
     *         0, if flashbox detail cannot be added
     * @throws SQLException if there is a problem with SQL connection
     */
    public int addFlashBoxDetail(FlashBoxDetail flashBoxDetail) throws SQLException {
        return db.flashBoxDetailAdd(flashBoxDetail);
    }

    /**
     * It deletes the detail of the given flashbox
     * @param id id is the ID of the flashbox to delete its detail
     * @return 1, if detail successfully deleted
     *         0, if detail cannot be deleted
     * @throws SQLException if there is a problem with SQL connection
     */
    public int deleteFlashBoxDetail(int id) throws SQLException {
        return db.flashBoxDetailDelete(id);
    }

    /**
     * It checks if the given flashbox has the detail
     * @param flashBoxId flashBoxId is the ID of the flashbox
     * @param categoryId categoriesId is the ID of the category
     * @return true, if the given flashbox has the detail
     *         false, if the given flashbox has not the detail
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean hasFlashBoxDetail(int flashBoxId, int categoryId) throws SQLException {
        return db.hasFlashBoxDetail(flashBoxId, categoryId);
    }

    /**
     * It checks if the given flashbox has related records or not
     * @param id id is the ID of the flashbox to check
     * @return true, if the given flashbox has related records
     *         false, if the given flashbox has not related records
     * @throws SQLException if there is a problem with SQL connection
     */
    public boolean flashBoxHasRelatedRecords(int id) throws SQLException {
        return db.flashBoxHasRelatedRecords(id);
    }

}

