package com.example.demo2.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Double descuento;
    private Double valoracion;
    private LocalDateTime fechaIngreso;
    private Boolean estado;
    private String marcaNombre;
    private String categoriaNombre;
    private String imagenPortadaUrl; // Placeholder para la URL de la imagen de portada
}
