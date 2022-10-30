package c195.task_1.java_c195.helper;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    public static Connection connection;

    /**
     * open database connection
     * @return connection
     */
    public static Connection openConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
        return connection;
    }

    /**
     * close database connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
