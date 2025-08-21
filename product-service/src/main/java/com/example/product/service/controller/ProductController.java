package com.example.product.service.controller;

import com.example.product.service.dto.ProductRequest;
import com.example.product.service.dto.ProductResponse;
import com.example.product.service.entity.Product;
import com.example.product.service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody @Valid ProductRequest req) {


        Product saved= productService.createProduct(req);

        return toResponse(saved);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(@PathVariable Long id, @RequestBody @Valid ProductRequest req) {




        Product saved= productService.updateProduct(id, req);
        return toResponse(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);

    }

    @GetMapping
    public java.util.List<ProductResponse> getProducts() {
        return productService.findAll().stream().map(this::toResponse).toList();
        //her bir product ProductResponse'a dönüştürülüyor.Veri Product geliyor ancak Response geri döndürülmesi lazım
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        Product p= productService.findById(id);
        return toResponse(p);
    }
    @PutMapping("/{id}/decrease")
    public ProductResponse decrease(@PathVariable Long id, @RequestParam int qty) {
        var p= productService.decreaseStock(id, qty);
        return toResponse(p);
    }
    @PutMapping("/{id}/increase")
    public ProductResponse increase(@PathVariable Long id, @RequestParam int qty) {
        var p= productService.increaseStock(id, qty);
        return toResponse(p);
    }

    private ProductResponse toResponse(Product p) { //Tekrarı azaltmak için yazıldı
        return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStock());
    }
}
