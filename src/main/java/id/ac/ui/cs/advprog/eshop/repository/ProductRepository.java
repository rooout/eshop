package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository implements IProductRepository {
    private List<Product> products = new ArrayList<>();

    public Product create(Product product) {
        products.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return products.iterator();
    }

    public Product findById(String id) {
        for (Product product : products) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }

        return null;
    }

    public Product update(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(product.getProductId())) {
                products.set(i, product);
                return product;
            }
        }

        return null;
    }

    public void delete(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(id)) {
                products.remove(i);
                break;
            }
        }
    }
}