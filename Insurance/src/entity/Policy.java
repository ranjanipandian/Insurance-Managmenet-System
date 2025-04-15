package entity;

public class Policy {
    private int policyId;
    private String policyName;
    private String policyType;
    private double coverageAmount;
    private double premiumAmount;
    private int tenure;

    // Default Constructor
    public Policy() {}

    // Parameterized Constructor
    public Policy(int policyId, String policyName, String policyType, double coverageAmount, double premiumAmount, int tenure) {
        this.policyId = policyId;
        this.policyName = policyName;
        this.policyType = policyType;
        this.coverageAmount = coverageAmount;
        this.premiumAmount = premiumAmount;
        this.tenure = tenure;
    }

    // Getters and Setters
    public int getPolicyId() { return policyId; }
    public void setPolicyId(int policyId) { this.policyId = policyId; }

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public String getPolicyType() { return policyType; }
    public void setPolicyType(String policyType) { this.policyType = policyType; }

    public double getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(double coverageAmount) { this.coverageAmount = coverageAmount; }

    public double getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(double premiumAmount) { this.premiumAmount = premiumAmount; }

    public int getTenure() { return tenure; }
    public void setTenure(int tenure) { this.tenure = tenure; }

    @Override
    public String toString() {
        return "Policy [policyId=" + policyId + ", policyName=" + policyName + ", policyType=" + policyType +
               ", coverageAmount=" + coverageAmount + ", premiumAmount=" + premiumAmount + ", tenure=" + tenure + "]";
    }
}
