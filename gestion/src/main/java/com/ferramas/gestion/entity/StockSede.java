package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stocksede")
public class StockSede {
    @Id
    private int idstock;
    private int stock;

    // Foreign key relationships
    @ManyToOne
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idsede", referencedColumnName = "idsede")
    private Sede sede;

    // Keep primitive fields for compatibility
    // private String idproducto;
    // private int idsede;

    public StockSede() {
    }

    public StockSede(int idstock, Producto producto, Sede sede, int stock) {
        this.idstock = idstock;
        this.producto = producto;
        this.sede = sede;
        this.stock = stock;
    }

    public int getIdstock() {
        return idstock;
    }

    public void setIdstock(int idstock) {
        this.idstock = idstock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
}