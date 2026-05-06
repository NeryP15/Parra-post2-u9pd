package com.ejemplo.service;

import com.ejemplo.entity.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto guardar(Producto producto);
    Optional<Producto> buscarPorId(Long id);
    List<Producto> listarTodos();
    void eliminar(Long id);
}
