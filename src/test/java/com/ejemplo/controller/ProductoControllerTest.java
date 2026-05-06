package com.ejemplo.controller;

import com.ejemplo.entity.Producto;
import com.ejemplo.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {
    
    @Mock
    private ProductoService productoService;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Producto producto;
    
    @BeforeEach
    void setUp() {
        ProductoController productoController = new ProductoController();
        productoController.setProductoService(productoService);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
        objectMapper = new ObjectMapper();
        
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop");
        producto.setDescripcion("Laptop de alto rendimiento");
        producto.setPrecio(999.99);
        producto.setStock(10);
    }
    
    @Test
    void listarProductos_retorna200ConLista() throws Exception {
        // Arrange
        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Mouse");
        producto2.setDescripcion("Mouse inalámbrico");
        producto2.setPrecio(25.50);
        producto2.setStock(50);
        
        List<Producto> productos = Arrays.asList(producto, producto2);
        when(productoService.listarTodos()).thenReturn(productos);
        
        // Act & Assert
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Laptop")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Mouse")));
        
        verify(productoService, times(1)).listarTodos();
    }
    
    @Test
    void crearProducto_datosValidos_retorna201() throws Exception {
        // Arrange
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Teclado");
        nuevoProducto.setDescripcion("Teclado mecánico");
        nuevoProducto.setPrecio(75.00);
        nuevoProducto.setStock(30);
        
        Producto productoGuardado = new Producto();
        productoGuardado.setId(3L);
        productoGuardado.setNombre("Teclado");
        productoGuardado.setDescripcion("Teclado mecánico");
        productoGuardado.setPrecio(75.00);
        productoGuardado.setStock(30);
        
        when(productoService.guardar(any(Producto.class))).thenReturn(productoGuardado);
        
        // Act & Assert
        mockMvc.perform(post("/api/productos")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(nuevoProducto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nombre", is("Teclado")))
                .andExpect(jsonPath("$.precio", is(75.00)))
                .andExpect(jsonPath("$.stock", is(30)));
        
        verify(productoService, times(1)).guardar(any(Producto.class));
    }
    
    @Test
    void buscarProducto_noExistente_retorna404() throws Exception {
        // Arrange
        when(productoService.buscarPorId(anyLong())).thenReturn(Optional.empty());
        
        // Act & Assert
        mockMvc.perform(get("/api/productos/999"))
                .andExpect(status().isNotFound());
        
        verify(productoService, times(1)).buscarPorId(999L);
    }
    
    @Test
    void obtenerProducto_existente_retorna200() throws Exception {
        // Arrange
        when(productoService.buscarPorId(1L)).thenReturn(Optional.of(producto));
        
        // Act & Assert
        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Laptop")));
        
        verify(productoService, times(1)).buscarPorId(1L);
    }
    
    @Test
    void actualizarProducto_existente_retorna200() throws Exception {
        // Arrange
        Producto productoActualizado = new Producto();
        productoActualizado.setId(1L);
        productoActualizado.setNombre("Laptop Pro");
        productoActualizado.setDescripcion("Laptop profesional");
        productoActualizado.setPrecio(1499.99);
        productoActualizado.setStock(5);
        
        when(productoService.buscarPorId(1L)).thenReturn(Optional.of(producto));
        when(productoService.guardar(any(Producto.class))).thenReturn(productoActualizado);
        
        // Act & Assert
        mockMvc.perform(put("/api/productos/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productoActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Laptop Pro")))
                .andExpect(jsonPath("$.precio", is(1499.99)));
        
        verify(productoService, times(1)).buscarPorId(1L);
        verify(productoService, times(1)).guardar(any(Producto.class));
    }
    
    @Test
    void actualizarProducto_noExistente_retorna404() throws Exception {
        // Arrange
        when(productoService.buscarPorId(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        mockMvc.perform(put("/api/productos/999")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isNotFound());
        
        verify(productoService, times(1)).buscarPorId(999L);
        verify(productoService, never()).guardar(any());
    }
    
    @Test
    void eliminarProducto_existente_retorna204() throws Exception {
        // Arrange
        when(productoService.buscarPorId(1L)).thenReturn(Optional.of(producto));
        
        // Act & Assert
        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());
        
        verify(productoService, times(1)).buscarPorId(1L);
        verify(productoService, times(1)).eliminar(1L);
    }
    
    @Test
    void eliminarProducto_noExistente_retorna404() throws Exception {
        // Arrange
        when(productoService.buscarPorId(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        mockMvc.perform(delete("/api/productos/999"))
                .andExpect(status().isNotFound());
        
        verify(productoService, times(1)).buscarPorId(999L);
        verify(productoService, never()).eliminar(anyLong());
    }
}
