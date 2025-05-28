package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SedeRepository extends JpaRepository<Sede, Integer> {
    List<Sede> findByIdcomuna(int idcomuna);
    List<Sede> findByIdciudad(int idciudad);
}