package com.example.demo2.service;

import com.example.demo2.model.dto.SaleItemRequestDTO;
import com.example.demo2.model.dto.VentaRequestDTO;
import com.example.demo2.model.entity.Producto;
import com.example.demo2.model.entity.SaleItem;
import com.example.demo2.model.entity.Venta;
import com.example.demo2.repository.ProductoRepository;
import com.example.demo2.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<Venta> findAll() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> findById(Long id) {
        return ventaRepository.findById(id);
    }

    @Transactional
    public Venta createSale(VentaRequestDTO ventaRequest) {
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDateTime.now());
        List<SaleItem> saleItems = new ArrayList<>();
        double totalVenta = 0.0;

        for (SaleItemRequestDTO itemRequest : ventaRequest.getItems()) {
            Optional<Producto> productoOptional = productoRepository.findById(itemRequest.getProductoId());
            if (productoOptional.isEmpty()) {
                throw new IllegalArgumentException("Producto con ID " + itemRequest.getProductoId() + " no encontrado.");
            }
            Producto producto = productoOptional.get();

            if (producto.getStock() < itemRequest.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Actualizar stock
            producto.setStock(producto.getStock() - itemRequest.getCantidad());
            productoRepository.save(producto); // Guardar el producto con el stock actualizado

            SaleItem saleItem = new SaleItem();
            saleItem.setProducto(producto);
            saleItem.setCantidad(itemRequest.getCantidad());
            // Considerar el descuento si existe
            double precioUnitarioConDescuento = producto.getPrecio() * (1 - (producto.getDescuento() / 100));
            saleItem.setPrecioUnitario(precioUnitarioConDescuento);
            saleItem.setVenta(venta); // Se establecerá la relación bidireccional
            saleItems.add(saleItem);

            totalVenta += precioUnitarioConDescuento * itemRequest.getCantidad();
        }

        venta.setItemsVenta(saleItems);
        venta.setTotal(totalVenta);

        return ventaRepository.save(venta);
    }
}
