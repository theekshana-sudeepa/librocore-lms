package lk.lms.lmsystem.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MySQL {
    private static Connection connection;
    private static String host = "127.0.0.1";
    private static String port = "3307";
    private static String database = "library_dbdb";
    private static String user = "root";
    private static String password = "";

    static {
        loadConfig();
    }

    public static String getHost() {
        return host;
    }

    public static String getPort() {
        return port;
    }

    public static String getDatabase() {
        return database;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    private static void loadConfig() {
        Properties props = new Properties();
        try {
            File propFile = new File("db.properties");
            if (propFile.exists()) {
                try (InputStream in = new FileInputStream(propFile)) {
                    props.load(in);
                }
            } else {
                InputStream resIn = MySQL.class.getClassLoader().getResourceAsStream("db.properties");
                if (resIn != null) {
                    try (resIn) {
                        props.load(resIn);
                    }
                }
            }
        } catch (Exception ignored) {
        }

        host = System.getProperty("db.host", System.getenv().getOrDefault("DB_HOST", props.getProperty("db.host", host)));
        port = System.getProperty("db.port", System.getenv().getOrDefault("DB_PORT", props.getProperty("db.port", port)));
        database = System.getProperty("db.name", System.getenv().getOrDefault("DB_NAME", props.getProperty("db.name", database)));
        user = System.getProperty("db.user", System.getenv().getOrDefault("DB_USER", props.getProperty("db.user", user)));
        password = System.getProperty("db.password", System.getenv().getOrDefault("DB_PASSWORD", props.getProperty("db.password", password)));
    }

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // First try the configured port
                String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
                try {
                    connection = DriverManager.getConnection(url, user, password);
                } catch (SQLException ex) {
                    // Smart fallback: If port was 3306, try 3307. If 3307, try 3306.
                    String altPort = "3307".equals(port) ? "3306" : "3307";
                    try {
                        String altUrl = "jdbc:mysql://" + host + ":" + altPort + "/" + database + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
                        connection = DriverManager.getConnection(altUrl, user, password);
                        port = altPort; // Update active port
                    } catch (SQLException ex2) {
                        System.err.println("[MySQL Error] Could not connect on port " + port + " or " + altPort + ": " + ex2.getMessage());
                        throw ex;
                    }
                }
                System.out.println("[MySQL] Connected successfully to " + host + ":" + port + "/" + database);
            }
            return connection;
        } catch (ClassNotFoundException ex) {
            throw new ExceptionInInitializerError("MySQL Driver not found in classpath: " + ex.getMessage());
        } catch (SQLException ex) {
            throw new ExceptionInInitializerError("Oops! MySQL Connection Failed: " + ex.getMessage());
        }
    }

    public static int iud(String query) {
        try {
            return getConnection().createStatement().executeUpdate(query);
        } catch (SQLException ex) {
            System.err.println("[MySQL IUD Error] Query: " + query);
            throw new RuntimeException(ex);
        }
    }
    
    public static ResultSet search(String query) throws SQLException {
        return getConnection().createStatement().executeQuery(query);
    }
}
