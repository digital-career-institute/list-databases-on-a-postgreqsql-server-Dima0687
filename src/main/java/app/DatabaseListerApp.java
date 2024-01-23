package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.PostgreSqlConnector;

public class DatabaseListerApp {

  public static void main( String[] args ) {
    try {
      performDatabaseOperation();
    } catch( SQLException e) {
      e.printStackTrace();
    }
  }

  private static void performDatabaseOperation() throws SQLException {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet= null;
    
    try {
      connection = PostgreSqlConnector.connectToDB();
      statement = connection.createStatement();
      resultSet = statement.executeQuery( "SELECT datname FROM pg_database" );
      
      if (!resultSet.next()) {
        System.out.println("No records found in the pg_database.");
      } else {

        System.out.println( "DATABASE NAME" );
        do {
            String databaseName = resultSet.getString( "datname" );
            System.out.printf( "- %s%n", databaseName );
        } while (resultSet.next());
      }
    } finally {
      if (resultSet != null) resultSet.close();
      if (statement != null) statement.close();
      if (connection != null) connection.close();
    }
  }
}
