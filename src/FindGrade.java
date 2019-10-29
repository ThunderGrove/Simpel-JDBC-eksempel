import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class FindGrade extends Application {

  // Statement for executing queries
  private Statement stmt;
  private TextField cityTextField = new TextField();
  private TextField languageTextField = new TextField();
  private Label lblResult = new Label();

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) throws SQLException {
    // Initialize database connection and create a Statement object
    initializeDB();

    Button btShowGrade = new Button("How many speaks this language?");
    HBox hBox = new HBox(5);
    hBox.getChildren().addAll(new Label("City"), cityTextField,
      new Label("Course ID"), languageTextField, (btShowGrade));

    VBox vBox = new VBox(10);
    vBox.getChildren().addAll(hBox, lblResult);

    cityTextField.setPrefColumnCount(8);
    languageTextField.setPrefColumnCount(6);
    btShowGrade.setOnAction(e -> showGrade());

    // Create a scene and place it in the stage
    Scene scene = new Scene(vBox, 620, 120);
    primaryStage.setTitle("FindGrade"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  private void initializeDB() throws SQLException {
      // Establish a connection
      String password = DB_Settings.getPassword();
      String username = DB_Settings.geUsername();
      Connection connection = DriverManager.getConnection
        ("jdbc:mysql://localhost/world", username, password);
      System.out.println("Database connected.");

      // Create a statement
      stmt = connection.createStatement();
  }

  private void showGrade() {
    String city = cityTextField.getText();
    String language = languageTextField.getText();
    try {

      String queryString =
              "SELECT city.name, country.Name, countrylanguage.Language, Percentage\n" +
              "FROM world.country, world.countrylanguage, world.city\n" +
              "WHERE country.Code = countrylanguage.CountryCode\n" +
              "AND country.Code = city.CountryCode\n" +
              "AND city.Name = '"+ city + "'\n" +
              "AND countrylanguage.Language = '"+language+"';";

      ResultSet rset = stmt.executeQuery(queryString);

      if (rset.next()) {
        String lastName = rset.getString("city.name");
        String mi = rset.getString("countrylanguage.Language");
        String grade = rset.getString("Percentage");


        // Display result in a label
        lblResult.setText( " There are  " + grade + "% of the citizens in  " +
          lastName + " who speaks " + mi + ".");
      } else {
        lblResult.setText("City and/or language not found.");
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}