package com.techwithden.SpringCacheExample;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;
    Product saveProduct(Product product){
        return productRepo.save(product);
    }

    List<Product> getAllProducts(){
        if (productCache.getIfPresent("products") != null){
            System.out.println("WE are getting products from catche");
            return productCache.getIfPresent("products");
        }else{
            List<Product> productList =  productRepo.findAll();
            System.out.println("WE are getting products directly from database");
            productCache.put("products", productList);
            System.out.println("CATCH SIZE IS:" + productCache.estimatedSize());
            return productList;
        }

    }


    private Cache<String ,List<Product>> productCache;
    @PostConstruct
    private void initCache() {
        productCache = Caffeine.newBuilder()
                .maximumSize(100)
                .build();
    }

    public String evictCache() {
        System.out.println("the cache size before eviction is " + productCache.estimatedSize());
        productCache.invalidate("products");
//        productCache.invalidateAll();
        System.out.println("the cache size after eviction is " + productCache.estimatedSize());

        return "Products Cache is evicted Successfully";
    }
}
