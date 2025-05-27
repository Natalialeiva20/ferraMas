package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Producto;
import com.ferramas.gestion.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/productos/")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String id) {
        return productoRepository.findById(id)
                .map(producto -> ResponseEntity.ok().body(producto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String id, @RequestBody Producto productoDetails) {
        return productoRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoDetails.getNombre());
                    producto.setPrecio(productoDetails.getPrecio());
                    producto.setStockminimo(productoDetails.getStockminimo());
                    producto.setIdcategoria(productoDetails.getIdcategoria());
                    producto.setIdsede(productoDetails.getIdsede());
                    return ResponseEntity.ok(productoRepository.save(producto));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String id) {
        return productoRepository.findById(id)
                .map(producto -> {
                    productoRepository.delete(producto);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{idCategoria}")
    public List<Producto> obtenerProductosPorCategoria(@PathVariable int idCategoria) {
        return productoRepository.findByIdcategoria(idCategoria);
    }

    @GetMapping("/sede/{idSede}")
    public List<Producto> obtenerProductosPorSede(@PathVariable int idSede) {
        return productoRepository.findByIdsede(idSede);
    }
}