package de.unibremen.swp.activerecall2;

import de.unibremen.swp.activerecall2.persistence.DbService;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class FlashCardAppTest
    extends TestCase
{
    DbService dbService = new DbService();

    @BeforeEach
    public void setUp() throws SQLException {
        dbService.clearDatabase();
        dbService.createTables();
        dbService.insertSampleData();
    }

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FlashCardAppTest(String testName ) throws SQLException, ClassNotFoundException {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FlashCardAppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
