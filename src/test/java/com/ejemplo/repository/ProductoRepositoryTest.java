package com.ejemplo.repository;

import com.ejemplo.entity.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductoRepositoryTest {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    private Producto producto;
    
    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setNombre("Laptop");
        producto.setDescripcion("Laptop de alto rendimiento");
        producto.setPrecio(999.99);
        producto.setStock(10);
    }
    
    @Test
    void save_asignaIdAutomaticamente() {
        // Arrange - el producto no tiene ID
        assertNull(producto.getId(), "El ID debe ser nulo antes de guardar");
        
        // Act - guardamos el producto
        Producto productoGuardado = productoRepository.save(producto);
        
        // Assert - verificamos que se asignó ID automáticamente
        assertNotNull(productoGuardado.getId(), "El ID debe asignarse automáticamente");
        assertTrue(productoGuardado.getId() > 0, "El ID debe ser positivo");
        assertEquals("Laptop", productoGuardado.getNombre());
        assertEquals(999.99, productoGuardado.getPrecio());
    }
    
    @Test
    void findById_existente_retornaProducto() {
        // Arrange - guardamos un producto
        Producto productoGuardado = productoRepository.save(producto);
        Long idGuardado = productoGuardado.getId();
        
        // Act - buscamos el producto por ID
        Optional<Producto> productoEncontrado = productoRepository.findById(idGuardado);
        
        // Assert - verificamos que se encontró
        assertTrue(productoEncontrado.isPresent(), "El producto debe existir");
        assertEquals("Laptop", productoEncontrado.get().getNombre());
        assertEquals(999.99, productoEncontrado.get().getPrecio());
        assertEquals(10, productoEncontrado.get().getStock());
    }
    
    @Test
    void findAll_retornaListaCompleta() {
        // Arrange - guardamos múltiples productos
        Producto producto1 = new Producto();
        producto1.setNombre("Mouse");
        producto1.setDescripcion("Mouse inalámbrico");
        producto1.setPrecio(25.50);
        producto1.setStock(50);
        
        Producto producto2 = new Producto();
        producto2.setNombre("Teclado");
        producto2.setDescripcion("Teclado mecánico");
        producto2.setPrecio(75.00);
        producto2.setStock(30);
        
        productoRepository.save(producto);
        productoRepository.save(producto1);
        productoRepository.save(producto2);
        
        // Act - obtenemos todos los productos
        List<Producto> productos = productoRepository.findAll();
        
        // Assert - verificamos que se devolvieron todos
        assertNotNull(productos, "La lista no debe ser nula");
        assertEquals(3, productos.size(), "Debe haber 3 productos");
        assertTrue(productos.stream().anyMatch(p -> p.getNombre().equals("Laptop")));
        assertTrue(productos.stream().anyMatch(p -> p.getNombre().equals("Mouse")));
        assertTrue(productos.stream().anyMatch(p -> p.getNombre().equals("Teclado")));
    }
    
    @Test
    void deleteById_eliminaProducto() {
        // Arrange - guardamos un producto
        Producto productoGuardado = productoRepository.save(producto);
        Long idGuardado = productoGuardado.getId();
        
        // Verificar que existe
        assertTrue(productoRepository.findById(idGuardado).isPresent(), "El producto debe existir antes de eliminar");
        
        // Act - eliminamos el producto
        productoRepository.deleteById(idGuardado);
        
        // Assert - verificamos que fue eliminado
        Optional<Producto> productoEliminado = productoRepository.findById(idGuardado);
        assertFalse(productoEliminado.isPresent(), "El producto debe haber sido eliminado");
    }
}
