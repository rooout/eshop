package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {

    private final List<Payment> payments = new ArrayList<>();

    public Payment addPayment(String paymentMethod, Map<String, String> paymentData, Order paymentOrder) {
        return null;
    }

    public Payment setStatus(Payment updatedPayment, String paymentStatus) {
        return null;
    }

    private void processPayment(Payment payment, String paymentStatus) {
    }

    public Payment getPayment(String paymentId) {
        return null;
    }

    public Iterator<Payment> getAllPayments() {
        return null;
    }

}