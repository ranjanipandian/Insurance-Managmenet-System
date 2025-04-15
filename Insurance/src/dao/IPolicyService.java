package dao;

import entity.Client;
import entity.Claim;
import entity.Payment;

public interface IPolicyService {
    void createClient(Client client);
    Client getClient(int clientId);
    void createClaim(Claim claim);
    Claim getClaim(int claimId);
    void createPayment(Payment payment);
}
