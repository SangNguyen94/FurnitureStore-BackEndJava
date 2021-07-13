/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FurnitureStore.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.jsp.jstl.core.Config;
import javax.sql.DataSource;




public class DBConnectionService {
//	public static HikariConfig config = new HikariConfig();
//	public static HikariDataSource createPool() throws NamingException, SQLException {
//		// HikariConfig config = new HikariConfig();
//
// 	    // The following is equivalent to setting the config options below:
// 	    // jdbc:sqlserver://;user=<DB_USER>;password=<DB_PASS>;databaseName=<DB_NAME>;
// 	    // socketFactoryClass=com.google.cloud.sql.sqlserver.SocketFactory;
// 	    // socketFactoryConstructorArg=<CLOUD_SQL_CONNECTION_NAME>
// 	    
// 	    // See the link below for more info on building a JDBC URL for the Cloud SQL JDBC Socket Factory
// 	    // https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory#creating-the-jdbc-url
//
// 	    // Configure which instance and what database user to connect with.
// 	    config
// 	        .setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
// 	    config.setUsername("sqlserver"); // e.g. "root", "sqlserver"
// 	    config.setPassword("Superpck123"); // e.g. "my-password"
// 	    config.addDataSourceProperty("databaseName", "FurnitureStore");
//
// 	    config.addDataSourceProperty("socketFactoryClass",
// 	        "com.google.cloud.sql.sqlserver.SocketFactory");
// 	    config.addDataSourceProperty("socketFactoryConstructorArg", "handy-compass-315314:asia-southeast1:myinstance");
//
// 	    // ... Specify additional connection properties here.
//
// 	    // [START_EXCLUDE]
//
// 	    // [START cloud_sql_sqlserver_servlet_limit]
// 	    // maximumPoolSize limits the total number of concurrent connections this pool will keep. Ideal
// 	    // values for this setting are highly variable on app design, infrastructure, and database.
// 	    config.setMaximumPoolSize(5);
// 	    // minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
// 	    // Additional connections will be established to meet this value unless the pool is full.
// 	    config.setMinimumIdle(5);
// 	    // [END cloud_sql_sqlserver_servlet_limit]
//
// 	    // [START cloud_sql_sqlserver_servlet_timeout]
// 	    // setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
// 	    // Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
// 	    // SQLException.
// 	    config.setConnectionTimeout(10000); // 10 seconds
// 	    // idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
// 	    // sit idle for this many milliseconds are retried if minimumIdle is exceeded.
// 	    config.setIdleTimeout(600000); // 10 minutes
// 	    // [END cloud_sql_sqlserver_servlet_timeout]
//
// 	    // [START cloud_sql_sqlserver_servlet_backoff]
// 	    // Hikari automatically delays between failed connection attempts, eventually reaching a
// 	    // maximum delay of `connectionTimeout / 2` between attempts.
// 	    // [END cloud_sql_sqlserver_servlet_backoff]
//
// 	    // [START cloud_sql_sqlserver_servlet_lifetime]
// 	    // maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
// 	    // live longer than this many milliseconds will be closed and reestablished between uses. This
// 	    // value should be several minutes shorter than the database's timeout value to avoid unexpected
// 	    // terminations.
// 	    config.setMaxLifetime(1800000); // 30 minutes
// 	    // [END cloud_sql_sqlserver_servlet_lifetime]
//
// 	    // [END_EXCLUDE]
// 	    HikariDataSource connectionPool = new HikariDataSource(config);
// 	    return connectionPool;
//	}
    public static Connection getConnection() throws NamingException, SQLException {
        Connection conn = null;
        try {
//        	 HikariConfig config = new HikariConfig();
//        	 config
//             .setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
//         config.setUsername("sqlserver"); // e.g. "root", "sqlserver"
//         config.setPassword("Superpck123"); // e.g. "my-password"
//         config.addDataSourceProperty("databaseName", "FurnitureStore");
//         
//         config.addDataSourceProperty("socketFactoryClass",
//        	        "com.google.cloud.sql.sqlserver.SocketFactory");
//        	    config.addDataSourceProperty("socketFactoryConstructorArg", "handy-compass-315314:asia-southeast1:myinstance");
//        	    config.setConnectionTimeout(10000); // 10s
        	
        	// HikariConfig config = new HikariConfig();

        	    // The following is equivalent to setting the config options below:
        	    // jdbc:sqlserver://;user=<DB_USER>;password=<DB_PASS>;databaseName=<DB_NAME>;
        	    // socketFactoryClass=com.google.cloud.sql.sqlserver.SocketFactory;
        	    // socketFactoryConstructorArg=<CLOUD_SQL_CONNECTION_NAME>
        	    
        	    // See the link below for more info on building a JDBC URL for the Cloud SQL JDBC Socket Factory
        	    // https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory#creating-the-jdbc-url

        	    // Configure which instance and what database user to connect with.
//        	    config
//        	        .setDataSourceClassName("com.microsoft.sqlserver.jdbc.SQLServerDataSource");
//        	    config.setUsername("sqlserver"); // e.g. "root", "sqlserver"
//        	    config.setPassword("Superpck123"); // e.g. "my-password"
//        	    config.addDataSourceProperty("databaseName", "FurnitureStore");
//
//        	    config.addDataSourceProperty("socketFactoryClass",
//        	        "com.google.cloud.sql.sqlserver.SocketFactory");
//        	    config.addDataSourceProperty("socketFactoryConstructorArg", "handy-compass-315314:asia-southeast1:myinstance");

        	    // ... Specify additional connection properties here.

        	    // [START_EXCLUDE]

        	    // [START cloud_sql_sqlserver_servlet_limit]
        	    // maximumPoolSize limits the total number of concurrent connections this pool will keep. Ideal
        	    // values for this setting are highly variable on app design, infrastructure, and database.
//        	    config.setMaximumPoolSize(5);
//        	    // minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
//        	    // Additional connections will be established to meet this value unless the pool is full.
//        	    config.setMinimumIdle(5);
//        	    // [END cloud_sql_sqlserver_servlet_limit]
//
//        	    // [START cloud_sql_sqlserver_servlet_timeout]
//        	    // setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
//        	    // Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
//        	    // SQLException.
//        	    config.setConnectionTimeout(10000); // 10 seconds
//        	    // idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
//        	    // sit idle for this many milliseconds are retried if minimumIdle is exceeded.
//        	    config.setIdleTimeout(600000); // 10 minutes
//        	    // [END cloud_sql_sqlserver_servlet_timeout]
//
//        	    // [START cloud_sql_sqlserver_servlet_backoff]
//        	    // Hikari automatically delays between failed connection attempts, eventually reaching a
//        	    // maximum delay of `connectionTimeout / 2` between attempts.
//        	    // [END cloud_sql_sqlserver_servlet_backoff]
//
//        	    // [START cloud_sql_sqlserver_servlet_lifetime]
//        	    // maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
//        	    // live longer than this many milliseconds will be closed and reestablished between uses. This
//        	    // value should be several minutes shorter than the database's timeout value to avoid unexpected
//        	    // terminations.
//        	    config.setMaxLifetime(1800000); // 30 minutes
//        	    // [END cloud_sql_sqlserver_servlet_lifetime]
//
//        	    // [END_EXCLUDE]
//        	    HikariDataSource connectionPool = createPool();
            String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String URL = "jdbc:sqlserver://localhost;databaseName=FurnitureStore;integratedSecurity=true;";
            String cloudURLString="jdbc:sqlserver://myinstance1.cz2v8uzq78e3.ap-southeast-1.rds.amazonaws.com:1433;databaseName=FurnitureStore;";
            String username = "sangnguyen";
            String password = System.getenv("RD_SQL_PASSWORD");

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(cloudURLString,username,"Superpck123");
            //conn = DriverManager.getConnection(URL);
            //conn=createPool().getConnection();
            System.out.println("DB connected successfully!");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (Exception e) {
//        	System.out.println("password: "+config.getUsername());
//            System.out.println("password: "+config.getPassword());
            System.out.println("Exception: " + e.getMessage());
        }
        return conn;
    }

    public static Connection getConnection(String masterDB) throws NamingException, SQLException {
        Connection conn = null;
        try {
            String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=master;integratedSecurity=true;";
            String cloudURLString="jdbc:sqlserver://localhost;databaseName=FurnitureStore;socketFactoryClass=com.google.cloud.sql.sqlserver.SocketFactory;socketFactoryConstructorArg=handy-compass-315314:asia-southeast1:myinstance;user=sqlserver;password=Superpck123";
            String username = "";
            String password = "";

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(cloudURLString, username, password);
            System.out.println("DB connected successfully!");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return conn;
    }
}
