package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Sede;
import com.ferramas.gestion.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/sedes/")
public class SedeController {

    @Autowired
    private SedeRepository sedeRepository;

    @GetMapping
    public List<Sede> listarSedes() {
        return sedeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sede> obtenerSede(@PathVariable int id) {
        return sedeRepository.findById(id)
                .map(sede -> ResponseEntity.ok().body(sede))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sede crearSede(@RequestBody Sede sede) {
        return sedeRepository.save(sede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sede> actualizarSede(@PathVariable int id, @RequestBody Sede sedeDetails) {
        return sedeRepository.findById(id)
                .map(sede -> {
                    sede.setNombresede(sedeDetails.getNombresede());
                    sede.setIdcomuna(sedeDetails.getIdcomuna());
                    sede.setIdciudad(sedeDetails.getIdciudad());
                    sede.setIdempleado(sedeDetails.getIdempleado());
                    return ResponseEntity.ok(sedeRepository.save(sede));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSede(@PathVariable int id) {
        return sedeRepository.findById(id)
                .map(sede -> {
                    sedeRepository.delete(sede);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/comuna/{idComuna}")
    public List<Sede> obtenerSedesPorComuna(@PathVariable int idComuna) {
        return sedeRepository.findByIdcomuna(idComuna);
    }

    @GetMapping("/ciudad/{idCiudad}")
    public List<Sede> obtenerSedesPorCiudad(@PathVariable int idCiudad) {
        return sedeRepository.findByIdciudad(idCiudad);
    }
}