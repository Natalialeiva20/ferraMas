package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    private int idcategoria;
    private String nombrecategoria;

    public Categoria() {
    }

    public Categoria(int idcategoria, String nombrecategoria) {
        this.idcategoria = idcategoria;
        this.nombrecategoria = nombrecategoria;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }
}