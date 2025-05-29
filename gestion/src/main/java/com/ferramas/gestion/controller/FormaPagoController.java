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

    @GetMapping("/{idformapago}")
    public ResponseEntity<FormaPago> obtenerFormaPago(@PathVariable int idformapago) {
        return formaPagoRepository.findById(idformapago)
                .map(formaPago -> ResponseEntity.ok().body(formaPago))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public FormaPago crearFormaPago(@RequestBody FormaPago formaPago) {
        return formaPagoRepository.save(formaPago);
    }

    @PutMapping("/{idformapago}")
    public ResponseEntity<FormaPago> actualizarFormaPago(@PathVariable int idformapago, @RequestBody FormaPago formaPagoDetails) {
        return formaPagoRepository.findById(idformapago)
                .map(formaPago -> {
                    formaPago.setNombreformapago(formaPagoDetails.getNombreformapago());
                    formaPago.setDescripcion(formaPagoDetails.getDescripcion());
                    return ResponseEntity.ok(formaPagoRepository.save(formaPago));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idformapago}")
    public ResponseEntity<?> eliminarFormaPago(@PathVariable int idformapago) {
        return formaPagoRepository.findById(idformapago)
                .map(formaPago -> {
                    formaPagoRepository.delete(formaPago);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}