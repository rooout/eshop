package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
        Payment payment = new Payment(paymentMethod, paymentData, paymentOrder);
        payments.add(payment);
        return payment;
    }


    public Payment setStatus(Payment updatedPayment, String paymentStatus) {
        Payment payment = getPayment(updatedPayment.getPaymentId());

        if ((payment.getPaymentStatus()).equals(PaymentStatus.WAITING.getValue())) {
            processPayment(payment, paymentStatus);
            payment.setPaymentStatus(paymentStatus);
        }
        return payment;
    }

    private void processPayment(Payment payment, String paymentStatus) {
        if (paymentStatus.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.getPaymentOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if (paymentStatus.equals(PaymentStatus.REJECTED.getValue())) {
            payment.getPaymentOrder().setStatus(OrderStatus.FAILED.getValue());
        }
    }

    public Payment getPayment(String paymentId) {
        for (Payment payment : payments) {
            if (payment.getPaymentId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    public Iterator<Payment> getAllPayments() {
        return payments.iterator();
    }
}