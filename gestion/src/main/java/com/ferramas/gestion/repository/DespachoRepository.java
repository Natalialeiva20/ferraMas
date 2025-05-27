package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DespachoRepository extends JpaRepository<Despacho, Integer> {
    List<Despacho> findByRutcliente(String rutcliente);
}