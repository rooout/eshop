package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private final Map<String, Order> paymentOrderMapping = new HashMap<>();

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        PaymentStatus status = (bankName == null || bankName.trim().isEmpty() ||
                                referenceCode == null || referenceCode.trim().isEmpty())
                                ? PaymentStatus.REJECTED : PaymentStatus.SUCCESS;
        
        Payment payment = new Payment(UUID.randomUUID().toString(), method, status.name(), paymentData);
        paymentRepository.save(payment);
        paymentOrderMapping.put(payment.getId(), order);
        
        order.setStatus(status == PaymentStatus.SUCCESS ? "SUCCESS" : "FAILED");
        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        PaymentStatus newStatus = PaymentStatus.valueOf(status);
        payment.setStatus(newStatus);
        
        Order order = paymentOrderMapping.get(payment.getId());
        if (order != null) {
            if (newStatus == PaymentStatus.SUCCESS) {
                order.setStatus("SUCCESS");
            } else if (newStatus == PaymentStatus.REJECTED) {
                order.setStatus("FAILED");
            }
        }
        
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
