package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="detalleventa")
@IdClass(DetalleVentaId.class)
public class DetalleVenta {
    @Id
    private int numerodocumento;
    @Id
    private String idproducto;
    private int cantidad;
    private Integer idempleado;

    public DetalleVenta() {
    }

    public DetalleVenta(int numerodocumento, String idproducto, int cantidad, Integer idempleado) {
        this.numerodocumento = numerodocumento;
        this.idproducto = idproducto;
        this.cantidad = cantidad;
        this.idempleado = idempleado;
    }

    public int getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(int numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(Integer idempleado) {
        this.idempleado = idempleado;
    }
}