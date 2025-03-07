package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order mockOrder;

    private Payment mockPayment;

    private Map<String, String> voucherCodePayment;

    private Map<String, String> bankPayment;

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
        mockOrder = Order.builder().id("787c1e14-8383-4308-b2d5-f924b9d588b8")
                .products(getProducts())
                .orderTime(1708560000L)
                .author("Syauqi")
                .status(OrderStatus.WAITING_PAYMENT.getValue())
                .build();

        voucherCodePayment = new HashMap<>();
        voucherCodePayment.put("voucherCode", "ESHOP1234ABC5678");

        bankPayment = new HashMap<>();
        bankPayment.put("bankName", "Bank Bambang");
        bankPayment.put("referenceCode", "X100");

        mockPayment = new Payment(PaymentMethod.VOUCHER_CODE.getValue(), voucherCodePayment, mockOrder);
    }

    @Test
    void testAddPaymentVoucherCode() {
        when(paymentRepository.addPayment(eq(PaymentMethod.VOUCHER_CODE.getValue()), anyMap(), any(Order.class)))
                .thenReturn(mockPayment);

        Payment payment = paymentService.addPayment(mockOrder, PaymentMethod.VOUCHER_CODE.getValue(), voucherCodePayment);

        assertNotNull(payment);
        assertEquals(PaymentMethod.VOUCHER_CODE.getValue(), payment.getPaymentMethod());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        verify(paymentRepository, times(1))
                .addPayment(eq(PaymentMethod.VOUCHER_CODE.getValue()), anyMap(), any(Order.class));
    }

    @Test
    void testAddPaymentBank() {
        Payment bankMockPayment = new Payment(PaymentMethod.BANK.getValue(), bankPayment, mockOrder);
        when(paymentRepository.addPayment(eq(PaymentMethod.BANK.getValue()), anyMap(), any(Order.class))).thenReturn(bankMockPayment);

        Payment payment = paymentService.addPayment(mockOrder, PaymentMethod.BANK.getValue(), bankPayment);

        assertNotNull(payment);
        assertEquals(PaymentMethod.BANK.getValue(), payment.getPaymentMethod());
        assertEquals("Bank Bambang", payment.getPaymentData().get("bankName"));
        assertEquals("X100", payment.getPaymentData().get("referenceCode"));
        verify(paymentRepository, times(1)).addPayment(eq(PaymentMethod.BANK.getValue()), anyMap(), any(Order.class));
    }

    @Test
    void testSetStatus() {
        when(paymentRepository.setStatus(any(Payment.class), anyString())).thenReturn(mockPayment);

        Payment updatedPayment = paymentService.setStatus(mockPayment, PaymentStatus.SUCCESS.getValue());

        assertNotNull(updatedPayment);
        assertEquals(mockPayment, updatedPayment);
        verify(paymentRepository, times(1)).setStatus(any(Payment.class), anyString());
    }

    @Test
    void testGetPayment() {
        when(paymentRepository.getPayment(anyString())).thenReturn(mockPayment);

        Payment retrievedPayment = paymentService.getPayment("00000000-0000-0000-0000-000000000000");

        assertNotNull(retrievedPayment);
        assertEquals(mockPayment, retrievedPayment);
        verify(paymentRepository, times(1)).getPayment(anyString());
    }

    @Test
    void testGetAllPayments() {
        List<Payment> paymentList = Arrays.asList(mockPayment, mockPayment);
        Iterator<Payment> paymentIterator = paymentList.iterator();
        when(paymentRepository.getAllPayments()).thenReturn(paymentIterator);

        List<Payment> retrievedPayments = paymentService.getAllPayments();

        assertNotNull(retrievedPayments);
        assertEquals(2, retrievedPayments.size());
        verify(paymentRepository, times(1)).getAllPayments();
    }

}