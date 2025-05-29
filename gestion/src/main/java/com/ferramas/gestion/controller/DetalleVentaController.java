package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Despacho;
import com.ferramas.gestion.entity.DetalleVenta;
import com.ferramas.gestion.entity.DetalleVentaId;
import com.ferramas.gestion.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/detalle-venta/")
public class DetalleVentaController {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @GetMapping
    public List<DetalleVenta> listarDetalleVenta() {
        return detalleVentaRepository.findAll();
    }

    @PostMapping
    public DetalleVenta crearDetalleVenta(@RequestBody DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @GetMapping("/venta/{numeroDocumento}")
    public List<DetalleVenta> obtenerDetallesPorVenta(@PathVariable int numeroDocumento) {
        return detalleVentaRepository.findByNumerodocumento(numeroDocumento);
    }

    @GetMapping("/producto/{idProducto}")
    public List<DetalleVenta> obtenerDetallesPorProducto(@PathVariable String idProducto) {
        return detalleVentaRepository.findByIdproducto(idProducto);
    }

    @GetMapping("/empleado/{idEmpleado}")
    public List<DetalleVenta> obtenerDetallesPorEmpleado(@PathVariable Integer idEmpleado) {
        return detalleVentaRepository.findByIdempleado(idEmpleado);
    }
}