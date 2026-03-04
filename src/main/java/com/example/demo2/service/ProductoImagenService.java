package com.example.demo2.service;

import com.example.demo2.model.entity.Producto;
import com.example.demo2.model.entity.ProductoImagen;
import com.example.demo2.repository.ProductoImagenRepository;
import com.example.demo2.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoImagenService {

    @Autowired
    private ProductoImagenRepository productoImagenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public ProductoImagen saveImage(Long productoId, MultipartFile file, boolean isCover, boolean isGallery) throws IOException {
        Optional<Producto> productoOptional = productoRepository.findById(productoId);
        if (productoOptional.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado con ID: " + productoId);
        }

        ProductoImagen productoImagen = new ProductoImagen();
        productoImagen.setProducto(productoOptional.get());
        productoImagen.setImagen(file.getBytes());
        productoImagen.setPortada(isCover);
        productoImagen.setGaleria(isGallery);

        // Si es una imagen de portada, asegurarse de que solo haya una por producto
        if (isCover) {
            productoImagenRepository.findByProductoAndPortada(productoOptional.get(), true)
                    .ifPresent(existingCover -> {
                        existingCover.setPortada(false);
                        productoImagenRepository.save(existingCover);
                    });
        }

        return productoImagenRepository.save(productoImagen);
    }

    public Optional<ProductoImagen> findById(Long id) {
        return productoImagenRepository.findById(id);
    }

    public Optional<ProductoImagen> findCoverImageByProductId(Long productoId) {
        Optional<Producto> productoOptional = productoRepository.findById(productoId);
        if (productoOptional.isEmpty()) {
            return Optional.empty();
        }
        return productoImagenRepository.findByProductoAndPortada(productoOptional.get(), true);
    }

    public List<ProductoImagen> findGalleryImagesByProductId(Long productoId) {
        Optional<Producto> productoOptional = productoRepository.findById(productoId);
        if (productoOptional.isEmpty()) {
            return List.of(); // Retorna una lista vacía si el producto no existe
        }
        return productoImagenRepository.findByProductoAndGaleria(productoOptional.get(), true);
    }

    public void deleteById(Long id) {
        productoImagenRepository.deleteById(id);
    }
}
