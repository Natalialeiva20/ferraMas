package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "direccion")
public class Direccion {
    @Id
    private int iddireccion;
    private String rutcliente;
    private String calle;
    private int numerocasa;
    private String villa;

    @ManyToOne
    @JoinColumn(name = "idcomuna", referencedColumnName = "idcomuna")
    private Comuna comuna;

    public Direccion() {
    }

    public Direccion(int iddireccion, String rutcliente, String calle, int numerocasa, String villa) {
        this.iddireccion = iddireccion;
        this.rutcliente = rutcliente;
        this.calle = calle;
        this.numerocasa = numerocasa;
        this.villa = villa;
    }

    public int getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(int iddireccion) {
        this.iddireccion = iddireccion;
    }

    public String getRutcliente() {
        return rutcliente;
    }

    public void setRutcliente(String rutcliente) {
        this.rutcliente = rutcliente;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumerocasa() {
        return numerocasa;
    }

    public void setNumerocasa(int numerocasa) {
        this.numerocasa = numerocasa;
    }

    public String getVilla() {
        return villa;
    }

    public void setVilla(String villa) {
        this.villa = villa;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

}