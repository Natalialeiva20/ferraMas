package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalleventa")
@IdClass(DetalleVentaId.class)
public class DetalleVenta {
    @Id
    private int numerodocumento;
    @Id
    private String idproducto;
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "numerodocumento", referencedColumnName = "numerodocumento", insertable = false, updatable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto", insertable = false, updatable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idempleado", referencedColumnName = "idempleado")
    private Empleado empleado;

    // Keep primitive field for compatibility
    // private Integer idempleado;

    public DetalleVenta() {
    }

    public DetalleVenta(int numerodocumento, Producto producto, int cantidad, Empleado empleado) {
        this.numerodocumento = numerodocumento;
        this.producto = producto;
        this.cantidad = cantidad;
        this.empleado = empleado;
    }

    public int getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(int numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}