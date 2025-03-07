package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {

    private String paymentId;

    private String paymentMethod;

    private String paymentStatus;

    private Map<String, String> paymentData;

    private Order paymentOrder;

    public Payment(String paymentId, String paymentMethod, String paymentStatus, Map<String, String> paymentData,
                   Order paymentOrder) {

    }

    public Payment(String paymentMethod, Map<String, String> paymentData, Order paymentOrder) {

    }

}