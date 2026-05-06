package com.ejemplo.service;

import com.ejemplo.entity.Producto;
import com.ejemplo.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {
    
    @Mock
    private ProductoRepository productoRepository;
    
    @InjectMocks
    private ProductoServiceImpl productoService;
    
    private Producto producto;
    
    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop");
        producto.setDescripcion("Laptop de alto rendimiento");
        producto.setPrecio(999.99);
        producto.setStock(10);
    }
    
    @Test
    void guardar_conProductoValido_retornaProductoGuardado() {
        // Arrange
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre("Mouse");
        nuevoProducto.setDescripcion("Mouse inalámbrico");
        nuevoProducto.setPrecio(25.50);
        nuevoProducto.setStock(50);
        
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        
        // Act
        Producto resultado = productoService.guardar(nuevoProducto);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Laptop", resultado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }
    
    @Test
    void guardar_conProductoNulo_lanzaRuntimeException() {
        // Arrange & Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productoService.guardar(null);
        });
        
        verify(productoRepository, never()).save(any());
    }
    
    @Test
    void buscarPorId_conIdValido_retornaProducto() {
        // Arrange
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        
        // Act
        Optional<Producto> resultado = productoService.buscarPorId(1L);
        
        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Laptop", resultado.get().getNombre());
        verify(productoRepository, times(1)).findById(1L);
    }
    
    @Test
    void buscarPorId_conIdInvalido_lanzaRuntimeException() {
        // Arrange & Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productoService.buscarPorId(null);
        });
        
        verify(productoRepository, never()).findById(anyLong());
    }
    
    @Test
    void buscarPorId_conIdNegativo_lanzaRuntimeException() {
        // Arrange & Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productoService.buscarPorId(-1L);
        });
        
        verify(productoRepository, never()).findById(anyLong());
    }
    
    @Test
    void listarTodos_retornaListaDeProductos() {
        // Arrange
        List<Producto> productos = Arrays.asList(producto);
        when(productoRepository.findAll()).thenReturn(productos);
        
        // Act
        List<Producto> resultado = productoService.listarTodos();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Laptop", resultado.get(0).getNombre());
        verify(productoRepository, times(1)).findAll();
    }
    
    @Test
    void eliminar_conIdValido_eliminaProducto() {
        // Arrange & Act
        productoService.eliminar(1L);
        
        // Assert
        verify(productoRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void eliminar_conIdInvalido_lanzaRuntimeException() {
        // Arrange & Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productoService.eliminar(null);
        });
        
        verify(productoRepository, never()).deleteById(anyLong());
    }
    
    @Test
    void eliminar_conIdNegativo_lanzaRuntimeException() {
        // Arrange & Act & Assert
        assertThrows(RuntimeException.class, () -> {
            productoService.eliminar(-5L);
        });
        
        verify(productoRepository, never()).deleteById(anyLong());
    }
}
