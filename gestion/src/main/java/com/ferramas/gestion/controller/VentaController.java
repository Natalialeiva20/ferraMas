package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Venta;
import com.ferramas.gestion.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/ventas/")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @GetMapping("/{numerodocumento}")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable int numerodocumento) {
        return ventaRepository.findById(numerodocumento)
                .map(venta -> ResponseEntity.ok().body(venta))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Venta crearVenta(@RequestBody Venta venta) {
        return ventaRepository.save(venta);
    }

    @PutMapping("/{numerodocumento}")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable int numerodocumento, @RequestBody Venta ventaDetails) {
        return ventaRepository.findById(numerodocumento)
                .map(venta -> {
                    venta.setTipodocumento(ventaDetails.getTipodocumento());
                    venta.setFechaventa(ventaDetails.getFechaventa());
                    venta.setTotalventa(ventaDetails.getTotalventa());
                    venta.setIdformapago(ventaDetails.getIdformapago());
                    venta.setRutcliente(ventaDetails.getRutcliente());
                    venta.setIdsede(ventaDetails.getIdsede());
                    return ResponseEntity.ok(ventaRepository.save(venta));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{numerodocumento}")
    public ResponseEntity<?> eliminarVenta(@PathVariable int numerodocumento) {
        return ventaRepository.findById(numerodocumento)
                .map(venta -> {
                    ventaRepository.delete(venta);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{rutCliente}")
    public List<Venta> obtenerVentasPorCliente(@PathVariable String rutCliente) {
        return ventaRepository.findByRutcliente(rutCliente);
    }

    @GetMapping("/sede/{idSede}")
    public List<Venta> obtenerVentasPorSede(@PathVariable int idSede) {
        return ventaRepository.findByIdsede(idSede);
    }
}