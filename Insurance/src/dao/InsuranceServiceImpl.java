package dao;

import entity.Client;
import entity.Claim;
import entity.Payment;

import java.sql.*;

public class InsuranceServiceImpl implements IPolicyService {
    private String url = "jdbc:mysql://localhost:3306/InsuranceManagementSystem";
    private String user = "root";
    private String password = "Ranjp19@";

    @Override
    public void createClient(Client client) {
        String sql = "INSERT INTO Client (clientName, contactInfo, policy) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, client.getClientName());
            pstmt.setString(2, client.getContactInfo());
            pstmt.setString(3, client.getPolicy());
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    client.setClientId(rs.getInt(1)); // set the generated ID back to object
                }
                System.out.println("✅ Client added successfully! Assigned ID: " + client.getClientId());
            } else {
                System.out.println("❌ Failed to add client.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client getClient(int clientId) {
        String sql = "SELECT * FROM Client WHERE clientId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getInt("clientId"),
                        rs.getString("clientName"),
                        rs.getString("contactInfo"),
                        rs.getString("policy")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createClaim(Claim claim) {
        String sql = "INSERT INTO Claim (claimNumber, dateFiled, claimAmount, status, policy, clientId) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, claim.getClaimNumber());
            pstmt.setDate(2, new java.sql.Date(claim.getDateFiled().getTime()));
            pstmt.setDouble(3, claim.getClaimAmount());
            pstmt.setString(4, claim.getStatus());
            pstmt.setString(5, claim.getPolicy()); // assuming policy holds policyId as string
            pstmt.setInt(6, claim.getClient().getClientId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public Claim getClaim(int claimId) {
        String sql = "SELECT * FROM Claim WHERE claimId = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, claimId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Claim(
                        rs.getInt("claimId"),
                        rs.getString("claimNumber"),
                        rs.getDate("dateFiled"),
                        rs.getDouble("claimAmount"),
                        rs.getString("status"),
                        String.valueOf(rs.getInt("policyId")),
                        new Client(rs.getInt("clientId"), "", "", "")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createPayment(Payment payment) {
        String sql = "INSERT INTO Payment (paymentId, paymentDate, paymentAmount, clientId) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payment.getPaymentId());
            pstmt.setDate(2, new java.sql.Date(payment.getPaymentDate().getTime()));
            pstmt.setDouble(3, payment.getPaymentAmount());
            pstmt.setInt(4, payment.getClient().getClientId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
