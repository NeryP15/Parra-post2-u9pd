package com.ejemplo.service;

import com.ejemplo.entity.Producto;
import com.ejemplo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    public Producto guardar(Producto producto) {
        if (producto == null) {
            throw new RuntimeException("El producto no puede ser nulo");
        }
        return productoRepository.save(producto);
    }
    
    @Override
    public Optional<Producto> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID debe ser válido");
        }
        return productoRepository.findById(id);
    }
    
    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }
    
    @Override
    public void eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new RuntimeException("El ID debe ser válido");
        }
        productoRepository.deleteById(id);
    }
}
