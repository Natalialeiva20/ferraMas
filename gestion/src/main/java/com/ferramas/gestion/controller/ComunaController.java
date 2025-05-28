package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Comuna;
import com.ferramas.gestion.repository.ComunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/comunas/")
public class ComunaController {

    @Autowired
    private ComunaRepository comunaRepository;

    @GetMapping
    public List<Comuna> listarComunas() {
        return comunaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> obtenerComuna(@PathVariable int id) {
        return comunaRepository.findById(id)
                .map(comuna -> ResponseEntity.ok().body(comuna))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Comuna crearComuna(@RequestBody Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> actualizarComuna(@PathVariable int id, @RequestBody Comuna comunaDetails) {
        return comunaRepository.findById(id)
                .map(comuna -> {
                    comuna.setIdciudad(comunaDetails.getIdciudad());
                    comuna.setNombrecomuna(comunaDetails.getNombrecomuna());
                    return ResponseEntity.ok(comunaRepository.save(comuna));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarComuna(@PathVariable int id) {
        return comunaRepository.findById(id)
                .map(comuna -> {
                    comunaRepository.delete(comuna);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ciudad/{idCiudad}")
    public List<Comuna> obtenerComunasPorCiudad(@PathVariable int idCiudad) {
        return comunaRepository.findByIdciudad(idCiudad);
    }
}