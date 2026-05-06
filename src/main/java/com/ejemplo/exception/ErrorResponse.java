package com.ejemplo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String mensaje;
    private String detalle;
    private int codigoEstado;
}
