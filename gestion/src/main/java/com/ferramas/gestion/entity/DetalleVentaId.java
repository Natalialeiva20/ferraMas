package com.ferramas.gestion.entity;

import java.io.Serializable;
import java.util.Objects;

public class DetalleVentaId implements Serializable {
    private int numerodocumento;
    private String idproducto;

    public DetalleVentaId() {
    }

    public DetalleVentaId(int numerodocumento, String idproducto) {
        this.numerodocumento = numerodocumento;
        this.idproducto = idproducto;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleVentaId)) return false;
        DetalleVentaId that = (DetalleVentaId) o;
        return numerodocumento == that.numerodocumento && Objects.equals(idproducto, that.idproducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerodocumento, idproducto);
    }
}