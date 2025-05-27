package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.FormaPago;
import com.ferramas.gestion.repository.FormaPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/formas-pago/")
public class FormaPagoController {

    @Autowired
    private FormaPagoRepository formaPagoRepository;

    @GetMapping
    public List<FormaPago> listarFormasPago() {
        return formaPagoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPago> obtenerFormaPago(@PathVariable int id) {
        return formaPagoRepository.findById(id)
                .map(formaPago -> ResponseEntity.ok().body(formaPago))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public FormaPago crearFormaPago(@RequestBody FormaPago formaPago) {
        return formaPagoRepository.save(formaPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPago> actualizarFormaPago(@PathVariable int id, @RequestBody FormaPago formaPagoDetails) {
        return formaPagoRepository.findById(id)
                .map(formaPago -> {
                    formaPago.setNombreformapago(formaPagoDetails.getNombreformapago());
                    formaPago.setDescripcion(formaPagoDetails.getDescripcion());
                    return ResponseEntity.ok(formaPagoRepository.save(formaPago));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFormaPago(@PathVariable int id) {
        return formaPagoRepository.findById(id)
                .map(formaPago -> {
                    formaPagoRepository.delete(formaPago);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}