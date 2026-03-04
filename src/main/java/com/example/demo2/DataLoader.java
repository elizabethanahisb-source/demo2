package com.example.demo2;

import com.example.demo2.model.entity.Categoria;
import com.example.demo2.model.entity.Marca;
import com.example.demo2.model.entity.Producto;
import com.example.demo2.repository.CategoriaRepository;
import com.example.demo2.repository.MarcaRepository;
import com.example.demo2.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ProductoRepository productoRepository;

    public DataLoader(CategoriaRepository categoriaRepository, MarcaRepository marcaRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.marcaRepository = marcaRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Crear categorías
        Categoria electronicos = new Categoria(null, "Electrónicos", "Dispositivos electrónicos de consumo");
        Categoria ropa = new Categoria(null, "Ropa", "Prendas de vestir y accesorios");
        Categoria hogar = new Categoria(null, "Hogar", "Artículos para el hogar");

        categoriaRepository.saveAll(Arrays.asList(electronicos, ropa, hogar));

        // Crear marcas
        Marca samsung = new Marca(null, "Samsung");
        Marca apple = new Marca(null, "Apple");
        Marca nike = new Marca(null, "Nike");
        Marca lg = new Marca(null, "LG");

        marcaRepository.saveAll(Arrays.asList(samsung, apple, nike, lg));

        // Crear productos
        Producto tvSamsung = new Producto(null, "Smart TV Samsung 55\"", "Televisor 4K UHD con SmartThings", 799.99, 50, 10.0, 4.5, LocalDateTime.now(), true, samsung, electronicos);
        Producto iphone15 = new Producto(null, "iPhone 15 Pro", "Smartphone de última generación", 1199.00, 30, 0.0, 4.8, LocalDateTime.now(), true, apple, electronicos);
        Producto zapatillasNike = new Producto(null, "Zapatillas Nike Air Max", "Calzado deportivo cómodo y ligero", 120.00, 100, 5.0, 4.2, LocalDateTime.now(), true, nike, ropa);
        Producto lavadoraLg = new Producto(null, "Lavadora LG 12kg", "Lavadora de carga frontal con tecnología AI DD", 550.00, 20, 0.0, 4.0, LocalDateTime.now(), true, lg, hogar);

        productoRepository.saveAll(Arrays.asList(tvSamsung, iphone15, zapatillasNike, lavadoraLg));

        System.out.println("Datos iniciales cargados.");
    }
}
