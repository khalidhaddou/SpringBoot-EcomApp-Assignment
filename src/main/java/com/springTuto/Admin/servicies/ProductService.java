package com.springTuto.Admin.servicies;

import com.springTuto.Admin.dto.ProductDto;
import com.springTuto.Admin.models.Product;
import com.springTuto.Admin.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
   public Product addProduct(ProductDto productDto)  {
       String image = null;
       try {
           image = Base64.getEncoder().encodeToString(productDto.getImageFile().getBytes());
       } catch (IOException e) {
           e.printStackTrace();
       }
       Product product = new Product(productDto.getName(),productDto.getDescription(),
                productDto.getPrice(),productDto.getStock(),productDto.getReduction(),
                image,productDto.getCategory());
        return productRepository.save(product);
   }
    public Product updateProduct(Long id ,ProductDto productDto)  {
        String image = null;
        Product product = this.getProductById(id);
       if(!productDto.getImageFile().getOriginalFilename().equals("") ){
           try {
               image = Base64.getEncoder().encodeToString(productDto.getImageFile().getBytes());
               product.setImage(image);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setReduction(productDto.getReduction());
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product getProductByName(String name){
        return productRepository.findByName(name);
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
    public List<Product> getProductsLike(String keyWord){
        return productRepository.findByNameContaining(keyWord);
    }

    public List<Product> getProductsByPrice(Double price){
        return productRepository.findByPrice(price);
    }
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
