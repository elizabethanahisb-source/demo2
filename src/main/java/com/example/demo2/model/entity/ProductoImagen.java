package com.example.demo2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "producto_imagenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Long idImagen;

    @Lob // Indica que es un objeto grande, como un BLOB
    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Column(name = "portada", nullable = false)
    private Boolean portada = false; // Por defecto no es portada

    @Column(name = "galeria", nullable = false)
    private Boolean galeria = false; // Por defecto no es de galería
}
