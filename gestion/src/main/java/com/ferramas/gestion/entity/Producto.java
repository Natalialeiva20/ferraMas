package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="producto")
public class Producto {
    @Id
    @Column(name="idproducto")
    private String idproducto;
    
    @Column(name="nombre")
    private String nombre;
    
    @Column(name="precio")
    private int precio;
    
    @Column(name="stockminimo")
    private int stockminimo;
    
    @Column(name="idcategoria")
    private int idcategoria;
    
    @Column(name="idsede")
    private int idsede;

    public Producto() {
    }

    public Producto(String idproducto, String nombre, int precio, int stockminimo, int idcategoria, int idsede) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stockminimo = stockminimo;
        this.idcategoria = idcategoria;
        this.idsede = idsede;
    }

    // Getters y setters
    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
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