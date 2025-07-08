package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "formapago")
public class FormaPago {
    @Id
    private int idformapago;
    private String nombreformapago;
    private String descripcion;

    public FormaPago() {
    }

    public FormaPago(int idformapago, String nombreformapago, String descripcion) {
        this.idformapago = idformapago;
        this.nombreformapago = nombreformapago;
        this.descripcion = descripcion;
    }

    public int getIdformapago() {
        return idformapago;
    }

    public void setIdformapago(int idformapago) {
        this.idformapago = idformapago;
    }

    public String getNombreformapago() {
        return nombreformapago;
    }

    public void setNombreformapago(String nombreformapago) {
        this.nombreformapago = nombreformapago;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}