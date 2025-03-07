package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Payment {

    private String paymentId;

    private String paymentMethod;

    private String paymentStatus;

    private Map<String, String> paymentData;

    private Order paymentOrder;

    public Payment(String paymentId, String paymentMethod, String paymentStatus, Map<String, String> paymentData,
                   Order paymentOrder) {
        this.paymentId = paymentId;
        this.setPaymentMethod(paymentMethod);
        this.setPaymentStatus(paymentStatus);
        this.setPaymentData(paymentData);
        this.setPaymentOrder(paymentOrder);
    }

    public Payment(String paymentMethod, Map<String, String> paymentData, Order paymentOrder) {
        this(UUID.randomUUID().toString(), paymentMethod, PaymentStatus.WAITING.getValue(), paymentData, paymentOrder);
    }

    public void setPaymentMethod(String paymentMethod) {
        if (!PaymentMethod.contains(paymentMethod)) {
            throw new IllegalArgumentException();
        }

        this.paymentMethod = paymentMethod;
    }

    public void setPaymentStatus(String paymentStatus) {
        if (!PaymentStatus.contains(paymentStatus)) {
            throw new IllegalArgumentException();
        }

        this.paymentStatus = paymentStatus;
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (paymentData.get("voucherCode") != null) {
            String regex = "^ESHOP(?=(?:.*\\d){8})(?=(?:.*[A-Z]){3})[0-9A-Z]{11}$";
            Pattern pattern = Pattern.compile(regex);

            String voucherCode = paymentData.get("voucherCode");
            Matcher matcher = pattern.matcher(voucherCode);

            if (!matcher.matches()) {
                paymentStatus = PaymentStatus.REJECTED.getValue();
            }
        } else if (paymentData.get("bankName") == null || paymentData.get("referenceCode") == null) {
            paymentStatus = PaymentStatus.REJECTED.getValue();
        }

        this.paymentData = paymentData;
    }

    public void setPaymentOrder(Order paymentOrder) {
        if (paymentOrder == null) {
            throw new IllegalArgumentException();
        }

        this.paymentOrder = paymentOrder;
    }

}