package com.example.demo2.repository;

import com.example.demo2.model.entity.Producto;
import com.example.demo2.model.entity.ProductoImagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoImagenRepository extends JpaRepository<ProductoImagen, Long> {
    // Busca una imagen de producto por el producto y si es de portada
    Optional<ProductoImagen> findByProductoAndPortada(Producto producto, boolean portada);

    // Busca imágenes de producto por el producto y si son de galería
    List<ProductoImagen> findByProductoAndGaleria(Producto producto, boolean galeria);
}
