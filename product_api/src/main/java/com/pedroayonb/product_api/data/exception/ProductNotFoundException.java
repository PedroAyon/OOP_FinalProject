package com.pedroayonb.product_api.data.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super("Could not find product " + id);
    }
}
