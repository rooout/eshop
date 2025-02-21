package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testProduct = new Product();
        testProduct.setProductId("12345");
        testProduct.setProductName("Test Product");
        testProduct.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        // Simulasi repository create
        when(productRepository.create(testProduct)).thenReturn(testProduct);

        Product createdProduct = productService.create(testProduct);

        assertNotNull(createdProduct);
        assertEquals("12345", createdProduct.getProductId());
        assertEquals("Test Product", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());

        verify(productRepository, times(1)).create(testProduct);
    }

    @Test
    void testFindAllProducts() {
        // Simulasi hasil repository.findAll()
        Iterator<Product> productIterator = Arrays.asList(testProduct).iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> products = productService.findAll();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getProductName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Simulasi repository findById
        when(productRepository.findById("12345")).thenReturn(testProduct);

        Product foundProduct = productService.findById("12345");

        assertNotNull(foundProduct);
        assertEquals("12345", foundProduct.getProductId());
        assertEquals("Test Product", foundProduct.getProductName());

        verify(productRepository, times(1)).findById("12345");
    }
}
