package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Categoria;
import com.ferramas.gestion.entity.Producto;
import com.ferramas.gestion.entity.Sede;
import com.ferramas.gestion.repository.ProductoRepository;
import com.ferramas.gestion.repository.CategoriaRepository;
import com.ferramas.gestion.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/productos/")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{idproducto}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String idproducto) {
        return productoRepository.findById(idproducto)
                .map(producto -> ResponseEntity.ok().body(producto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // @PostMapping
    // public Producto crearProducto(@RequestBody Producto producto) {
    // return productoRepository.save(producto);
    // }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Map<String, Object> productoData) {
        Producto producto = new Producto();
        producto.setIdproducto((String) productoData.get("idproducto"));
        producto.setNombre((String) productoData.get("nombre"));
        producto.setPrecio((Integer) productoData.get("precio"));
        producto.setStockminimo((Integer) productoData.get("stockminimo"));

        Integer idcategoria = (Integer) productoData.get("idcategoria");
        if (idcategoria != null) {
            Categoria categoria = categoriaRepository.findById(idcategoria).orElse(null);
            producto.setCategoria(categoria);
        }

        Integer idsede = (Integer) productoData.get("idsede");
        if (idsede != null) {
            Sede sede = sedeRepository.findById(idsede).orElse(null);
            producto.setSede(sede);
        }

        return ResponseEntity.ok(productoRepository.save(producto));
    }

    @PutMapping("/{idproducto}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable String idproducto,
            @RequestBody Producto productoDetails) {
        return productoRepository.findById(idproducto)
                .map(producto -> {
                    producto.setNombre(productoDetails.getNombre());
                    producto.setPrecio(productoDetails.getPrecio());
                    producto.setStockminimo(productoDetails.getStockminimo());
                    producto.setCategoria(productoDetails.getCategoria());
                    producto.setSede(productoDetails.getSede());
                    return ResponseEntity.ok(productoRepository.save(producto));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idproducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String idproducto) {
        return productoRepository.findById(idproducto)
                .map(producto -> {
                    productoRepository.delete(producto);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{idCategoria}")
    public List<Producto> obtenerProductosPorCategoria(@PathVariable int idCategoria) {
        return productoRepository.findByCategoriaIdcategoria(idCategoria);
    }

    @GetMapping("/sede/{idSede}")
    public List<Producto> obtenerProductosPorSede(@PathVariable int idSede) {
        return productoRepository.findBySedeIdsede(idSede);
    }
}