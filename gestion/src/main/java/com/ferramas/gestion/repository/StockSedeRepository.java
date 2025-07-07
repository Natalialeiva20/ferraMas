package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.StockSede;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockSedeRepository extends JpaRepository<StockSede, Integer> {
    List<StockSede> findByProductoIdproducto(String idproducto);

    List<StockSede> findBySedeIdsede(int idsede);

    StockSede findByProductoIdproductoAndSedeIdsede(String idproducto, int idsede);
}