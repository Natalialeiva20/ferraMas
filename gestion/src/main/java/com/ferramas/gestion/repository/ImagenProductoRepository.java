package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto, Integer> {
    List<ImagenProducto> findByProductoIdproducto(String idproducto);
}