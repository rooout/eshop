package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    private Order mockOrder;

    private Map<String, String> voucherCodePayment = new HashMap<>();

    private Map<String, String> bankPayment = new HashMap<>();

    private static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);
        products.add(product1);
        products.add(product2);
        return products;
    }

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        voucherCodePayment.put("voucherCode", "ESHOP1234ABC5678");
        bankPayment.put("bankName", "Bank Bambang");
        bankPayment.put("referenceCode", "X100");

        mockOrder = Order.builder().id("787c1e14-8383-4308-b2d5-f924b9d588b8")
                .products(getProducts())
                .orderTime(1708560000L)
                .author("Syauqi")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .build();
    }

    @Test
    void testAddPayment() {
        Payment payment = paymentRepository.addPayment(PaymentMethod.VOUCHER_CODE.getValue(), voucherCodePayment, mockOrder);
        assertNotNull(payment);
    }

    @Test
    void testAddPaymentThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = paymentRepository.addPayment(null, null, null);
        });
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = paymentRepository.addPayment(PaymentMethod.VOUCHER_CODE.getValue(),
                voucherCodePayment, mockOrder);
        String newStatus = PaymentStatus.SUCCESS.getValue();

        Payment updatedPayment = paymentRepository.setStatus(payment, newStatus);
        assertEquals(newStatus, updatedPayment.getPaymentStatus());
        assertEquals(newStatus, mockOrder.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = paymentRepository.addPayment(PaymentMethod.VOUCHER_CODE.getValue(),
                voucherCodePayment, mockOrder);
        String newStatus = PaymentStatus.REJECTED.getValue();

        Payment updatedPayment = paymentRepository.setStatus(payment, newStatus);
        assertEquals(newStatus, updatedPayment.getPaymentStatus());
        assertEquals(OrderStatus.FAILED.getValue(), mockOrder.getStatus());
    }


    @Test
    void testSetStatusSuccessToRejected() {
        Payment payment = paymentRepository.addPayment(PaymentMethod.VOUCHER_CODE.getValue(),
                voucherCodePayment, mockOrder);
        String initialStatus = PaymentStatus.SUCCESS.getValue();
        payment.setPaymentStatus(initialStatus);

        String newStatus = PaymentStatus.REJECTED.getValue();
        Payment updatedPayment = paymentRepository.setStatus(payment, newStatus);
        assertEquals(initialStatus, updatedPayment.getPaymentStatus());
    }

    @Test
    void testSetStatusRejectedToSuccess() {
        Payment payment = paymentRepository.addPayment(PaymentMethod.VOUCHER_CODE.getValue(),
                voucherCodePayment, mockOrder);
        String initialStatus = PaymentStatus.REJECTED.getValue();
        payment.setPaymentStatus(initialStatus);

        String newStatus = PaymentStatus.SUCCESS.getValue();
        Payment updatedPayment = paymentRepository.setStatus(payment, newStatus);
        assertEquals(initialStatus, updatedPayment.getPaymentStatus());
    }

    @Test
    void testGetPaymentFound() {
        Payment payment1 = paymentRepository.addPayment(PaymentMethod.VOUCHER_CODE.getValue(),
                voucherCodePayment, mockOrder);
        Payment payment2 = paymentRepository.addPayment(PaymentMethod.BANK.getValue(),
                bankPayment, mockOrder);

        Payment retrievedPayment = paymentRepository.getPayment(payment2.getPaymentId());
        assertEquals(payment2.getPaymentId(), retrievedPayment.getPaymentId());
    }

    @Test
    void testGetPaymentNotFound() {
        paymentRepository.addPayment(PaymentMethod.VOUCHER_CODE.getValue(), voucherCodePayment, mockOrder);
        paymentRepository.addPayment(PaymentMethod.BANK.getValue(), bankPayment, mockOrder);

        Payment retrievedPayment = paymentRepository.getPayment("00000000-0000-0000-0000-000000000000");
        assertNull(retrievedPayment);
    }

    @Test
    void testGetAllPaymentsNotEmpty() {
        paymentRepository.addPayment(PaymentMethod.VOUCHER_CODE.getValue(), voucherCodePayment, mockOrder);
        paymentRepository.addPayment(PaymentMethod.BANK.getValue(), bankPayment, mockOrder);

        Iterator<Payment> paymentsIterator = paymentRepository.getAllPayments();
        assertNotNull(paymentsIterator);
        assertTrue(paymentsIterator.hasNext());
    }

    @Test
    void testGetAllPaymentsEmpty() {
        Iterator<Payment> paymentsIterator = paymentRepository.getAllPayments();
        assertFalse(paymentsIterator.hasNext());
    }

}