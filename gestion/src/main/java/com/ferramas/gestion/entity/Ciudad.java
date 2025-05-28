package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="ciudad")
public class Ciudad {
    @Id
    private int idciudad;
    private String nombreciudad;

    public Ciudad() {
    }

    public Ciudad(int idciudad, String nombreciudad) {
        this.idciudad = idciudad;
        this.nombreciudad = nombreciudad;
    }

    public int getIdciudad() {
        return idciudad;
    }

    public void setIdciudad(int idciudad) {
        this.idciudad = idciudad;
    }

    public String getNombreciudad() {
        return nombreciudad;
    }

    public void setNombreciudad(String nombreciudad) {
        this.nombreciudad = nombreciudad;
    }
}