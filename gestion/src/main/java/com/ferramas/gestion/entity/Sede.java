// filepath: c:\Users\diego\Desktop\ferraMas\gestion\src\main\java\com\ferramas\gestion\entity\Sede.java
package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="sede")
public class Sede {
    @Id
    private int idsede;
    private String nombresede;
    private int idcomuna;
    private int idciudad;
    private int idempleado;

    public Sede() {
    }

    public Sede(int idsede, String nombresede, int idcomuna, int idciudad, int idempleado) {
        this.idsede = idsede;
        this.nombresede = nombresede;
        this.idcomuna = idcomuna;
        this.idciudad = idciudad;
        this.idempleado = idempleado;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public String getNombresede() {
        return nombresede;
    }

    public void setNombresede(String nombresede) {
        this.nombresede = nombresede;
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

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }
}