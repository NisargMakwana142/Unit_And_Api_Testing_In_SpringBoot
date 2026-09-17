package com.unit.testing.service;


import com.unit.testing.model.Product;
import com.unit.testing.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;
    //stub -> findById(1) -> {name,stock,price}

    @Test
    void shouldReturnProductWhenProductExist(){
        //arrange
        Product product =
                new Product(1L,"Laptop",75000,10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        //Action
        Product actualResult = productService.getProductById(1L);

        //assert
        assertEquals(1L,actualResult.getId());
        assertEquals("Laptop",actualResult.getName());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist(){

        //arrange
        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        //action
        RuntimeException exception =
                assertThrows(RuntimeException.class, () ->
                        productService.getProductById(99L)
                );

        //assert
        assertEquals("Product not found",
                exception.getMessage()
        );

        verify(productRepository).findById(99L);
    }

    @Test
    void shouldCreateProductWhenNameIsUnique(){
        //arrange
        Product request =
                new Product(null, "Keyboard", 200,5);

        Product savedProduct =
                new Product(10L, "Keyboard", 200, 5);

        when(productRepository.existsByName("Keyboard"))
                .thenReturn(false);

        when(productRepository.save(request))
                .thenReturn(savedProduct);

        //action
        Product result =
                productService.addProduct(request);

        //assert
        assertEquals(10L,result.getId());
        assertEquals("Keyboard",result.getName());

        verify(productRepository).existsByName("Keyboard");

        verify(productRepository).save(request);
    }

}
