package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByClienteRutcliente(String rutcliente);

    List<Venta> findBySedeIdsede(int idsede);
}