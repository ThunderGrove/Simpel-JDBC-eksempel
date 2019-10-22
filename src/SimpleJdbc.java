import java.sql.*;
import java.util.Scanner;

public class SimpleJdbc {
  public static void main(String[] args) {

    // Connect to a database
    Connection connection = null;
    try {
      connection = DriverManager.getConnection
              ("jdbc:mysql://localhost/world?serverTimezone=UTC", "root", "CodeWarrior8");

    System.out.println("Database connected");

    // Create a statement
    Statement statement = connection.createStatement();

    Scanner scanner = new Scanner(System.in);
    System.out.print("Hvilket land vil du se oplysninger om? ");
    String landeNavn = scanner.nextLine();

    // Execute a statement
    ResultSet resultSet = statement.executeQuery
            ("SELECT * FROM world.country WHERE Name = '"  + landeNavn + "';");

    // Iterate through the result and print the student names
    while (resultSet.next())
      System.out.println(resultSet.getString(1) + "\t" +
              resultSet.getString(2) + "\t" + resultSet.getString(3)+ "\t" + resultSet.getString(4)+ " population: " + resultSet.getString("Population"));

    // Close the connection
    connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}