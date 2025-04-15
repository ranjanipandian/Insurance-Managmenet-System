package main;

import dao.InsurancePolicyImpl;
import dao.IPolicyManage;
import entity.Client;
import entity.Claim;
import entity.Payment;
import entity.Policy;
import util.DatabaseSetup;
import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        // Call the method to create tables in the database
        DatabaseSetup.createTables(); 

        IPolicyManage policyService = new InsurancePolicyImpl();
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n\n===== WELCOME TO INSURANCE MANAGEMENT SYSTEM =====\n\n");
                System.out.println("1. Create Client");
                System.out.println("2. Create Claim");
                System.out.println("3. Create Payment");
                System.out.println("4. Manage Policy");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                case 1:
                    System.out.println("Enter Client ID:");
                    int clientId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Client Name:");
                    String clientName = scanner.nextLine();
                    System.out.println("Enter Contact Info:");
                    String contactInfo = scanner.nextLine();
                    System.out.println("Enter Policy:");
                    String policy = scanner.nextLine();

                    // Create client object
                    Client newClient = new Client();
                    newClient.setClientName(clientName);
                    newClient.setContactInfo(contactInfo);
                    newClient.setPolicy(policy);

                    // Create client using service
                    dao.InsuranceServiceImpl clientService = new dao.InsuranceServiceImpl();
                    clientService.createClient(newClient);
                    break;

                case 2:
                    System.out.println("Enter Claim ID:");
                    int claimId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Claim Number:");
                    String claimNumber = scanner.nextLine();
                    System.out.println("Enter Date Filed (YYYY-MM-DD):");
                    String dateFiled = scanner.nextLine();
                    System.out.println("Enter Claim Amount:");
                    double claimAmount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Enter Status:");
                    String status = scanner.nextLine();
                    System.out.println("Enter Policy:");
                    String claimPolicy = scanner.nextLine();
                    System.out.println("Enter Client ID for this Claim:");
                    int claimClientId = scanner.nextInt();
                    scanner.nextLine();
                    
                    // Prepare Claim object
                    Claim newClaim = new Claim();
                    newClaim.setClaimNumber(claimNumber);
                    newClaim.setDateFiled(java.sql.Date.valueOf(dateFiled));
                    newClaim.setClaimAmount(claimAmount);
                    newClaim.setStatus(status);
                    newClaim.setPolicy(claimPolicy);
                    newClaim.setClient(new Client(claimClientId, "", "", "")); 
                    // Call createClaim()
                    dao.InsuranceServiceImpl claimService = new dao.InsuranceServiceImpl();
                    claimService.createClaim(newClaim);
                    System.out.println("✅ Claim created successfully!");
                    break;

                case 3:
                    System.out.println("Enter Payment ID:");
                    int paymentId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Payment Date (YYYY-MM-DD):");
                    String dateInput = scanner.next().trim();
                    System.out.println("Enter Payment Amount:");
                    double paymentAmount = scanner.nextDouble();
                    scanner.nextLine(); 
                    System.out.println("Enter Client ID for this Payment:");
                    int paymentClientId = scanner.nextInt();
                    scanner.nextLine();
                    // Create Payment object
                    Payment payment = new Payment();
                    payment.setPaymentId(paymentId);
                    payment.setPaymentDate(java.sql.Date.valueOf(dateInput));
                    payment.setPaymentAmount(paymentAmount);
                    payment.setClient(new Client(paymentClientId, "", "", "")); 
                    
                    // Call the method to insert into DB
                    dao.InsuranceServiceImpl paymentService = new dao.InsuranceServiceImpl();
                    paymentService.createPayment(payment);
                    System.out.println("✅ Payment created successfully!");
                    break;

                    case 4:
                        System.out.println("\n===== Policy Management =====");
                        System.out.println("1. Create Policy");
                        System.out.println("2. View Policy");
                        System.out.println("3. View All Policies");
                        System.out.println("4. Update Policy");
                        System.out.println("5. Delete Policy");
                        System.out.print("Enter choice:");
                        
                        int policyChoice = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
                        
                        switch (policyChoice) {
                            case 1:  // Create Policy
                                System.out.println("Enter Policy Name:");
                                String policyName = scanner.nextLine();
                                System.out.println("Enter Policy Type:");
                                String policyType = scanner.nextLine();
                                System.out.println("Enter Coverage Amount:");
                                double coverage = scanner.nextDouble();
                                System.out.println("Enter Premium Amount:");
                                double premium = scanner.nextDouble();
                                System.out.println("Enter Tenure:");
                                int tenure = scanner.nextInt();

                                Policy newPolicy = new Policy(0, policyName, policyType, coverage, premium, tenure);
                                boolean created = policyService.createPolicy(newPolicy);

                                if (created) {
                                    System.out.println("✅ Policy created successfully!");
                                } else {
                                    System.out.println("❌ Error: Policy creation failed.");
                                }
                                break;

                            case 2:  // View Single Policy
                                System.out.println("Enter Policy ID:");
                                int policyId = scanner.nextInt();
                                Policy foundPolicy = policyService.getPolicy(policyId);
                                
                                if (foundPolicy != null) {
                                    System.out.println("\nPolicy Details: " + foundPolicy);
                                } else {
                                    System.out.println("❌ Policy not found.");
                                }
                                break;

                            case 3:  // View All Policies
                                List<Policy> allPolicies = policyService.getAllPolicies();
                                if (allPolicies.isEmpty()) {
                                    System.out.println("❌ No policies found.");
                                } else {
                                    System.out.println("\n===== All Policies =====");
                                    for (Policy p : allPolicies) {
                                        System.out.println(p);
                                    }
                                }
                                break;

                            case 4:  // Update Policy
                                System.out.println("Enter Policy ID to update:");
                                int updateId = scanner.nextInt();
                                scanner.nextLine();
                                
                                System.out.println("Enter New Policy Name: ");
                                String newName = scanner.nextLine();
                                System.out.println("Enter New Policy Type: ");
                                String newType = scanner.nextLine();
                                System.out.println("Enter New Coverage Amount: ");
                                double newCoverage = scanner.nextDouble();
                                System.out.println("Enter New Premium Amount: ");
                                double newPremium = scanner.nextDouble();
                                System.out.println("Enter New Tenure: ");
                                int newTenure = scanner.nextInt();

                                Policy updatedPolicy = new Policy(updateId, newName, newType, newCoverage, newPremium, newTenure);
                                boolean updated = policyService.updatePolicy(updatedPolicy);

                                if (updated) {
                                    System.out.println("✅ Policy updated successfully!");
                                } else {
                                    System.out.println("❌ Error: Policy update failed.");
                                }
                                break;

                            case 5:  // Delete Policy
                                System.out.println("Enter Policy ID to delete:");
                                int deleteId = scanner.nextInt();
                                boolean deleted = policyService.deletePolicy(deleteId);

                                if (deleted) {
                                    System.out.println("✅ Policy deleted successfully!");
                                } else {
                                    System.out.println("❌ Error: Policy deletion failed.");
                                }
                                break;

                            default:
                                System.out.println("❌ Invalid choice. Please try again.");
                        }
                        break;

                    case 5:
                        System.out.println("Exiting system...");
                        System.exit(0);
                        break;

                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            }
        }
    }
}
