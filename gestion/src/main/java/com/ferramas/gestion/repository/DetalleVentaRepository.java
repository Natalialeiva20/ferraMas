package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.DetalleVenta;
import com.ferramas.gestion.entity.DetalleVentaId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, DetalleVentaId> {
    List<DetalleVenta> findByNumerodocumento(int numerodocumento);
    List<DetalleVenta> findByIdproducto(String idproducto);
    List<DetalleVenta> findByIdempleado(Integer idempleado);
}