import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.management.Query;
import java.sql.*;

public class Studentapp extends Application {

    private Statement stmt;
    private TextField fornavn = new TextField();
    private Label resultLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        primaryStage.setTitle("Student");
        Button button = new Button("Indsæt");
        VBox vbox = new VBox(fornavn, button, resultLabel);
        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        initializeDB();

        button.setOnAction(e -> {
            try {
                getinfo();
            } catch (SQLException e1) {
                String fejl = e1.getMessage();
                resultLabel.setText(fejl);
            }
        });
    }

    private void initializeDB() throws SQLException {
        // Establish a connection
        String password = DB_Settings.getPassword();
        String username = DB_Settings.geUsername();
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/Zealand?serverTimezone=UTC", username, password);
        System.out.println("Database connected.");

        // Create a statement
        stmt = connection.createStatement();
    }

    public void getinfo() throws SQLException {
        String fornavnString = fornavn.getText();
        String query = "SELECT * FROM zealand.studerende WHERE studerende.fornavn ='" + fornavnString + "';";
        ResultSet resultSet = stmt.executeQuery(query);
        if (resultSet.next()) {
            String dbfornavn = resultSet.getString("fornavn");
            String dbefternavn = resultSet.getString("efternavn");
            resultLabel.setText("fornavn: " + dbfornavn + "\n" + "efternavn: " + dbefternavn);
        }
    }
}


