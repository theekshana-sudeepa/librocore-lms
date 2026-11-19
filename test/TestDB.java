package test;

import lk.lms.lmsystem.connection.MySQL;
import java.sql.ResultSet;

public class TestDB {
    public static void main(String[] args) {
        try {
            System.out.println("Testing MySQL connection from Java...");
            ResultSet rs = MySQL.search("SELECT COUNT(*) AS total_books FROM book");
            if (rs.next()) {
                System.out.println(">>> SUCCESS! Book count in database: " + rs.getInt("total_books"));
            }
            ResultSet uRs = MySQL.search("SELECT * FROM login_details");
            while (uRs.next()) {
                System.out.println(">>> User in DB: " + uRs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
