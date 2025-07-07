package com.ferramas.gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numerodocumento;

    private String tipodocumento;
    private LocalDate fechaventa;
    private int totalventa;

    // Foreign key relationships
    @ManyToOne
    @JoinColumn(name = "idformapago", referencedColumnName = "idformapago")
    private FormaPago formapago;

    @ManyToOne
    @JoinColumn(name = "rutcliente", referencedColumnName = "rutcliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idsede", referencedColumnName = "idsede")
    private Sede sede;

    // Keep primitive fields for compatibility
    // private int idformapago;
    // private String rutcliente;
    // private int idsede;

    public Venta() {
    }

    public Venta(int numerodocumento, String tipodocumento, LocalDate fechaventa, int totalventa, FormaPago formapago,
            Cliente cliente, Sede sede) {
        this.numerodocumento = numerodocumento;
        this.tipodocumento = tipodocumento;
        this.fechaventa = fechaventa;
        this.totalventa = totalventa;
        this.formapago = formapago;
        this.cliente = cliente;
        this.sede = sede;
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

    public FormaPago getFormaPago() {
        return formapago;
    }

    public void setFormaPago(FormaPago formapago) {
        this.formapago = formapago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
}