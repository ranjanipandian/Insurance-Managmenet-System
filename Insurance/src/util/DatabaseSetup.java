package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String URL = "jdbc:mysql://localhost:3306/InsuranceManagementSystem"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "Ranjp19@"; 

    public static void createTables() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null) {
                System.out.println("\n\n✅ DATABASE CONNECTION ESTABLISHED SUCCESSFULLY ! \n\n");
            }

            String createClientTable = "CREATE TABLE IF NOT EXISTS Client (" +
                    "clientId INT PRIMARY KEY," +
                    "clientName VARCHAR(100) NOT NULL," +
                    "contactInfo VARCHAR(255) NOT NULL," +
                    "policy VARCHAR(100) NOT NULL" +
                    ");";
            

            String createClaimTable = "CREATE TABLE IF NOT EXISTS Claim (" +
                    "claimId INT PRIMARY KEY," +
                    "claimNumber VARCHAR(50) NOT NULL," +
                    "dateFiled DATE NOT NULL," +
                    "claimAmount DOUBLE NOT NULL," +
                    "status VARCHAR(50) NOT NULL," +
                    "policy VARCHAR(100) NOT NULL," +
                    "clientId INT," +
                    "FOREIGN KEY (clientId) REFERENCES Client(clientId)" +
                    ");";

            String createPaymentTable = "CREATE TABLE IF NOT EXISTS Payment (" +
                    "paymentId INT PRIMARY KEY," +
                    "paymentDate DATE NOT NULL," +
                    "paymentAmount DOUBLE NOT NULL," +
                    "clientId INT," +
                    "FOREIGN KEY (clientId) REFERENCES Client(clientId)" +
                    ");";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createClientTable);
                stmt.execute(createClaimTable);
                stmt.execute(createPaymentTable);
                System.out.println("✅ TABLES CREATED SUCCESSFULLY !\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }}

