package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
    List<Comuna> findByCiudadIdciudad(int idciudad);
}