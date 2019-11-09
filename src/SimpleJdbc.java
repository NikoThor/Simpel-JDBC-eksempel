import java.sql.*;
import java.util.Scanner;

public class SimpleJdbc {
    public static void main(String[] args) throws SQLException {

        System.out.println("\n****** \nProgram til hentning af landedata fra Zealand.sql databasen. \n****** ");

        // #1. Connect to the database
        Connection connection = null;
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Zealand?serverTimezone=UTC",
                "root",
                "CodeWarrior8"
        );

        System.out.println("\nDatabase connected.");

        // #2. Create a statement / query / forespørgsel

        // ** Med Scanner kan vi få brugeren til at indtaste, hvilket land, de vil se info om
         Scanner scanner = new Scanner(System.in);
         System.out.print("Hvilken student vil du se oplysninger om? vælg mellem N, T og T og husk %! ±");
         String student = scanner.nextLine();
         String mitQuery ="SELECT studerende.fornavn, studerende.efternavn FROM studerende WHERE fornavn LIKE '" + student + "';";

        // ** Uden scanner kan vi kun vise det land, som vi harcoder i query-et
        // String mitQuery = "SELECT * FROM world.country WHERE name LIKE 'D%';";
        Statement statement = connection.createStatement();

        // #3. Execute the statement and send the SQL-query to the SQL-server
        ResultSet resultSet = statement.executeQuery
                (mitQuery);
        System.out.println("Følgende query er sendt til MySQL-serveren: " + mitQuery);
        System.out.println("Svar fra databasen: " + "\n");


        int i = 0;
        // #4. Iterate through the result and print the results (vi har måske flere resultater end 1, derfor vil en løkke læse alle rækker ud fra resultSettet)
            while (resultSet.next())
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));

        // #5. Close the connection (always!)
        connection.close();
        System.out.println();
    }
}
