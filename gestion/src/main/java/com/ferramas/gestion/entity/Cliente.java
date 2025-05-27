package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="cliente")
public class Cliente {
    @Id
    private String rut;
    private String nombrecliente;
    private String apellidocliente;
    private int idcomuna;

    public Cliente() {
    }

    public Cliente(String rut, String nombrecliente, String apellidocliente, int idcomuna) {
        this.rut = rut;
        this.nombrecliente = nombrecliente;
        this.apellidocliente = apellidocliente;
        this.idcomuna = idcomuna;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getApellidocliente() {
        return apellidocliente;
    }

    public void setApellidocliente(String apellidocliente) {
        this.apellidocliente = apellidocliente;
    }

    public int getIdcomuna() {
        return idcomuna;
    }

    public void setIdcomuna(int idcomuna) {
        this.idcomuna = idcomuna;
    }
}