package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Despacho;
import com.ferramas.gestion.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/despachos/")
public class DespachoController {

    @Autowired
    private DespachoRepository despachoRepository;

    @GetMapping
    public List<Despacho> listarDespachos() {
        return despachoRepository.findAll();
    }

    @GetMapping("/{numdespacho}")
    public ResponseEntity<Despacho> obtenerDespacho(@PathVariable int numdespacho) {
        return despachoRepository.findById(numdespacho)
                .map(despacho -> ResponseEntity.ok().body(despacho))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Despacho crearDespacho(@RequestBody Despacho despacho) {
        return despachoRepository.save(despacho);
    }

    @PutMapping("/{numdespacho}")
    public ResponseEntity<Despacho> actualizarDespacho(@PathVariable int numdespacho, @RequestBody Despacho despachoDetails) {
        return despachoRepository.findById(numdespacho)
                .map(despacho -> {
                    despacho.setRutcliente(despachoDetails.getRutcliente());
                    despacho.setFechadespacho(despachoDetails.getFechadespacho());
                    return ResponseEntity.ok(despachoRepository.save(despacho));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{numdespacho}")
    public ResponseEntity<?> eliminarDespacho(@PathVariable int numdespacho) {
        return despachoRepository.findById(numdespacho)
                .map(despacho -> {
                    despachoRepository.delete(despacho);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{rutCliente}")
    public List<Despacho> obtenerDespachosPorCliente(@PathVariable String rutCliente) {
        return despachoRepository.findByRutcliente(rutCliente);
    }
}