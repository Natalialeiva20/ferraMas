package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Categoria;
import com.ferramas.gestion.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/categorias/")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{idcategoria}")
    public ResponseEntity<Categoria> obtenerCategoria(@PathVariable int idcategoria) {
        return categoriaRepository.findById(idcategoria)
                .map(categoria -> ResponseEntity.ok().body(categoria))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @PutMapping("/{idcategoria}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable int idcategoria, @RequestBody Categoria categoriaDetails) {
        return categoriaRepository.findById(idcategoria)
                .map(categoria -> {
                    categoria.setNombrecategoria(categoriaDetails.getNombrecategoria());
                    return ResponseEntity.ok(categoriaRepository.save(categoria));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idcategoria}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable int idcategoria) {
        return categoriaRepository.findById(idcategoria)
                .map(categoria -> {
                    categoriaRepository.delete(categoria);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}