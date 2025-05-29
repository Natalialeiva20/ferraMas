package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Ciudad;
import com.ferramas.gestion.repository.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/ciudades/")
public class CiudadController {

    @Autowired
    private CiudadRepository ciudadRepository;

    @GetMapping
    public List<Ciudad> listarCiudades() {
        return ciudadRepository.findAll();
    }

    @GetMapping("/{idciudad}")
    public ResponseEntity<Ciudad> obtenerCiudad(@PathVariable int idciudad) {
        return ciudadRepository.findById(idciudad)
                .map(ciudad -> ResponseEntity.ok().body(ciudad))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ciudad crearCiudad(@RequestBody Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    @PutMapping("/{idciudad}")
    public ResponseEntity<Ciudad> actualizarCiudad(@PathVariable int idciudad, @RequestBody Ciudad ciudadDetails) {
        return ciudadRepository.findById(idciudad)
                .map(ciudad -> {
                    ciudad.setNombreciudad(ciudadDetails.getNombreciudad());
                    return ResponseEntity.ok(ciudadRepository.save(ciudad));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idciudad}")
    public ResponseEntity<?> eliminarCiudad(@PathVariable int idciudad) {
        return ciudadRepository.findById(idciudad)
                .map(ciudad -> {
                    ciudadRepository.delete(ciudad);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}