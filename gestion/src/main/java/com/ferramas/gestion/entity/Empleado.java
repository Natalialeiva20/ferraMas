package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="empleado")
public class Empleado {
    @Id
    private int idempleado;
    private String nombreempleado;
    private String cargoempleado;

    public Empleado() {
    }

    public Empleado(int idempleado, String nombreempleado, String cargoempleado) {
        this.idempleado = idempleado;
        this.nombreempleado = nombreempleado;
        this.cargoempleado = cargoempleado;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public String getNombreempleado() {
        return nombreempleado;
    }

    public void setNombreempleado(String nombreempleado) {
        this.nombreempleado = nombreempleado;
    }

    public String getCargoempleado() {
        return cargoempleado;
    }

    public void setCargoempleado(String cargoempleado) {
        this.cargoempleado = cargoempleado;
    }
}