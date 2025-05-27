package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.StockSede;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockSedeRepository extends JpaRepository<StockSede, Integer> {
    List<StockSede> findByIdproducto(String idproducto);
    List<StockSede> findByIdsede(int idsede);
    StockSede findByIdproductoAndIdsede(String idproducto, int idsede);
}