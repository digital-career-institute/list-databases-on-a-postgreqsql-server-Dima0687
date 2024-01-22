package jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class PostgreSqlConnector {
  private static HikariDataSource dataSource = null;
  
  static {
    try {
      Properties prop = new Properties();
      InputStream inputStream = PostgreSqlConnector.class.getClassLoader().getResourceAsStream( "db.properties" );
      prop.load( inputStream );
      
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl( prop.getProperty( "db.url" ) );
      config.setUsername( prop.getProperty( "db.username" ) );
      config.setPassword( prop.getProperty( "db.password" ) );
      config.addDataSourceProperty( "minimumIdle", "5" );
      config.addDataSourceProperty( "maximumPoolSize", "25" );
      
      dataSource = new HikariDataSource( config );
    } catch( IOException e ) {
      e.printStackTrace();
      throw new RuntimeException( "Error loading db.properties or JDBC driver, check if you have it");
    }
  }
  
  public static Connection connectToDB() throws SQLException {
    return dataSource.getConnection();
  }
}
