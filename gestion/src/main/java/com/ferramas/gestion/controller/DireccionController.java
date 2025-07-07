package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Direccion;
import com.ferramas.gestion.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/direcciones/")
public class DireccionController {

    @Autowired
    private DireccionRepository direccionRepository;

    @GetMapping
    public List<Direccion> listarDirecciones() {
        return direccionRepository.findAll();
    }

    @GetMapping("/{iddireccion}")
    public ResponseEntity<Direccion> obtenerDireccion(@PathVariable int iddireccion) {
        return direccionRepository.findById(iddireccion)
                .map(direccion -> ResponseEntity.ok().body(direccion))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Direccion crearDireccion(@RequestBody Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    @PutMapping("/{iddireccion}")
    public ResponseEntity<Direccion> actualizarDireccion(@PathVariable int iddireccion,
            @RequestBody Direccion direccionDetails) {
        return direccionRepository.findById(iddireccion)
                .map(direccion -> {
                    direccion.setRutcliente(direccionDetails.getRutcliente());
                    direccion.setCalle(direccionDetails.getCalle());
                    direccion.setNumerocasa(direccionDetails.getNumerocasa());
                    direccion.setVilla(direccionDetails.getVilla());
                    direccion.setComuna(direccionDetails.getComuna());
                    return ResponseEntity.ok(direccionRepository.save(direccion));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{iddireccion}")
    public ResponseEntity<?> eliminarDireccion(@PathVariable int iddireccion) {
        return direccionRepository.findById(iddireccion)
                .map(direccion -> {
                    direccionRepository.delete(direccion);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{rutCliente}")
    public List<Direccion> obtenerDireccionesPorCliente(@PathVariable String rutCliente) {
        return direccionRepository.findByRutcliente(rutCliente);
    }

    @GetMapping("/comuna/{idComuna}")
    public List<Direccion> obtenerDireccionesPorComuna(@PathVariable int idComuna) {
        return direccionRepository.findByComunaIdcomuna(idComuna);
    }
}