//CRUD

package com.example.projeto_apirest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.aot.generate.ValueCodeGenerator.Delegate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto_apirest.dtos.ProductRecordDto;
import com.example.projeto_apirest.models.ProductModel;
import com.example.projeto_apirest.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    
    //salvar = post
    //listar = get
    //atualizar = push
    //deletar = delete
    
    //criar produtos para a tabela
    @PostMapping("/products")
    public ResponseEntity<ProductModel>saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        //conversao de dto para model
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
    
    //listar todos os prdutos da tabela
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>>getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    //listar um produto especifico da tabela pelo id
    @GetMapping("/products/{id}")
    public ResponseEntity<Object>getOneProducts(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> produt0 = productRepository.findById(id);
        if (produt0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(produt0.get());
            
        }

    //atualizar um produto especifico da tabela pelo id
    @PutMapping("/products/{id}")
    public ResponseEntity<Object>updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto){

        Optional<ProductModel> produt0 = productRepository.findById(id);
        if (produt0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found.");
        }
        var productModel = produt0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
        
    }

    //deletar um produto especifico da tabela pelo id
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object>deleteProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> produt0 = productRepository.findById(id);
        if (produt0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found.");
        }
        productRepository.delete(produt0.get());
        return ResponseEntity.status(HttpStatus.OK).body("product deleted sucessfully.");
    }
    
}
