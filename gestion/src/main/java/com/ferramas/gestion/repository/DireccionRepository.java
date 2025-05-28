package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findByRutcliente(String rutcliente);
    List<Direccion> findByIdcomuna(int idcomuna);
}