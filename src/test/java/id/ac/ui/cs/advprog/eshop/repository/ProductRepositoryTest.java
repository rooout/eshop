package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private ProductRepository productRepository;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        testProduct = new Product();
        testProduct.setProductId("12345");
        testProduct.setProductName("Test Product");
        testProduct.setProductQuantity(10);

        productRepository.create(testProduct);
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertEquals(2, countProducts(productIterator)); // Harus ada 2 produk
    }

    @Test
    void testFindAllIfEmpty() {
        productRepository = new ProductRepository(); // Reset repository agar kosong
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("d40f9d46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertEquals(3, countProducts(productIterator)); // Harus ada 3 produk (termasuk testProduct)
    }

    @Test
    void testFindById_ProductExists() {
        Product foundProduct = productRepository.findById("12345");
        assertNotNull(foundProduct);
        assertEquals("12345", foundProduct.getProductId());
    }

    @Test
    void testFindById_ProductNotFound() {
        Product foundProduct = productRepository.findById("99999");
        assertNull(foundProduct);
    }

    @Test
    void testUpdateProduct_ProductExists() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("12345");
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Updated Product", result.getProductName());

        Product foundProduct = productRepository.findById("12345");
        assertNotNull(foundProduct);
        assertEquals("Updated Product", foundProduct.getProductName());
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("99999");
        nonExistentProduct.setProductName("Non-existent Product");
        nonExistentProduct.setProductQuantity(5);

        Product result = productRepository.update(nonExistentProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProduct_ProductExists() {
        productRepository.delete("12345");
        assertNull(productRepository.findById("12345"));
        assertEquals(0, countProducts(productRepository.findAll())); // Pastikan produk benar-benar terhapus
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        productRepository.delete("99999"); // Harus tidak error meskipun produk tidak ditemukan
        assertEquals(1, countProducts(productRepository.findAll())); // Produk testProduct masih ada
    }

    private int countProducts(Iterator<Product> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }
}
