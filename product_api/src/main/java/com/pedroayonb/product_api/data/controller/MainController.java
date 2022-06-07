package com.pedroayonb.product_api.data.controller;

import com.pedroayonb.product_api.data.exception.ProductNotFoundException;
import com.pedroayonb.product_api.domain.model.entities.Product;
import com.pedroayonb.product_api.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/products")
public class MainController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @GetMapping(path = "/search")
    public @ResponseBody Iterable<Product> findProductsByName(@RequestParam String name) {
        return productRepository.findByName(name);
    }

    @PostMapping(path = "/add")
    public @ResponseBody Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setCurrency(newProduct.getCurrency());
                    product.setImageURL(newProduct.getImageURL());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
    }

    @DeleteMapping(path = "/{id}")
    public @ResponseBody String deleteProduct(@PathVariable Long id) {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.deleteById(id);
        return "Deleted";
    }
}
