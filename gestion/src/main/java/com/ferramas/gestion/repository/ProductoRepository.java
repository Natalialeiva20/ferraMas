package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    List<Producto> findByIdcategoria(int idcategoria);
    List<Producto> findByIdsede(int idsede);
}