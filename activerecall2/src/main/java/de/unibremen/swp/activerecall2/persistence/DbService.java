package de.unibremen.swp.activerecall2.persistence;
import de.unibremen.swp.activerecall2.model.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class serves for all methods that reaches the database to get or write data (adding, updating, deleting, saving, loading)
 *
 * @author Ömer
 */
public class DbService {

    /** Url for database connection */
    private String url = "jdbc:sqlite:sample.db";
    /** Connection for database */
    private Connection con;
    /** Levels for learning */
    private static List<Level> levels;

    /**
     * Constructor of database service that contains database connection, levels for learning and tables
     * @throws SQLException if there is a problem at connecting to database
     * @throws ClassNotFoundException if the using class not found
     */
    public DbService() throws SQLException, ClassNotFoundException {
        connect();
        levels= new ArrayList<>();
        createLevels();
        createTables();
    }

    /**
     * Creates a database connection
     * @return con is the connection for constructor of database
     * @throws SQLException if there is a problem at connecting to database
     * @throws ClassNotFoundException if the using class not found
     */
    private Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Properties properties = new Properties();
        properties.setProperty("PRAGMA foreign_keys", "ON");
        con = DriverManager.getConnection(url, properties);
        Statement statement = con.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        System.out.println("Connection ok");
        return con;
    }

    //  -- CATEGORY METHODS --

    /**
     * Gets all categories in the database
     * @return a List of categories
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<Category> categoryGetAll() throws SQLException {
        List<Category> list= new ArrayList<>();
        Category category;
        int categoryId;
        String categoryName;
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select categoryId, categoryName from categories order by categoryName COLLATE NOCASE ");
        while(rs.next()) {
            categoryId= rs.getInt("categoryId");
            categoryName = rs.getString("categoryName");
            category= new Category(categoryId,categoryName);
            list.add(category);
        }
        return  list;
    }

    /**
     * Gets all categories with their id in the database
     * @return a Map <Integer,Category> that contains categoryIds and categories
     * @throws SQLException if there is a problem at connecting to database
     */
    public Map<Integer, Category> categoryGetAllMap() throws SQLException {
        Map<Integer, Category> map= new HashMap<>();
        Category category;
        int categoryId;
        String categoryName;
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select categoryId, categoryName from categories order by categoryName COLLATE NOCASE ");
        while(rs.next()) {
            categoryId= rs.getInt("categoryId");
            categoryName = rs.getString("categoryName");
            category= new Category(categoryId,categoryName);
            map.put(categoryId,category);
        }
        return  map;
    }

    /**
     * Gets a category for a categoryId
     * @param id id is an ID number of a category that is unique for any category
     * @return a Category if there is a category with searched categoryId in the database
     *         null if there is no category with searched categoryId in the database
     * @throws SQLException if there is a problem at connecting to database
     */
    public Category categoryGet(int id) throws SQLException {
        Category category;
        int categoryId;
        String categoryName;
        PreparedStatement statement = con.prepareStatement("select categoryId, categoryName from categories where categoryId=?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            categoryId= rs.getInt("categoryId");
            categoryName = rs.getString("categoryName");
            category= new Category(categoryId,categoryName);
            return  category;
        } else {
            return null;
        }
    }

    /**
     * Checks if there is a category (with a categoryName) in the database
     * @param categoryName categoryName is the name of the category that is searched in the database
     * @return true if there is a category with categoryName in the database
     *         false if there is no category with categoryName in the database
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean hasCategory(String categoryName) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select categoryName from categories where categoryName=?");
        statement.setString(1,categoryName);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    /**
     * Adds a category in the database
     * @param category category is a category to add
     * @return 1 if the category is added
     *         0 if the category is not added
     * @throws SQLException if there is a problem at connecting to database
     */
    public int categoryAdd(Category category) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into categories (categoryName) values(?)");
        statement.setString(1,category.getCategoryName());
        return statement.executeUpdate();
    }

    /**
     * Updates a category in the database
     * @param category category is a category to update
     * @return 1 if the category is updated
     *         0 if the category is not updated
     * @throws SQLException if there is a problem at connecting to database
     */
    public int categoryUpdate(Category category) throws SQLException {
        PreparedStatement statement = con.prepareStatement("update categories set categoryName=? where categoryId=?");
        statement.setString(1,category.getCategoryName());
        statement.setInt(2,category.getCategoryId());
        return statement.executeUpdate();
    }

    /**
     * Deletes a category from the database
     * @param id id is a categoryId of a category to delete
     * @return 1 if the category is deleted
     *         0 if the category is not deleted
     * @throws SQLException if there is a problem at connecting to database
     */
    public int categoryDelete(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from categories where categoryId=?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    /**
     * Checks if the category has any related records (is there any flashcard with this category or is added into any flashbox)
     * @param id id of the category to check
     * @return true, if the category has any related records
     *         false otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean categoryHasRelatedRecords(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select flashcardId from flashcards where categoryId=? limit 1 " );
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            rs.close();
            return true;
        }
        rs.close();
        statement = con.prepareStatement("select flashBoxDetailId from flashboxdetails where categoryId=? limit 1 " );
        statement.setInt(1,id);
        rs = statement.executeQuery();
        if(rs.next()) {
            rs.close();
            return true;
        }
        rs.close();
        return  false;
    }

    //  -- FLASH CARD METHODS --
    /**
     * Gets all flashcards from the database
     * @param orderby orderby is a String to define the ordering criteria of list
     * @return an ordered List of flashcards by orderby criteria that contains all flashcards in the database
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashCard> flashCardGetAll(String orderby) throws SQLException {
        return  flashCardGetAll(orderby,"");
    }

    /**
     * Gets all flashcards from the database
     * @param orderby orderby is a String to define the ordering criteria of list
     * @param filter filter is a String to define the filter of list
     * @return an ordered and filtered List of flashcards by orderby criteria and filter that contains all flashcards in the database
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashCard> flashCardGetAll(String orderby, String filter) throws SQLException {
        orderby= orderby==null ? "": orderby;
        filter= filter==null ? "": filter;
        List<FlashCard> list= new ArrayList<>();
        FlashCard flashCard;
        int flashCardId;
        int categoryId;
        String question;
        String answer;
        String keywords;
        Category category;
        Map<Integer,Category> map= categoryGetAllMap();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select flashCardId, flashCards.categoryId, question, answer, keywords "+
                  "from flashCards inner join categories on flashCards.categoryId=categories.categoryId " + filter+" "+ orderby);
        while(rs.next()) {
            flashCardId= rs.getInt("flashCardId");
            categoryId= rs.getInt("categoryId");
            question = rs.getString("question");
            answer = rs.getString("answer");
            keywords = rs.getString("keywords");
            category= map.get(categoryId);
            flashCard= new FlashCard(flashCardId, question,answer, category);
            flashCard.setKeywords(keywords);
            list.add(flashCard);
        }
        return  list;
    }

    /**
     * Gets all flashcards in a category from the database
     * @param selectedCategoryId selectedCategoryId is a categoryId of category from that all flashcards to get
     * @return a flashcards List that contains only all flashcards from selected category
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashCard> flashCardGetByCategory(int  selectedCategoryId) throws SQLException {
        Category category= categoryGet(selectedCategoryId);
        return  flashCardGetByCategory(category);
    }

    /**
     * Gets all flashcards in a category from the database
     * @param selectedCategory selectedCategory is a category from that all flashcards to get
     * @return a flashcards List that contains only all flashcards from selected category
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashCard> flashCardGetByCategory(Category selectedCategory) throws SQLException {
        List<FlashCard> list= new ArrayList<>();
        FlashCard flashCard;
        int flashCardId;
        String question;
        String answer;
        String keywords;
        PreparedStatement statement = con.prepareStatement("select flashCardId, categoryId, question, answer, keywords from flashCards where categoryId=?");
        statement.setInt(1,selectedCategory.getCategoryId());
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            flashCardId= rs.getInt("flashCardId");
            question = rs.getString("question");
            answer = rs.getString("answer");
            keywords = rs.getString("keywords");
            flashCard= new FlashCard(flashCardId, question,answer, selectedCategory);
            flashCard.setKeywords(keywords);
            list.add(flashCard);
        }
        return  list;
    }

    /**
     * Gets a flashcard from the database
     * @param id id is a flashcardId of flashcard to get from database
     * @return a flashcard if there is a flashcard in database with id (flashcardId)
     *         null if there is no flashcard in database with id (flashcardId)
     * @throws SQLException if there is a problem at connecting to database
     */
    public FlashCard flashCardGet(int id) throws SQLException {
        FlashCard flashCard;
        int flashCardId;
        int categoryId;
        String question;
        String answer;
        String keywords;
        PreparedStatement statement = con.prepareStatement("select flashCardId, categoryId, question, answer, keywords from flashCards where flashCardId=?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            flashCardId= rs.getInt("flashCardId");
            categoryId= rs.getInt("categoryId");
            question = rs.getString("question");
            answer = rs.getString("answer");
            keywords = rs.getString("keywords");
            Category category = categoryGet(categoryId);
            flashCard= new FlashCard(flashCardId, question,answer, category);
            flashCard.setKeywords(keywords);
            return  flashCard;
        } else {
            return null;
        }
    }

    /**
     * Adds a flashcard in database
     * @param flashCard flashCard is a flashcard to add in database
     * @return 1 if the flashcard is added
     *         0 if the flashcard is not added
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashCardAdd(FlashCard flashCard) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into flashCards (categoryId, question, answer, keywords) values(?,?,?,?)");
        statement.setInt(1,flashCard.getCategoryId());
        statement.setString(2,flashCard.getQuestion());
        statement.setString(3,flashCard.getAnswer());
        statement.setString(4,flashCard.getKeywords());
        return statement.executeUpdate();
    }

    /**
     * Updates a flashcard in the database
     * @param flashCard flashCard is a flashcard to update
     * @return 1 if the flashcard is updated
     *         0 if the flashcard is not updated
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashCardUpdate(FlashCard flashCard) throws SQLException {
        PreparedStatement statement = con.prepareStatement("update flashCards set categoryId=?, question=?, answer=?, keywords=? where flashCardId=?");
        statement.setInt(1,flashCard.getCategoryId());
        statement.setString(2,flashCard.getQuestion());
        statement.setString(3,flashCard.getAnswer());
        statement.setString(4,flashCard.getKeywords());
        statement.setInt(5,flashCard.getFlashCardId());
        return statement.executeUpdate();
    }

    /**
     * Deletes a flashcard from the database
     * @param id id is a flashCardId of a flashcard to delete
     * @return 1 if the flashcard is deleted
     *         0 if the flashcard is not deleted
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashCardDelete(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from flashCards where flashCardId=?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    /**
     * Checks if the flashcard has any related record (added into a Flashbox)
     * @param id id of the flashcard to check
     * @return  true, if the flashcard has any related record
     *          false otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean flashCardHasRelatedRecords(int id) throws SQLException {
        // flash box
        PreparedStatement statement = con.prepareStatement("select flashBoxDetailId from flashboxdetails where categoryId=? limit 1 " );
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            rs.close();
            return true;
        }
        rs.close();
        return  false;
    }

    /**
     * Gets the category id of a flashcard
     * @param id id of the flashcard whose category id is looked for
     * @return categoryId is an integer value of the category id of the flashcard if there is any flashcard with id
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int getCategoryIdOfFlashCard(int id) throws SQLException {
        int categoryId;
        PreparedStatement statement = con.prepareStatement("select categoryId from flashcards where flashCardId=?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            categoryId= rs.getInt("categoryId");
            return categoryId;
        } else {
            return 0;
        }
    }

    // -- FLASH CARD LINK METHODS --

    /**
     * Gets the list of flashcard links of a flashcard from the database
     * @param flashCardId flashCardId is the id of the flashcard whose flashcard links list will be get
     * @return list is a list of all FlashCardLinks of the flashcard
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashCardLink> flashCardLinkGetAllByFlashCard(int flashCardId) throws SQLException {
        List<FlashCardLink> list= new ArrayList<>();
        FlashCardLink flashCardLink;
        int flashCardLinkId;
        String term;
        int targetFlashCardId;
        FlashCard flashCard ;
        FlashCard targetFlashCard;
        PreparedStatement statement = con.prepareStatement("select flashCardLinkId, flashCardId, term, targetFlashCardId from flashcardlinks where flashCardId=? ");
        statement.setInt(1,flashCardId);
        flashCard = flashCardGet(flashCardId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            flashCardLinkId= rs.getInt("flashCardLinkId");
            term = rs.getString("term");
            targetFlashCardId= rs.getInt("targetFlashCardId");
            targetFlashCard = flashCardGet(targetFlashCardId);
            flashCardLink= new FlashCardLink(flashCardLinkId,flashCard, term,  targetFlashCard);
            list.add(flashCardLink);
        }
        return list;
    }

    /**
     * Checks if the link term has alredy linked to target flashcard
     * @param flashCardId flashCardId is the id of the source flashcard for the link
     * @param term term is the link term for the link
     * @param targetFlashCardId targetFlashCardId is the id of the target flashcard for the link
     * @return true if there is already a link with term from source flashcard to target flashcard
     *         false otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean hasTermTargetFlashCard(int flashCardId, String term,int targetFlashCardId) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select flashCardLinkId from flashcardlinks where flashCardId=? and term=? and targetFlashCardId=? ");
        statement.setInt(1, flashCardId);
        statement.setString(2,term);
        statement.setInt(3, targetFlashCardId);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    /**
     * Adds a flashcard link in the database
     * @param flashCardLink flashCardLink is the flashcard link to be added
     * @return 1 if the flashcard link is added
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashCardLinkAdd(FlashCardLink flashCardLink) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into flashcardlinks (flashCardId, term, targetFlashCardId) values(?,?,?)");
        statement.setInt(1,flashCardLink.getFlashCardId());
        statement.setString(2, flashCardLink.getTerm());
        statement.setInt(3,flashCardLink.getTargetFlashCardId());
        return statement.executeUpdate();
    }

    /**
     * Deletes a flashcard link from the database
     * @param id id is the id of the flashcard link to be deleted
     * @return 1 if the flashcard link is deleted
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashCardLinkDelete(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from flashcardlinks where flashCardLinkId=?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    // -- FLASHBOX METHODS --

    /**
     * Gets all flashboxes from the database
     * @return list is a list of all flashboxes that exist in the database
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashBox> flashBoxGetAll() throws SQLException {
        List<FlashBox> list= new ArrayList<>();
        FlashBox flashBox;
        int flashBoxId;
        String flashBoxName;
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select flashBoxId, flashBoxName from flashboxes");
        while(rs.next()) {
            flashBoxId= rs.getInt("flashBoxId");
            flashBoxName = rs.getString("flashBoxName");
            flashBox= new FlashBox(flashBoxId,flashBoxName);
            list.add(flashBox);
        }
        return list;
    }

    /**
     * Gets a map with flashbox id (key) and flasboxes (value)
     * @return a map with flashbox id (Integer) as key and flashbox object (FlashBox) as value
     * @throws SQLException if there is a problem at connecting to database
     */
    public Map<Integer, FlashBox> flashBoxGetAllMap() throws SQLException {
        Map<Integer, FlashBox> map= new HashMap<>();
        FlashBox flashBox;
        int flashBoxId;
        String flashBoxName;
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select flashBoxId, flashBoxName from flashboxes");
        while(rs.next()) {
            flashBoxId= rs.getInt("flashBoxId");
            flashBoxName = rs.getString("flashBoxName");
            flashBox= new FlashBox(flashBoxId,flashBoxName);
            map.put(flashBoxId,flashBox);
        }
        return  map;
    }

    /**
     * Gets a flashbox for a flashboxId
     * @param id id is the id of the flashbox to be get
     * @return a FlashBox object for id if it exists in database
     *         null otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public FlashBox flashBoxGet(int id) throws SQLException {
        FlashBox flashBox;
        int flashBoxId;
        String flashBoxName;
        PreparedStatement statement = con.prepareStatement("select flashBoxId, flashBoxName from flashboxes where flashBoxId=?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            flashBoxId= rs.getInt("flashBoxId");
            flashBoxName = rs.getString("flashBoxName");
            flashBox= new FlashBox(flashBoxId,flashBoxName);
            return flashBox;
        } else {
            return null;
        }
    }

    /**
     * Checks if the flashBoxName is already exists in the database (for a flashbox)
     * @param flashBoxName flashBoxName is the unique name of a flashbox
     * @return true if the flashBoxName exists already in the database
     *         false otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean hasFlashBox(String flashBoxName) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select flashBoxName from flashboxes where flashBoxName=?");
        statement.setString(1,flashBoxName);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    /**
     * Adds a flashbox in the database
     * @param flashBox flashBox is a flashbox to be added
     * @return 1 if the flashbox is added
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashBoxAdd(FlashBox flashBox) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into flashboxes (flashBoxName) values(?)");
        statement.setString(1,flashBox.getFlashBoxName());
        return statement.executeUpdate();
    }

    /**
     * Updates a flashbox in the database
     * @param flashBox flashBox is a flashbox to be updated
     * @return 1 if the flashbox is updated
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashBoxUpdate(FlashBox flashBox) throws SQLException {
        PreparedStatement statement = con.prepareStatement("update flashboxes set flashBoxName=? where flashBoxId=?");
        statement.setString(1,flashBox.getFlashBoxName());
        statement.setInt(2,flashBox.getFlashBoxId());
        return statement.executeUpdate();
    }

    /**
     * Deletes a flashbox from the database
     * @param id id is the id of the flashbox to be deleted
     * @return 1 if the flashbox is deleted
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashBoxDelete(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from flashboxes where flashBoxId=?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    /**
     * Checks if the flashbox has any related record (added into a learning)
     * @param id id is the id of the flashbox to be checked
     * @return true if the flashbox has related record
     *         false otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean flashBoxHasRelatedRecords(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select  learningId from learnings where flashBoxId=? limit 1 " );
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            rs.close();
            return true;
        }
        rs.close();
        return  false;
    }

    // -- FLASHBOX DETAIL METHODS --

    /**
     * Gets all flashbox details of the flashbox from the database
     * @param flashBoxId flashBoxId is the id of the flashbox whose details will be get
     * @return a list of FlashBoxDetail that includes all flashbox details of the flashbox
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashBoxDetail> flashBoxDetailGetAllByFlashBox(int flashBoxId) throws SQLException {
        List<FlashBoxDetail> list= new ArrayList<>();
        FlashBoxDetail flashBoxDetail;
        int flashBoxDetailId;
        int categoryId;
        FlashBox flashBox= flashBoxGet(flashBoxId);
        Category category;
        PreparedStatement statement = con.prepareStatement("select flashBoxDetailId, flashBoxId, categoryId from flashboxdetails where flashBoxId=?");
        statement.setInt(1, flashBoxId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            flashBoxDetailId= rs.getInt("flashBoxDetailId");
            categoryId = rs.getInt("categoryId");
            category= categoryGet(categoryId);
            flashBoxDetail= new FlashBoxDetail(flashBoxDetailId,flashBox, category);
            list.add(flashBoxDetail);
        }
        return  list;
    }

    /**
     * Gets all flashcards of the flashbox from the database
     * @param flashBoxId flashBoxId is the id of the flashbox whose flashcards will be get
     * @return a list of FlashCard that includes all flashcards of the flashbox
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashCard> flashBoxGetAllFlashCards(int flashBoxId) throws SQLException {
        return flashBoxGetAllFlashCards(flashBoxId,"");
    }

    /**
     * Gets all flashcards of the flashbox from the database that are ordered by criteria
     * @param flashBoxId flashBoxId is the id of the flashbox whose flashcards will be get
     * @param orderby orderby is the criteria for ordering
     * @return an ordered list of FlashCard that includes all flashcards of the flashbox
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<FlashCard> flashBoxGetAllFlashCards(int flashBoxId, String orderby) throws SQLException {
        List<FlashCard> list= new ArrayList<>();
        FlashCard flashCard;
        int flashCardId;
        int categoryId;
        String question;
        String answer;
        String  keywords;
        Category category;
        PreparedStatement statement = con.prepareStatement("select flashCardId, flashCards.categoryId, question, answer, keywords "+
                " from flashboxdetails inner join flashcards on flashboxdetails.categoryId=flashcards.categoryId  where flashBoxId=? " + orderby);
        statement.setInt(1, flashBoxId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            flashCardId= rs.getInt("flashCardId");
            categoryId = rs.getInt("categoryId");
            question = rs.getString("question");
            answer = rs.getString("answer");
            keywords = rs.getString("keywords");
            category= categoryGet(categoryId);
            flashCard= new FlashCard(flashCardId,question,answer,keywords, category);
            list.add(flashCard);
        }
        return  list;
    }

    /**
     * Gets flashbox detail from the database
     * @param id id is id of the flashbox detail to be got
     * @return a FlashBoxDetail object if it exists in the database
     *         null otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public FlashBoxDetail flashBoxDetailGet(int id) throws SQLException {
        FlashBoxDetail flashBoxDetail;
        int flashBoxId;
        int categoryId;
        PreparedStatement statement = con.prepareStatement("select flashBoxDetailId, flashBoxId, categoryId from flashboxdetails where flashBoxDetailId=?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            flashBoxId= rs.getInt("flashBoxId");
            categoryId = rs.getInt("categoryId");
            Category category= categoryGet(categoryId);
            FlashBox flashBox= flashBoxGet(flashBoxId);
            flashBoxDetail= new FlashBoxDetail(id,flashBox,category);
            return  flashBoxDetail;
        } else {
            return null;
        }
    }

    /**
     * Checks whether the flashbox detail (category) is already exists/added in a flashbox in the database
     * @param flashBoxId flashBoxId is the id of the flashbox to be checked
     * @param categoryId categoryId is the id of the category to be checked
     * @return true if the flashbox detail is already added in a flashbox in the database
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean hasFlashBoxDetail(int flashBoxId, int categoryId) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select flashBoxDetailId, flashBoxId, categoryId from flashboxdetails where flashBoxId=? and categoryId=?");
        statement.setInt(1,flashBoxId);
        statement.setInt(2,categoryId);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    /**
     * Adds a flashbox detail in the database
     * @param flashBoxDetail flashBoxDetail is a flashbox detail to be added
     * @return 1 if the flashbox detail is added
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashBoxDetailAdd(FlashBoxDetail flashBoxDetail) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into flashboxdetails (flashBoxId, categoryId) values(?,?)");
        statement.setInt(1,flashBoxDetail.getFlashBoxId());
        statement.setInt(2,flashBoxDetail.getCategoryId());
        return statement.executeUpdate();
    }

    /**
     * Updates a flashbox detail in the database
     * @param flashBoxDetail flashBoxDetail is a flashbox detail to be updated
     * @return 1 if the flashbox detail is updated
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashBoxDetailUpdate(FlashBoxDetail flashBoxDetail) throws SQLException {
        PreparedStatement statement = con.prepareStatement("update flashboxdetails set flashBoxId=?, categoryId=?  where flashBoxDetailId=?");
        statement.setInt(1,flashBoxDetail.getFlashBoxId());
        statement.setInt(2,flashBoxDetail.getCategoryId());
        statement.setInt(3,flashBoxDetail.getFlashBoxDetailId());
        return statement.executeUpdate();
    }

    /**
     * Deletes a flashbox detail from the database
     * @param id id is the id of the flashbox detail to be deleted
     * @return 1 if the flashbox detail is deleted
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int flashBoxDetailDelete(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from flashboxdetails where flashBoxDetailId=?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    //  -- LEVELS --

    /**
     * Gets the levels
     * @return a list of levels
     */
    public List<Level> getLevels() {
        return levels;
    }

    /**
     * Gets the level of the question
     * @param levelId levelId of the level to get
     * @return a Level object if the levelId is included in the levels
     *         null otherwise
     */
    public Level getLevel(int levelId) {
        for (Level level : levels ) {
            if (level.getLevelId() == levelId) {
                return level;
            }
        }
        return null;
    }

    // -- LEARNING METHODS --

    /**
     * Gets all learnings in the database
     * @return a list of learning
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<Learning> learningGetAll() throws SQLException {
        return learningGetAll("");
    }

    /**
     * Gets all learnings in the database whose status is prepared
     * @return a list of learning whose status is prepared
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<Learning> learningGetAllStatusPrepared() throws SQLException {
        return learningGetAll("where status ='prepared' ");
    }

    /**
     * Gets all learnings in the database whose status is started
     * @return a list of learning whose status is started
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<Learning> learningGetAllStatusStarted() throws SQLException {
        return learningGetAll("where status ='started' ");
    }

    /**
     * Gets all learnings in the database whose status is completed
     * @return a list of learning whose status is completed
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<Learning> learningGetAllStatusCompleted() throws SQLException {
        return learningGetAll("where status ='completed' ");
    }

    /**
     * Gets all filtered learnings by filter criteria in the database
     * @param filter filter is the criteria for filtering
     * @return a list of learnings that are filtered by filter
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<Learning> learningGetAll(String filter) throws SQLException {
        filter = filter == null ? "" : filter;
        List<Learning> list= new ArrayList<>();
        Learning learning;
        int learningId;
        String title;
        int flashBoxId;
        String creationDate;
        String startDate;
        String completionDate;
        int resumeLearningDetailId;
        FlashBox flashBox;
        Map<Integer,FlashBox> map = flashBoxGetAllMap();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select learningId, title, flashBoxId, creationDate, startDate, completionDate, resumeLearningDetailId from learnings " + filter);
        while(rs.next()) {
            learningId= rs.getInt("learningId");
            title= rs.getString("title");
            flashBoxId = rs.getInt("flashBoxId");
            creationDate = rs.getString("creationDate");
            startDate = rs.getString("startDate");
            completionDate = rs.getString("completionDate");
            resumeLearningDetailId = rs.getInt("resumeLearningDetailId");
            flashBox = map.get(flashBoxId);
            learning = new Learning(learningId, title, creationDate, startDate, completionDate, flashBox);
            learning.setResumeLearningDetailId(resumeLearningDetailId);
            System.out.println("resumeLearningDetailId: "+ learning.getResumeLearningDetailId());
            list.add(learning);
        }
        return  list;
    }

    /**
     * Gets the learning in the database
     * @param id id is the id of the learning to be got
     * @return a Learning object whose learningId is equal to id
     * @throws SQLException if there is a problem at connecting to database
     */
    public Learning learningGet(int id) throws SQLException {
        Learning learning;
        int learningId;
        String title;
        int flashBoxId;
        String creationDate;
        String startDate;
        String completionDate;
        int resumeLearningDetailId;
        FlashBox flashBox;
        PreparedStatement statement = con.prepareStatement("select learningId, title, flashBoxId, creationDate, startDate, completionDate, resumeLearningDetailId from learnings where learningId=?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            learningId= rs.getInt("learningId");
            title= rs.getString("title");
            flashBoxId = rs.getInt("flashBoxId");
            creationDate = rs.getString("creationDate");
            startDate = rs.getString("startDate");
            completionDate = rs.getString("completionDate");
            resumeLearningDetailId = rs.getInt("resumeLearningDetailId");
            flashBox= flashBoxGet(flashBoxId);
            learning= new Learning(learningId, title, creationDate, startDate, completionDate, flashBox);
            learning.setResumeLearningDetailId(resumeLearningDetailId);
            return  learning;
        } else {
            return null;
        }
    }

    /**
     * Adds the learning in the database
     * @param learning learning is the learning to be added
     * @return 1 if the learning is added
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int learningAdd(Learning learning) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into learnings (title, flashBoxId, creationDate, startDate, completionDate, resumeLearningDetailId) values(?,?,?,?,?,?)");
        statement.setString(1,learning.getTitle());
        statement.setInt(2,learning.getFlashBoxId());
        statement.setString(3,learning.getCreationDate());
        statement.setString(4,learning.getStartDate());
        statement.setString(5,learning.getCompletionDate());
        statement.setInt(6,learning.getResumeLearningDetailId());
        return statement.executeUpdate();
    }

    /**
     * Updates the learning in the database
     * @param learning learning is the learning to be updated
     * @return 1 if the learning is updated
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int learningUpdate(Learning learning) throws SQLException {
        PreparedStatement statement = con.prepareStatement("update learnings set title=?, flashBoxId=?, creationDate=?, startDate=?, completionDate=?, resumeLearningDetailId=? where learningId=?");
        statement.setString(1,learning.getTitle());
        statement.setInt(2,learning.getFlashBoxId());
        statement.setString(3,learning.getCreationDate());
        statement.setString(4,learning.getStartDate());
        statement.setString(5,learning.getCompletionDate());
        statement.setInt(6,learning.getResumeLearningDetailId());
        statement.setInt(7,learning.getLearningId());
        return statement.executeUpdate();
    }

    /**
     * Deletes the learning from the database
     * @param id id is the id of learning to be deleted
     * @return 1 if the learning is deleted
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int learningDelete(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from learnings where learningId=?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    /**
     * Checks if the learning title already exists in the database
     * @param title title is the learning title to be checked
     * @return true if the learning title already exists in the database
     *         false otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean hasLearningTitle(String title) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select title from learnings where title=?");
        statement.setString(1,title);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    // -- LEARNING DETAIL METHODS --

    /**
     * Gets all learning details of the learning in the database
     * @param learningId learningId is the id of the learning whose learning details are to be got
     * @return a list of LearningDetail
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<LearningDetail> learningDetailsGetAll(int learningId) throws SQLException {
        return  learningDetailsGetAll(" where learningId = "+ learningId);
    }

    /**
     * Gets all learning details at the given level of a learning in the database
     * @param learningId learningId is the id of the learning whose learning details will be got
     * @param level level is the level for learning detail
     * @return a list of LearningDetail
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<LearningDetail> learningDetailsGetAll(int learningId, int level) throws SQLException {
        return  learningDetailsGetAll(" where learningId = "+ learningId + " and level =" + level);
    }

    /**
     * Gets all learning details at the given level or less than it of a learning in the database
     * @param learningId  learningId is the id of the learning whose learning details will be got
     * @param level level is the level for learning detail
     * @return a list of LearningDetail
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<LearningDetail> learningDetailsGetAllLevelLessThanOrEqual(int learningId, int level) throws SQLException {
        return  learningDetailsGetAll(" where learningId = "+ learningId + " and level<=" + level);
    }

    /**
     * Gets all filtered learning details by filter criteria of a learning in the database
     * @param filter is the criteria for filtering
     * @return a list of LearningDetail that are filtered by filter
     * @throws SQLException if there is a problem at connecting to database
     */
    public List<LearningDetail> learningDetailsGetAll(String filter) throws SQLException {
        filter= filter==null? "" : filter;
        List<LearningDetail> list= new ArrayList<>();
        LearningDetail learningDetail;
        int learningDetailId;
        int learningId;
        int flashCardId;
        String answer;
        int levelId;
        Level level;
        Learning learning;
        FlashCard flashCard;
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("select learningDetailId, learningId, flashCardId, answer, level   from learningdetails " + filter + " order by learningDetailId");
        while(rs.next()) {
            learningDetailId= rs.getInt("learningDetailId");
            learningId= rs.getInt("learningId");
            flashCardId= rs.getInt("flashCardId");
            answer = rs.getString("answer");
            levelId = rs.getInt("level");
            level = getLevel(levelId);
            learning= learningGet(learningId);
            flashCard = flashCardGet(flashCardId);
            learningDetail= new LearningDetail(learningDetailId, answer, levelId, learning, flashCard);
            learningDetail.setLevel(level);
            list.add(learningDetail);
        }
        return  list;
    }

    /**
     * Gets the learning detail in the database
     * @param id id is the id of the learning detail to be got
     * @return a LearningDetail object if it exists in the database
     *         null otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public LearningDetail learningDetailsGet(int id) throws SQLException {
        LearningDetail learningDetail;
        int learningDetailId;
        int learningId;
        int flashCardId;
        String answer;
        int levelId;
        Level level;
        Learning learning;
        FlashCard flashCard;
        PreparedStatement statement = con.prepareStatement("select learningDetailId, learningId, flashCardId, answer, level   from learningdetails where learningDetailId=?");
        statement.setInt(1,id);
        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            learningDetailId= rs.getInt("learningDetailId");
            learningId= rs.getInt("learningId");
            flashCardId= rs.getInt("flashCardId");
            answer = rs.getString("answer");
            levelId = rs.getInt("level");
            level= getLevel(levelId);
            learning= learningGet(learningId);
            flashCard = flashCardGet(flashCardId);
            learningDetail= new LearningDetail(learningDetailId, answer, levelId, learning, flashCard);
            learningDetail.setLevel(level);
            return  learningDetail;
        } else {
            return null;
        }
    }

    /**
     * Adds the learning detail in the database
     * @param learningDetail learningDetail is the learning detail to be added
     * @return 1 if the learning detail is added
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int learningDetailsAdd(LearningDetail learningDetail) throws SQLException {
        PreparedStatement statement = con.prepareStatement("insert into learningdetails (learningId, flashCardId, answer, level) values(?,?,?,?)");
        statement.setInt(1,learningDetail.getLearningId());
        statement.setInt(2,learningDetail.getFlashCardId());
        statement.setString(3,learningDetail.getAnswer());
        statement.setInt(4,learningDetail.getLevelId());
        return statement.executeUpdate();
    }

    /**
     * Removes all learning details from the learning in the database
     * @param learningId learningId is the id of the learning whose learning details will be removed
     * @return 1 if the learning details are removed
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int learningDetailsRemoveAllFor(int learningId) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from learningdetails where learningId=?");
        statement.setInt(1,learningId);
        return statement.executeUpdate();
    }

    /**
     * Updates the learning detail in the database
     * @param learningDetail learningDetail is the learning detail to be updated
     * @return 1 if the learning detail is updated
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int learningDetailsUpdate(LearningDetail learningDetail) throws SQLException {
        PreparedStatement statement = con.prepareStatement("update learningdetails set learningId=?, flashCardId=?, answer=?, level=? where learningDetailId=? ");
        statement.setInt(1,learningDetail.getLearningId());
        statement.setInt(2,learningDetail.getFlashCardId());
        statement.setString(3,learningDetail.getAnswer());
        statement.setInt(4,learningDetail.getLevelId());
        statement.setInt(5,learningDetail.getLearningDetailId());
        return statement.executeUpdate();
    }

    /**
     * Deletes the learning detail from the database
     * @param id id is the id of the learning detail to be deleted
     * @return 1 if the learning detail is deleted
     *         0 otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public int learningDetailsDelete(int id) throws SQLException {
        PreparedStatement statement = con.prepareStatement("delete from learningdetails where learningDetailId=?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    /**
     * Checks if the learning has any learning details inside it
     * @param learningId learningId is the id of the learning is to be checked
     * @return true if the learning has any learning detail
     *         false otherwise
     * @throws SQLException if there is a problem at connecting to database
     */
    public boolean hasLearningDetails(int learningId) throws SQLException {
        PreparedStatement statement = con.prepareStatement("select learningDetailId from learningdetails where learningId=?");
        statement.setInt(1,learningId);
        ResultSet rs = statement.executeQuery();
        return rs.next();
    }

    /**
     * Creates levels for questions
     */
    private void createLevels() {
        levels.add(new Level(1,"weak",0));
        levels.add(new Level(2,"medium",3));
        levels.add(new Level(3,"strong",7));
        levels.add(new Level(4,"completed",0));
    }

    /**
     * Creates tables in the database for datas
     * @throws SQLException if there is a problem at connecting to database
     */
    public void createTables() throws SQLException {
        Statement statement = con.createStatement();
        statement.setQueryTimeout(30);

        statement.executeUpdate("create table if not exists categories ("+
                "categoryId integer primary key autoincrement," +
                "categoryName string unique not null)");

        statement.executeUpdate("create table if not exists flashcards (" +
                "flashCardId integer primary key autoincrement, " +
                "categoryId integer, " +
                "question string not null, " +
                "answer string not null, " +
                "keywords string null, " +
                "constraint FK_FlashCards_CategoryId Foreign Key (categoryId) "+
                " references categories (categoryId) on delete restrict on update restrict " +
                " ) " );

        statement.executeUpdate("create table if not exists flashcardlinks (" +
                "flashCardLinkId integer primary key autoincrement, " +
                "flashCardId integer not null, " +
                "term string not null, " +
                "targetFlashCardId int not null, " +
                "constraint UNQ_FlashCardLinks_flashCardId_Term_TargetFlashCardId unique (flashCardId,term,targetFlashCardId)," +
                "constraint FK_FlashCardLinks_FlashCardId Foreign Key (flashCardId) "+
                " references flashcards (flashCardId) on delete restrict on update restrict, " +
                "constraint FK_FlashCardLinks_TargetFlashCardId Foreign Key (targetFlashCardId) "+
                " references flashcards (flashCardId) on delete restrict on update restrict " +
                " ) " );

        statement.executeUpdate("create table if not exists flashboxes ("+
                "flashBoxId integer primary key autoincrement," +
                "flashBoxName string unique not null)");

        statement.executeUpdate("create table if not exists flashboxdetails ("+
                "flashBoxDetailId integer primary key autoincrement," +
                "flashBoxId integer not null, " +
                "categoryId integer not null, " +
                "constraint UNQ_FlashBoxes_flashBoxId_categoryId unique (flashBoxId,categoryId)," +
                "constraint FK_FlashBoxes_flashBoxId Foreign Key (flashBoxId) "+
                " references flashboxes (flashBoxId) on delete restrict on update restrict, " +
                "constraint FK_FlashBoxes_categoryId Foreign Key (categoryId) "+
                " references categories (categoryId) on delete restrict on update restrict " +
                ") " );

        statement.executeUpdate("create table if not exists learnings (" +
                "learningId integer primary key autoincrement, " +
                "title string not null, " +
                "flashBoxId integer not null, " +
                "creationDate string not null, " +
                "startDate string , " +
                "completionDate string , " +
                "resumeLearningDetailId int , " +
                "status string GENERATED ALWAYS AS ( case  when completionDate is not null then 'completed' " +
                "                                           when startDate is not null then 'started' " +
                "                                           else 'prepared' end  ) STORED , " +
                "constraint CHK_learnings_LearningId_startDate CHECK (startDate >=creationDate and completionDate>=startDate and completionDate>=creationDate ), " +
                "constraint FK_Learnings_FlashBoxId Foreign Key (flashBoxId) "+
                " references flashboxes (flashBoxId) on delete restrict on update restrict " +
                " ) " );

        statement.executeUpdate("create table if not exists learningdetails (" +
                "learningDetailId integer primary key autoincrement, " +
                "learningId integer not null , " +
                "flashCardId integer not null, " +
                "answer string null, " +
                "level integer not null, " +
                "constraint UNQ_Learningdetails_LearningId_FlashCardId unique (learningId, flashCardId), " +
                "constraint FK_Learningdetails_LearningId Foreign Key (learningId) "+
                " references learnings (learningId) on delete restrict on update restrict, " +
                "constraint FK_Learningdetails_flashCardId Foreign Key (flashCardId) "+
                " references flashcards (flashCardId) on delete restrict on update restrict " +
                " ) " );
        System.out.println("Tables are created...");
    }

    /**
     * Inserts sample data in database to test program
     */
    public  void insertSampleData() {
        try {
            categoryAdd(new Category("Algorithms & Data Structures"));
            categoryAdd(new Category("Operating Systems"));
            categoryAdd(new Category("Automata Theory"));
            categoryAdd(new Category("Machine Learning"));

            flashCardAdd(new FlashCard("Buble sort","Algorithm for sorting that works according to the following principle: " +
                    "Repeatedly swapping the adjacent elements if they are in the wrong order.",1));
            flashCardAdd(new FlashCard("Selection sort","Algorithm for sorting that works according to the following principle: " +
                    "Dividing input list into a sorted sublist and an unsorted sublist. Initially, the sorted sublist is empty and the unsorted sublist is the entire input list. The algorithm proceeds by finding the smallest element in the unsorted sublist, swapping it with the leftmost element of the unsorted sublist, and then adding it to the sorted sublist. It repeats until unsorted sublist is empty.",1));
            flashCardAdd(new FlashCard("Insertion sort","Algorithm for sorting that works according to the following principle: " +
                    "Iterating through the list, and for each element, inserting it into its correct position in a sorted sublist. The sorted sublist is built up one element at a time, with the elements being inserted in the correct order.",1));
            flashCardAdd(new FlashCard("What is faster, Main Memory or Registers?","Registers are faster than main memory.",2));
            flashCardAdd(new FlashCard("The instruction execution consists of what two steps?","Fetch and Execute",2));

            flashCardAdd(new FlashCard("Finite State Machine (FSM)","Any device that stores its current status and whose status can change as the result of an input. Mainly used as a conceptual model for designing and describing systems.",3));
            flashCardAdd(new FlashCard("What are the two types of finite automata?","Deterministic and Non-Deterministic.",3));

            flashCardAdd(new FlashCard("Machine learning","Form of artificial intelligence that does not require you to explicitly program, learning from patterns in data.", "AI, Machine Learning,",4));
            flashCardAdd(new FlashCard("Supervised learning","Type of machine learning in which the response variable is known.", "Learning, Supervised",4));
            flashCardAdd(new FlashCard("Unsupervised learning","Type of machine learning in which the response variable is unknown.", "Learning, Unsupervised",4));

            flashCardLinkAdd(new FlashCardLink(1,"sort",2));
            flashCardLinkAdd(new FlashCardLink(1,"sort",3));
            flashCardLinkAdd(new FlashCardLink(2,"sort",3));

            flashBoxAdd(new FlashBox("Informatik 2. Semester"));
            flashBoxAdd(new FlashBox("Informatik 3. Semester"));
            flashBoxAdd(new FlashBox("Informatik 4. Semester"));

            flashBoxDetailAdd(new FlashBoxDetail(1,1));
            flashBoxDetailAdd(new FlashBoxDetail(1,2));
            flashBoxDetailAdd(new FlashBoxDetail(2,3));
            flashBoxDetailAdd(new FlashBoxDetail(2,4));
            flashBoxDetailAdd(new FlashBoxDetail(3,1));
            flashBoxDetailAdd(new FlashBoxDetail(3,4));

            learningAdd(new Learning("Klausurphase1", 1, LocalDateTime.now().toString(), null, null));
            learningAdd(new Learning("Klausurphase2", 2, LocalDateTime.now().toString(), null, null));
            learningAdd(new Learning("Klausurphase3", 3, LocalDateTime.now().toString(), null, null));
        } catch (SQLException e) {
            System.out.println("insert sample data failed. Data may be exists");
        }
    }

    /**
     * Clears the database to test program
     * @throws SQLException if there is a problem at connecting to database
     */
    public void clearDatabase() throws SQLException {
        Statement statement = con.createStatement();
        statement.executeUpdate("DROP TABLE categories");
        statement.executeUpdate("DROP TABLE flashcards");
        statement.executeUpdate("DROP TABLE flashcardlinks");
        statement.executeUpdate("DROP TABLE flashboxes");
        statement.executeUpdate("DROP TABLE flashboxdetails");
    }

}
