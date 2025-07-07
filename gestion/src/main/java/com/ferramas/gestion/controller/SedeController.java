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

    @GetMapping("/{idsede}")
    public ResponseEntity<Sede> obtenerSede(@PathVariable int idsede) {
        return sedeRepository.findById(idsede)
                .map(sede -> ResponseEntity.ok().body(sede))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sede crearSede(@RequestBody Sede sede) {
        return sedeRepository.save(sede);
    }

    @PutMapping("/{idsede}")
    public ResponseEntity<Sede> actualizarSede(@PathVariable int idsede, @RequestBody Sede sedeDetails) {
        return sedeRepository.findById(idsede)
                .map(sede -> {
                    sede.setNombresede(sedeDetails.getNombresede());
                    sede.setComuna(sedeDetails.getComuna());
                    sede.setCiudad(sedeDetails.getCiudad());
                    sede.setEmpleado(sedeDetails.getEmpleado());
                    return ResponseEntity.ok(sedeRepository.save(sede));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idsede}")
    public ResponseEntity<?> eliminarSede(@PathVariable int idsede) {
        return sedeRepository.findById(idsede)
                .map(sede -> {
                    sedeRepository.delete(sede);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/comuna/{idComuna}")
    public List<Sede> obtenerSedesPorComuna(@PathVariable int idComuna) {
        return sedeRepository.findByComunaIdcomuna(idComuna);
    }

    @GetMapping("/ciudad/{idCiudad}")
    public List<Sede> obtenerSedesPorCiudad(@PathVariable int idCiudad) {
        return sedeRepository.findByCiudadIdciudad(idCiudad);
    }
}