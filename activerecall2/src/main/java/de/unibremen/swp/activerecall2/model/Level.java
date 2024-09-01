package de.unibremen.swp.activerecall2.model;

/**
 * This class provides the structure of a level in the data model.
 *
 * @author Ömer Faruk Büyükbas
 */
public class Level {

    /** Unique ID number for a level */
    private int levelId;

    /** Name of a level */
    private String name;

    /** Days to start the level */
    private int daysStart;

    /**
     * The constructor of this class
     * @param levelId levelId is the ID of the level
     * @param name name is the name of the level
     * @param daysStart daysStart is the days to start the level
     */
    public Level(int levelId, String name, int daysStart) {
        this.levelId = levelId;
        this.name = name;
        this.daysStart = daysStart;
    }

    /**
     * It gets the level ID
     * @return levelId
     */
    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    /**
     * It gets the name of the level
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * It sets the name of the level
     * @param name name is the name of the level
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * It gets the days to start the level
     * @return daysStart
     */
    public int getDaysStart() {
        return daysStart;
    }

    public void setDaysStart(int daysStart) {
        this.daysStart = daysStart;
    }

    /**
     *  a String Text containing levelId, name and daysStart for a level
     */
    @Override
    public String toString() {
        return "Level{" +
                "levelId=" + levelId +
                ", name='" + name + '\'' +
                ", daysStart=" + daysStart +
                '}';
    }
}
