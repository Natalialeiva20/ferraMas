package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Empleado;
import com.ferramas.gestion.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/empleados/")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable int id) {
        return empleadoRepository.findById(id)
                .map(empleado -> ResponseEntity.ok().body(empleado))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Empleado crearEmpleado(@RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable int id, @RequestBody Empleado empleadoDetails) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombreempleado(empleadoDetails.getNombreempleado());
                    empleado.setCargoempleado(empleadoDetails.getCargoempleado());
                    return ResponseEntity.ok(empleadoRepository.save(empleado));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable int id) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleadoRepository.delete(empleado);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}