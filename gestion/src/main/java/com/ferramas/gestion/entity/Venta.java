package com.ferramas.gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="venta")
public class Venta {
    @Id
    private int numerodocumento;
    private String tipodocumento;
    private LocalDate fechaventa;
    private int totalventa;
    private int idformapago;
    private String rutcliente;
    private int idsede;

    public Venta() {
    }

    public Venta(int numerodocumento, String tipodocumento, LocalDate fechaventa, int totalventa, int idformapago, String rutcliente, int idsede) {
        this.numerodocumento = numerodocumento;
        this.tipodocumento = tipodocumento;
        this.fechaventa = fechaventa;
        this.totalventa = totalventa;
        this.idformapago = idformapago;
        this.rutcliente = rutcliente;
        this.idsede = idsede;
    }

    public int getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(int numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public LocalDate getFechaventa() {
        return fechaventa;
    }

    public void setFechaventa(LocalDate fechaventa) {
        this.fechaventa = fechaventa;
    }

    public int getTotalventa() {
        return totalventa;
    }

    public void setTotalventa(int totalventa) {
        this.totalventa = totalventa;
    }

    public int getIdformapago() {
        return idformapago;
    }

    public void setIdformapago(int idformapago) {
        this.idformapago = idformapago;
    }

    public String getRutcliente() {
        return rutcliente;
    }

    public void setRutcliente(String rutcliente) {
        this.rutcliente = rutcliente;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }
}