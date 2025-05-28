package com.ferramas.gestion.controller;

import com.ferramas.gestion.entity.StockSede;
import com.ferramas.gestion.repository.StockSedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/stock-sede/")
public class StockSedeController {

    @Autowired
    private StockSedeRepository stockSedeRepository;

    @GetMapping
    public List<StockSede> listarStockSede() {
        return stockSedeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockSede> obtenerStockSede(@PathVariable int id) {
        return stockSedeRepository.findById(id)
                .map(stockSede -> ResponseEntity.ok().body(stockSede))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StockSede crearStockSede(@RequestBody StockSede stockSede) {
        return stockSedeRepository.save(stockSede);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockSede> actualizarStockSede(@PathVariable int id, @RequestBody StockSede stockSedeDetails) {
        return stockSedeRepository.findById(id)
                .map(stockSede -> {
                    stockSede.setIdproducto(stockSedeDetails.getIdproducto());
                    stockSede.setIdsede(stockSedeDetails.getIdsede());
                    stockSede.setStock(stockSedeDetails.getStock());
                    return ResponseEntity.ok(stockSedeRepository.save(stockSede));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarStockSede(@PathVariable int id) {
        return stockSedeRepository.findById(id)
                .map(stockSede -> {
                    stockSedeRepository.delete(stockSede);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{idProducto}")
    public List<StockSede> obtenerStockPorProducto(@PathVariable String idProducto) {
        return stockSedeRepository.findByIdproducto(idProducto);
    }

    @GetMapping("/sede/{idSede}")
    public List<StockSede> obtenerStockPorSede(@PathVariable int idSede) {
        return stockSedeRepository.findByIdsede(idSede);
    }

    @GetMapping("/producto/{idProducto}/sede/{idSede}")
    public ResponseEntity<StockSede> obtenerStockProductoSede(@PathVariable String idProducto, @PathVariable int idSede) {
        StockSede stockSede = stockSedeRepository.findByIdproductoAndIdsede(idProducto, idSede);
        return stockSede != null ? ResponseEntity.ok(stockSede) : ResponseEntity.notFound().build();
    }
}