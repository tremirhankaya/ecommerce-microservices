package com.example.product.service.controller;

import com.example.product.service.dto.ProductRequest;
import com.example.product.service.dto.ProductResponse;
import com.example.product.service.model.Product;
import com.example.product.service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/api/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody @Valid ProductRequest req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());


        Product saved= productService.createProduct(product);

        return toResponse(saved);
    }

    @PutMapping("/api/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(@PathVariable Long id, @RequestBody @Valid ProductRequest req) {
        Product product = new Product();
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setStock(req.getStock());

        Product saved= productService.updateProduct(id, product);
        return toResponse(saved);
    }

    @DeleteMapping("/api/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);

    }

    @GetMapping("/api/products")
    public java.util.List<ProductResponse> getProducts() {
        return productService.findAll().stream().map(this::toResponse).toList();
        //her bir product ProductResponse'a dönüştürülüyor.Veri Product geliyor ancak Response geri döndürülmesi lazım
    }

    @GetMapping("/api/products/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        Product p= productService.findById(id);
        return toResponse(p);
    }


    private ProductResponse toResponse(Product p) { //Tekrarı azaltmak için yazıldı
        return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStock());
    }
}
