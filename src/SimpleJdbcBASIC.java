import java.sql.*;

public class SimpleJdbcBASIC {
    public static void main(String[] args) throws SQLException {

        // #1. Connect to the database
        Connection connection = null;
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/world?serverTimezone=UTC",
                "root",
                "CodeWarrior8"
        );

        // #2. Create a statement / query / forespørgsel
        String mitQuery = "SELECT * FROM world.country WHERE name LIKE 'D%';";
        Statement statement = connection.createStatement();

        // #3. Execute the statement and send the SQL-query to the SQL-server
        ResultSet resultSet = statement.executeQuery
                (mitQuery);

        // #4. Iterate through the result and print the results (vi har måske flere resultater end 1, derfor vil en løkke læse alle rækker ud fra resultSettet)
            while (resultSet.next())
            System.out.println(resultSet.getString(1) + " " +
                    resultSet.getString(2) + ", " +
                    resultSet.getString(3) + ", " +
                    "Population: " +
                    resultSet.getString("Population"));

        // #5. Close the connection (always!)
        connection.close();
    }
}
