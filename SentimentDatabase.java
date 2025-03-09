import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class SentimentDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/sentiment_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "mysql"; 
    public static void main(String[] args) {
        String term;
        String sentiment;
        Scanner sc = new Scanner (System.in);
        term = sc.nextLine();
        sentiment = sc.nextLine();
        insertSentiment(term, sentiment);
    }

    public static void insertSentiment(String term, String sentiment) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO sentiments (term, sentiment) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, term);
            preparedStatement.setString(2, sentiment);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into the sentiments table.");

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {

            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}