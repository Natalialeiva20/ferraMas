package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="categoria")
public class Categoria {
    @Id
    private int id;
    private String nombrecategoria;

    public Categoria() {
    }

    public Categoria(int id, String nombrecategoria) {
        this.id = id;
        this.nombrecategoria = nombrecategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }
}