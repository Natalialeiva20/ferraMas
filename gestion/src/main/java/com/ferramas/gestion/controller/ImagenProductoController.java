package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.ImagenProducto;
import com.ferramas.gestion.repository.ImagenProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/imagenes-producto/")
public class ImagenProductoController {

    @Autowired
    private ImagenProductoRepository imagenProductoRepository;

    @GetMapping
    public List<ImagenProducto> listarImagenesProducto() {
        return imagenProductoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenProducto> obtenerImagenProducto(@PathVariable int id) {
        return imagenProductoRepository.findById(id)
                .map(imagen -> ResponseEntity.ok().body(imagen))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ImagenProducto crearImagenProducto(@RequestBody ImagenProducto imagenProducto) {
        return imagenProductoRepository.save(imagenProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagenProducto> actualizarImagenProducto(@PathVariable int id, @RequestBody ImagenProducto imagenDetails) {
        return imagenProductoRepository.findById(id)
                .map(imagen -> {
                    imagen.setIdproducto(imagenDetails.getIdproducto());
                    imagen.setImagen(imagenDetails.getImagen());
                    imagen.setDescripcion(imagenDetails.getDescripcion());
                    return ResponseEntity.ok(imagenProductoRepository.save(imagen));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarImagenProducto(@PathVariable int id) {
        return imagenProductoRepository.findById(id)
                .map(imagen -> {
                    imagenProductoRepository.delete(imagen);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{idProducto}")
    public List<ImagenProducto> obtenerImagenesPorProducto(@PathVariable String idProducto) {
        return imagenProductoRepository.findByIdproducto(idProducto);
    }
}