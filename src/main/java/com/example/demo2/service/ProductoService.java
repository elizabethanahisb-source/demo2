package com.example.demo2.service;

import com.example.demo2.model.dto.ProductoResponseDTO;
import com.example.demo2.model.entity.Producto;
import com.example.demo2.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoResponseDTO> listarTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ProductoResponseDTO convertToDto(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setIdProducto(producto.getIdProducto());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStock(producto.getStock());
        dto.setDescuento(producto.getDescuento());
        dto.setValoracion(producto.getValoracion());
        dto.setFechaIngreso(producto.getFechaIngreso());
        dto.setEstado(producto.getEstado());
        dto.setMarcaNombre(producto.getMarca() != null ? producto.getMarca().getNombre() : null);
        dto.setCategoriaNombre(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null);
        // Por ahora, la URL de la imagen de portada es un placeholder.
        // Si se implementa un servicio de imágenes, esto debería obtener la URL real.
        dto.setImagenPortadaUrl("/api/productos/" + producto.getIdProducto() + "/imagen/portada");
        return dto;
    }
}
