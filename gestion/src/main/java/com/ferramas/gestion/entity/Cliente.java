package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="cliente")
public class Cliente {
    @Id
    @Column(name="rutcliente")
    private String rutcliente;
    
    @Column(name="nombrecliente")
    private String nombrecliente;
    
    @Column(name="apellidocliente")
    private String apellidocliente;
    
    @Column(name="idcomuna")
    private int idcomuna;

    public Cliente() {
    }

    public Cliente(String rutcliente, String nombrecliente, String apellidocliente, int idcomuna) {
        this.rutcliente = rutcliente;
        this.nombrecliente = nombrecliente;
        this.apellidocliente = apellidocliente;
        this.idcomuna = idcomuna;
    }

    public String getRutcliente() {
        return rutcliente;
    }

    public void setRutcliente(String rutcliente) {
        this.rutcliente = rutcliente;
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