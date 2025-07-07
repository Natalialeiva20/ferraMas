package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.Cliente;
import com.ferramas.gestion.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/clientes/")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable String rut) {
        return clienteRepository.findById(rut)
                .map(cliente -> ResponseEntity.ok().body(cliente))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{rut}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable String rut, @RequestBody Cliente clienteDetails) {
        return clienteRepository.findById(rut)
                .map(cliente -> {
                    cliente.setNombrecliente(clienteDetails.getNombrecliente());
                    cliente.setApellidocliente(clienteDetails.getApellidocliente());
                    cliente.setComuna(clienteDetails.getComuna());
                    return ResponseEntity.ok(clienteRepository.save(cliente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarCliente(@PathVariable String rut) {
        return clienteRepository.findById(rut)
                .map(cliente -> {
                    clienteRepository.delete(cliente);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/comuna/{idComuna}")
    public List<Cliente> obtenerClientesPorComuna(@PathVariable int idComuna) {
        return clienteRepository.findByComunaIdcomuna(idComuna);
    }
}