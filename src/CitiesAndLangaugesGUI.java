import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

/**
 * Denne klasse er udviklet på baggrund af FindGrade.java fra Liangs bog i kapitel 34.
 * Logikken er den samme som i bogen, men koden er opdateret til nyere versioner af Java, JavaFX og Connector/J.
 * Herudover er Liangs database er erstattet med world.sql database.
 *
 *           #                          #######  #     #              #     #
 *           #    ##    #    #    ##    #         #   #               ##   ##    ##     ####   #   ####
 *           #   #  #   #    #   #  #   #          # #                # # # #   #  #   #    #  #  #    #
 *           #  #    #  #    #  #    #  #####       #                 #  #  #  #    #  #       #  #
 *     #     #  ######  #    #  ######  #          # #                #     #  ######  #  ###  #  #
 *     #     #  #    #   #  #   #    #  #         #   #               #     #  #    #  #    #  #  #    #
 *      #####   #    #    ##    #    #  #        #     #              #     #  #    #   ####   #   ####
 * 
 * For at få JavaFX kode til at virke, skal du sikre dig følgende:
 * 
 * (0) Download JavaFX SDK herfra: https://gluonhq.com/products/javafx/
 * 
 * (1) Udpak filen i en ny mappe, f.eks. IdeaProjects/_external_libraries/ (IdeaProjects kan du finde i din brugermappe,
 * og den indeholder alle dine javaprojekter, og heri kan du lave denne _external_libraries undermappe.)
 * 
 * (2) Gå ind på File -> Project Structure -> Project og sæt project SDK til 11, og language level til 11.
 * 
 * (3) Gå ind på  File -> Project Structure -> Libraries og tilføj JavaFX 11 SDK som en library, ved at vælge lib mappen
 * af den JavaFX SDK, som du har gemt i IdeaProjects/_external_libraries/.
 * 
 * Nu kan Java genkende dine JavaFX klasser, og røde linjer forsvinder fra f.eks. Application eller Parent,
 * men du får stadig bøvl, når du prøver at køre programmet. Derfor skal du sætte VM options:
 * 
 * (4) Klik på Run -> Edit Configurations... og tilføj disse VM options:
 * 
 * HVIS DU HAR LINUX / MAC, SKRIV:
 * --module-path /din/path/til/javafx-sdk-11/lib --add-modules javafx.controls,javafx.fxml
 * 
 * HVIS DU HAR PC, SKRIV:
 * --module-path "\sti\til\din\javafx-sdk-13\lib" --add-modules javafx.controls,javafx.fxml
 * 
 * (5) Nu kan du køre projektet, ved at klikke Run -> Run...
 * 
 * (6) Hvis du får en SQL JDBC fejl, så skal du sikre dig, at JDBC-connectoren er også tilføjet til dit projekt som
 * external library. Du kan downloade herfra: https://dev.mysql.com/downloads/connector/j/
 * Det er en god idé at gemme den udpakkede fil i samme _external_libraries mappe som under punkt (1).
 * Du skal også sikre dig, at du har world databasen installeret, og din MySQL kører.
 * 
 * Se skærmbilleder for denne vejledning på den gældende JavaFX manual på:
 * https://openjfx.io/openjfx-docs/#install-javafx --> JavaFX and IntelliJ --> Non-modular from IDE
 */

public class CitiesAndLangaugesGUI extends Application{

    // Statement for executing queries
    private Statement stmt;
    private TextField cityTextField = new TextField();
    private TextField languageTextField = new TextField();
    private Label resultLabel = new Label();

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");//NOTE: The driver has to be added as a lib.
        // Initialize database connection and create a Statement object
        initializeDB();

        Button showGradeButton = new Button("How many speaks this language?");
        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(new Label("City"), cityTextField,
                new Label("Language"), languageTextField, (showGradeButton));

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(hBox, resultLabel);

        cityTextField.setPrefColumnCount(8);
        languageTextField.setPrefColumnCount(8);
        showGradeButton.setOnAction(e -> {
            try {
                showGrade();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(vBox, 620, 120);
        primaryStage.setTitle("Cities vs. languages"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    private void initializeDB() throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");//NOTE: The driver has to be added as a lib.
        // Establish a connection
        String password = DB_Settings.getPassword();
        String username = DB_Settings.geUsername();
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/world", username, password);
        System.out.println("Database connected.");

        // Create a statement
        stmt = connection.createStatement();
    }

    private void showGrade() throws SQLException {
        String city = cityTextField.getText();
        String language = languageTextField.getText();

        String queryString =
                "SELECT city.name, country.Name, countrylanguage.Language, Percentage\n" +
                        "FROM world.country, world.countrylanguage, world.city\n" +
                        "WHERE country.Code = countrylanguage.CountryCode\n" +
                        "AND country.Code = city.CountryCode\n" +
                        "AND city.Name = '" + city + "'\n" +
                        "AND countrylanguage.Language = '" + language + "';";

        ResultSet resultSet = stmt.executeQuery(queryString);

        if (resultSet.next()) {
            String cityName = resultSet.getString("city.name");
            String countryLanguage = resultSet.getString("countrylanguage.Language");
            String percentage = resultSet.getString("Percentage");

            // Display result in a label
            resultLabel.setText("     " + percentage + "% of the citizens in " +
                    cityName + " speaks " + countryLanguage + ".");
        } else {
            resultLabel.setText("City and/or language not found.");
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