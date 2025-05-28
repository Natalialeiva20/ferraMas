package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="producto")
public class Producto {
    @Id
    private String id;
    private String nombre;
    private double precio;
    private int stockminimo;
    private int idcategoria;
    private int idsede;

    public Producto() {
    }

    public Producto(String id, String nombre, double precio, int stockminimo, int idcategoria, int idsede) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stockminimo = stockminimo;
        this.idcategoria = idcategoria;
        this.idsede = idsede;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStockminimo() {
        return stockminimo;
    }

    public void setStockminimo(int stockminimo) {
        this.stockminimo = stockminimo;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }
}