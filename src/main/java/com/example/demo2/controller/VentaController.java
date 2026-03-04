package com.example.demo2.controller;

import com.example.demo2.model.dto.VentaRequestDTO;
import com.example.demo2.model.entity.Venta;
import com.example.demo2.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> ventas = ventaService.findAll();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        return ventaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Venta> createVenta(@RequestBody VentaRequestDTO ventaRequest) {
        try {
            Venta newVenta = ventaService.createSale(ventaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newVenta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // O un DTO de error más específico
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
