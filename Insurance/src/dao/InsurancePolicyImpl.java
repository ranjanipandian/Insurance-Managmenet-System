package dao;

import entity.Policy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsurancePolicyImpl implements IPolicyManage {
    private String url = "jdbc:mysql://localhost:3306/InsuranceManagementSystem";
    private String user = "root";
    private String password = "Ranjp19@";

    // Allowed policy types
    private static final String VALID_POLICY_TYPES = "Health, Vehicle, Life, Home, Bike";

    @Override
    public boolean createPolicy(Policy policy) {
        String policyTypeInput = policy.getPolicyType().trim();

        // Validate policy type
        if (!VALID_POLICY_TYPES.contains(policyTypeInput)) {
            return false;  // No success message here, just return false
        }

        String checkQuery = "SELECT COUNT(*) FROM Policy WHERE policyName = ?";
        String insertQuery = "INSERT INTO Policy (policyName, policyType, coverageAmount, premiumAmount, tenure) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Check if the policy already exists
            checkStmt.setString(1, policy.getPolicyName());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;  // No success message here, just return false
            }

            // Insert the new policy
            insertStmt.setString(1, policy.getPolicyName());
            insertStmt.setString(2, policyTypeInput);
            insertStmt.setDouble(3, policy.getCoverageAmount());
            insertStmt.setDouble(4, policy.getPremiumAmount());
            insertStmt.setInt(5, policy.getTenure());

            int rowsAffected = insertStmt.executeUpdate();
            return rowsAffected > 0;  // Just return the result, no success message

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // If there is an error, return false
    }

    @Override
    public Policy getPolicy(int policyId) {
        String query = "SELECT * FROM policy WHERE policyId = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, policyId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Policy(
                    rs.getInt("policyId"),
                    rs.getString("policyName"),
                    rs.getString("policyType"),
                    rs.getDouble("coverageAmount"),
                    rs.getDouble("premiumAmount"),
                    rs.getInt("tenure")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if policy not found
    }

    @Override
    public List<Policy> getAllPolicies() {
        List<Policy> policies = new ArrayList<>();
        String query = "SELECT * FROM policy";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                policies.add(new Policy(
                    rs.getInt("policyId"),
                    rs.getString("policyName"),
                    rs.getString("policyType"),
                    rs.getDouble("coverageAmount"),
                    rs.getDouble("premiumAmount"),
                    rs.getInt("tenure")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return policies;  // Return the list of all policies
    }

    @Override
    public boolean updatePolicy(Policy policy) {
        String policyTypeInput = policy.getPolicyType().trim();

        // Validate policy type
        if (!VALID_POLICY_TYPES.contains(policyTypeInput)) {
            return false;  // No success message here, just return false
        }

        String query = "UPDATE policy SET policyName=?, policyType=?, coverageAmount=?, premiumAmount=?, tenure=? WHERE policyId=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, policy.getPolicyName());
            pstmt.setString(2, policyTypeInput);
            pstmt.setDouble(3, policy.getCoverageAmount());
            pstmt.setDouble(4, policy.getPremiumAmount());
            pstmt.setInt(5, policy.getTenure());
            pstmt.setInt(6, policy.getPolicyId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;  // Just return the result, no success message

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // If there is an error, return false
    }

    @Override
    public boolean deletePolicy(int policyId) {
        String query = "DELETE FROM policy WHERE policyId = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, policyId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;  // Return true if the deletion was successful

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // If there is an error, return false
    }
}
