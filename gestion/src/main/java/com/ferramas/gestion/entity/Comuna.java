package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comuna")
public class Comuna {
    @Id
    private int idcomuna;
    private String nombrecomuna;

    // Foreign key relationship
    @ManyToOne
    @JoinColumn(name = "idciudad", referencedColumnName = "idciudad")
    private Ciudad ciudad;

    // Keep primitive field for compatibility
    // private int idciudad;

    public Comuna() {
    }

    public Comuna(int idcomuna, Ciudad ciudad, String nombrecomuna) {
        this.idcomuna = idcomuna;
        this.ciudad = ciudad;
        this.nombrecomuna = nombrecomuna;
    }

    public int getIdcomuna() {
        return idcomuna;
    }

    public void setIdcomuna(int idcomuna) {
        this.idcomuna = idcomuna;
    }

    public String getNombrecomuna() {
        return nombrecomuna;
    }

    public void setNombrecomuna(String nombrecomuna) {
        this.nombrecomuna = nombrecomuna;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}