package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="comuna")
public class Comuna {
    @Id
    private int idcomuna;
    private int idciudad;
    private String nombrecomuna;

    public Comuna() {
    }

    public Comuna(int idcomuna, int idciudad, String nombrecomuna) {
        this.idcomuna = idcomuna;
        this.idciudad = idciudad;
        this.nombrecomuna = nombrecomuna;
    }

    public int getIdcomuna() {
        return idcomuna;
    }

    public void setIdcomuna(int idcomuna) {
        this.idcomuna = idcomuna;
    }

    public int getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(int idciudad) {
        this.idciudad = idciudad;
    }

    public String getNombrecomuna() {
        return nombrecomuna;
    }

    public void setNombrecomuna(String nombrecomuna) {
        this.nombrecomuna = nombrecomuna;
    }
}