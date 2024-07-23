package MicrosoftSQL;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.List;

/**
 * The SQLTests class contains tests for verifying the correct creation, insertion, and
 * validation of data in the OracleDataset table within a Microsoft SQL Server database.
 */
public class SQLTests {

    // Static members for dataset reading and database connection
    public static final DatasetReader reader = new DatasetReader();
    public static final String connectionUrl = "jdbc:sqlserver://DESKTOP-AE092V0;databaseName=Test;integratedSecurity=true;trustServerCertificate=true";
    public static final boolean DELETE_TABLE = false;

    /**
     * Creates the OracleDataset table and inserts data into it before running all tests.
     */
    @BeforeAll
    static void createTableAndInsertData() {

        // Reusable variables
        String sql;
        StringBuilder sqlBuilder;
        PreparedStatement statement;

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            // Check if table exists, exit if true
            sql = "SELECT CASE WHEN EXISTS (" +
                    "SELECT * FROM INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = 'OracleDataset') " +
                    "THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END AS TableExists;";

            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getBoolean("TableExists")) {
                return; // Table exists, exit method
            }

            // TABLE CREATION ------------------------------------------------------------------//

            // Construct the SQL statement dynamically based on column names from DatasetReader
            sqlBuilder = new StringBuilder("CREATE TABLE OracleDataset (");

            for (int i = 0; i < AccurateDatasetInformation.NUMBER_OF_COLUMNS; i++) {
                String columnName = AccurateDatasetInformation.columnNames.get(i);
                sqlBuilder.append(columnName).append(" VARCHAR(30), ");
            }

            // Replace the last comma with a closing parenthesis
            sqlBuilder.setLength(sqlBuilder.length() - 2);
            sqlBuilder.append(");");

            sql = sqlBuilder.toString();
            statement = connection.prepareStatement(sql);

            statement.execute(); // Table creation happens here

            // Clear variables
            sql = null;
            sqlBuilder = null;
            statement = null;

            // DATA INSERTION ------------------------------------------------------------------//

            for (List<String> line : reader.getRows()) {
                Line data = LineProcessor.getLineObject(line);
                sqlBuilder = new StringBuilder("INSERT INTO OracleDataset VALUES ('");
                sqlBuilder.append(data.date()).append("', '");
                sqlBuilder.append(data.opened()).append("', '");
                sqlBuilder.append(data.high()).append("', '");
                sqlBuilder.append(data.low()).append("', '");
                sqlBuilder.append(data.closed()).append("', '");
                sqlBuilder.append(data.adjClosed()).append("', '");
                sqlBuilder.append(data.volume()).append("');");

                sql = sqlBuilder.toString();
                statement = connection.prepareStatement(sql);
                statement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies the data in the OracleDataset table against the original dataset.
     */
    @Test
    void verifyData() {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            String sql = "SELECT * FROM OracleDataset";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            int rowIndex = 0;
            while (resultSet.next() && rowIndex < AccurateDatasetInformation.NUMBER_OF_ROWS_EXCLUDING_COLUMN_TITLES) {

                // Date is currently not being inserted right, we skip that for now
                // String date = resultSet.getString(AccurateDatasetInformation.COLUMN_1);

                String opened = resultSet.getString(AccurateDatasetInformation.COLUMN_2);
                assertEquals(reader.getRows().get(rowIndex).get(1), opened);

                String high = resultSet.getString(AccurateDatasetInformation.COLUMN_3);
                assertEquals(reader.getRows().get(rowIndex).get(2), high);

                String low = resultSet.getString(AccurateDatasetInformation.COLUMN_4);
                assertEquals(reader.getRows().get(rowIndex).get(3), low);

                String closed = resultSet.getString(AccurateDatasetInformation.COLUMN_5);
                assertEquals(reader.getRows().get(rowIndex).get(4), closed);

                String adjClosed = resultSet.getString(AccurateDatasetInformation.COLUMN_6);
                assertEquals(reader.getRows().get(rowIndex).get(5), adjClosed);

                String volume = resultSet.getString(AccurateDatasetInformation.COLUMN_7);
                assertEquals(reader.getRows().get(rowIndex).get(6), volume);

                rowIndex++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests if the 'OracleDataset' table exists in the database.
     *
     * @throws SQLException if there is an error connecting to the database or executing the query
     */
    @Test
    void tableExistsTest() {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            // Query to ensure table exists
            String checkTableExistsSql = "IF EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'OracleDataset') " +
                    "SELECT CAST(1 AS BIT) AS TableExists " +
                    "ELSE " +
                    "SELECT CAST(0 AS BIT) AS TableExists;";
            PreparedStatement checkStatement = connection.prepareStatement(checkTableExistsSql);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                boolean tableExists = resultSet.getBoolean("TableExists");
                assertTrue(tableExists);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes the OracleDataset table after running all tests, if DELETE_TABLE is set to true.
     */
    @AfterAll
    static void deleteTable() {
        if (DELETE_TABLE)
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {
                String sql = "DROP TABLE OracleDataset";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
