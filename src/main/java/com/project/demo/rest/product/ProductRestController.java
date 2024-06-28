package com.project.demo.rest.product;

import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductRestController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public Product createProduct(@RequestBody Product product) {
        Optional<Category> optionalRole = categoryRepository.findByName((product.getCategory().getName()));
        if (optionalRole.isPresent()) {
            Category category = optionalRole.get();
            product.setCategory(category);
        } else {
            Category category = new Category();
            category.setName(product.getCategory().getName());
            category.setDescription(product.getCategory().getDescription());
            categoryRepository.save(category);
        }
        return productRepository.save(product);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public Product updateProduct(@RequestBody Product product) {
        Optional<Category> optionalRole = categoryRepository.findByName((product.getCategory().getName()));
        if (optionalRole.isPresent()) {
            Category category = optionalRole.get();
            product.setCategory(category);
        } else {
            Category category = new Category();
            category.setName(product.getCategory().getName());
            category.setDescription(product.getCategory().getDescription());
            categoryRepository.save(category);
        }
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN_ROLE')")
    public void deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}
