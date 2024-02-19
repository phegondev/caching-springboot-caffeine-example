package com.techwithden.SpringCacheExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class Controller {

    @Autowired
    private ProductService productService;

    @PostMapping("/save-product")
    Product saveProduct( @RequestBody Product product){
        return productService.saveProduct(product);
    }

    @GetMapping("/get-all-products")
    List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/evict-products-catch")
    public String evictCache(){
        return   productService.evictCache();
    }
}
