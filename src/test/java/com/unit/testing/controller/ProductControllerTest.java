package com.unit.testing.controller;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.unit.testing.model.Product;
import com.unit.testing.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private tools.jackson.databind.ObjectMapper objectMapper;


    // POST /products

    @Test
    void shouldAddProduct() throws Exception {

        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(75000);
        product.setQuantity(10);

        when(productService.addProduct(any(Product.class)))
                .thenReturn(product);

        // Act + Assert
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(75000))
                .andExpect(jsonPath("$.quantity").value(10));

        // Verify
        verify(productService).addProduct(any(Product.class));
    }


    // GET /products

    @Test
    void shouldGetAllProducts() throws Exception {

        // Arrange
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(75000);
        product1.setQuantity(10);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
        product2.setPrice(1000);
        product2.setQuantity(25);

        when(productService.getAllProducts())
                .thenReturn(List.of(product1, product2));

        // Act + Assert
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Mouse"));

        // Verify
        verify(productService).getAllProducts();
    }



    // GET /products/{id}

    @Test
    void shouldGetProductById() throws Exception {

        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(75000);
        product.setQuantity(10);

        when(productService.getProductById(1L))
                .thenReturn(product);

        // Act + Assert
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(75000))
                .andExpect(jsonPath("$.quantity").value(10));

        // Verify
        verify(productService).getProductById(1L);
    }



}
