package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    List<Cliente> findByComunaIdcomuna(int idcomuna);
}