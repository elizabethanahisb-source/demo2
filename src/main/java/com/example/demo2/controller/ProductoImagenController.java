package com.example.demo2.controller;

import com.example.demo2.model.entity.ProductoImagen;
import com.example.demo2.service.ProductoImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductoImagenController {

    @Autowired
    private ProductoImagenService productoImagenService;

    // Endpoint para subir una imagen a un producto
    @PostMapping("/productos/{productId}/imagenes")
    public ResponseEntity<ProductoImagen> uploadImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "portada", defaultValue = "false") boolean portada,
            @RequestParam(value = "galeria", defaultValue = "false") boolean galeria) {
        try {
            ProductoImagen savedImage = productoImagenService.saveImage(productId, file, portada, galeria);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedImage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para obtener una imagen por su ID
    @GetMapping("/imagenes/{imageId}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long imageId) {
        Optional<ProductoImagen> imagenOptional = productoImagenService.findById(imageId);
        if (imagenOptional.isPresent()) {
            ProductoImagen imagen = imagenOptional.get();
            // Asumiendo que todas las imágenes son JPEG o PNG. Podrías necesitar un campo para el tipo de contenido.
            MediaType mediaType = MediaType.IMAGE_JPEG; // Valor por defecto
            // Aquí podrías intentar determinar el tipo de imagen si lo guardaste en la DB
            // Por simplicidad, asumimos JPEG o PNG.
            // if (imagen.getFileName().endsWith(".png")) mediaType = MediaType.IMAGE_PNG;

            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(imagen.getImagen());
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para obtener la imagen de portada de un producto
    @GetMapping("/productos/{productId}/imagen/portada")
    public ResponseEntity<byte[]> getCoverImageByProductId(@PathVariable Long productId) {
        Optional<ProductoImagen> imagenOptional = productoImagenService.findCoverImageByProductId(productId);
        if (imagenOptional.isPresent()) {
            ProductoImagen imagen = imagenOptional.get();
            MediaType mediaType = MediaType.IMAGE_JPEG; // Valor por defecto
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(imagen.getImagen());
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para eliminar una imagen por su ID
    @DeleteMapping("/imagenes/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        if (productoImagenService.findById(imageId).isPresent()) {
            productoImagenService.deleteById(imageId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
