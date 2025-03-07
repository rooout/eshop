package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    private Map<String, String> voucherCodePayment = new HashMap<>();

    private Map<String, String> bankPayment = new HashMap<>();

    private List<Product> products;

    private Order order;

    @BeforeEach
    void setUp() {
        voucherCodePayment.put("voucherCode", "ESHOP1234ABC5678");
        bankPayment.put("bankName", "Bank Bambang");
        bankPayment.put("referenceCode", "X100");
        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        this.products.add(product1);
        this.products.add(product2);

        this.order = Order.builder().id("787c1e14-8383-4308-b2d5-f924b9d588b8")
                .products(this.products)
                .orderTime(1708560000L)
                .author("Syauqi")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .build();
    }

    @Test
    void testPaymentIdAutoGenerated() {
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8",
                PaymentMethod.BANK.getValue(), PaymentStatus.WAITING.getValue(), bankPayment, order);
        assertNotNull(payment.getPaymentId());
    }

    @Test
    void testPaymentMethodInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8",
                    "Invalid Payment Method", PaymentStatus.WAITING.getValue(), bankPayment, order);
        });
    }

    @Test
    void testPaymentMethodValid() {
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8",
                PaymentMethod.BANK.getValue(), PaymentStatus.WAITING.getValue(), bankPayment, order);
        assertEquals(PaymentMethod.BANK.getValue(), payment.getPaymentMethod());
    }

    @Test
    void testPaymentEmptyOrder() {
        this.order = null;
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8",
                    PaymentMethod.BANK.getValue(), PaymentStatus.WAITING.getValue(), bankPayment, order);
        });
    }

    @Test
    void testPaymentOrder() {
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.BANK.getValue(),
                PaymentStatus.WAITING.getValue(), bankPayment, order);
        assertEquals(PaymentMethod.BANK.getValue(), payment.getPaymentMethod());
    }

    @Test
    void testPaymentDataEmpty() {
        this.bankPayment.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.BANK.getValue(),
                    PaymentStatus.WAITING.getValue(), bankPayment, order);
        });
    }

    @Test
    void testPaymentData() {
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.BANK.getValue(),
                PaymentStatus.WAITING.getValue(), bankPayment, order);
        assertEquals(bankPayment, payment.getPaymentData());
    }

    @Test
    void testPaymentStatusSuccess() {
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.BANK.getValue(),
                PaymentStatus.WAITING.getValue(), bankPayment, order);
        payment.setPaymentStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getPaymentStatus());
    }

    @Test
    void testPaymentStatusRejected() {
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.WAITING.getValue(), voucherCodePayment, order);
        payment.setPaymentStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getPaymentStatus());
    }

    @Test
    void testPaymentStatusVoucherCodeValid() {
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.WAITING.getValue(), voucherCodePayment, order);
        assertEquals(PaymentStatus.WAITING.getValue(), payment.getPaymentStatus());
    }

    @Test
    void testPaymentStatusVoucherCodeInvalid() {
        voucherCodePayment.put("voucherCode", "ESHOP1234ABC567X");
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.VOUCHER_CODE.getValue(),
                PaymentStatus.WAITING.getValue(), voucherCodePayment, order);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getPaymentStatus());
    }

    @Test
    void testPaymentStatusBankValid() {
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.BANK.getValue(),
                PaymentStatus.WAITING.getValue(), bankPayment, order);
        assertEquals(PaymentStatus.WAITING.getValue(), payment.getPaymentStatus());
    }

    @Test
    void testPaymentStatusBankInvalid() {
        bankPayment.put("bankName", null);
        Payment payment = new Payment("4f95549b-19d5-43d8-9267-6cc37c5ae0a8", PaymentMethod.BANK.getValue(),
                PaymentStatus.WAITING.getValue(), bankPayment, order);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getPaymentStatus());
    }

}