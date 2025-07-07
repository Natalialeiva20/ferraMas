package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    List<Producto> findByCategoriaIdcategoria(int idcategoria);

    List<Producto> findByCategoriaIdcategoriaAndSedeIdsede(int idcategoria, int idsede);

    List<Producto> findBySedeIdsede(int idsede);
}