package com.ferramas.gestion.repository;

import com.ferramas.gestion.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
    List<Cliente> findByIdcomuna(int idcomuna);
}