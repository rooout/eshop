package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testProduct = new Product();
        testProduct.setProductId("test-id");
        testProduct.setProductName("Test Product");
        testProduct.setProductQuantity(10);
    }

    @Test
    @DisplayName("Test: Update product successfully")
    void testUpdateProduct_Success() {
        when(productRepository.update(any(Product.class))).thenReturn(testProduct);
        testProduct.setProductName("Updated Product");

        Product updatedProduct = productService.update(testProduct);
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getProductName());

        verify(productRepository, times(1)).update(testProduct);
    }

    @Test
    @DisplayName("Test: Update product failure when product not found")
    void testUpdateProduct_Failure_ProductNotFound() {
        when(productRepository.update(any(Product.class))).thenReturn(null);
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("invalid-id");

        Product result = productService.update(nonExistentProduct);
        assertNull(result);

        verify(productRepository, times(1)).update(nonExistentProduct);
    }

    @Test
    @DisplayName("Test: Delete product successfully")
    void testDeleteProduct_Success() {
        doNothing().when(productRepository).delete(testProduct.getProductId());
        assertDoesNotThrow(() -> productService.delete(testProduct.getProductId()));

        verify(productRepository, times(1)).delete(testProduct.getProductId());
    }

    @Test
    @DisplayName("Test: Delete product failure when product not found")
    void testDeleteProduct_Failure_ProductNotFound() {
        doNothing().when(productRepository).delete("invalid-id");
        assertDoesNotThrow(() -> productService.delete("invalid-id"));

        verify(productRepository, times(1)).delete("invalid-id");
    }
}
