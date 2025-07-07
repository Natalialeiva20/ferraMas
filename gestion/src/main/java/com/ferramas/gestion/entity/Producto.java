package com.ferramas.gestion.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @Column(name = "idproducto")
    private String idproducto;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "precio")
    private int precio;

    @Column(name = "stockminimo")
    private int stockminimo;

    // Foreign key relationships
    @ManyToOne
    @JoinColumn(name = "idcategoria", referencedColumnName = "idcategoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "idsede", referencedColumnName = "idsede")
    private Sede sede;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ImagenProducto> imagenes;

    // private int idcategoria;
    // private int idsede;
    public Producto() {
    }

    public Producto(String idproducto, String nombre, int precio, int stockminimo, Categoria categoria, Sede sede) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.stockminimo = stockminimo;
        this.categoria = categoria;
        this.sede = sede;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public List<ImagenProducto> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenProducto> imagenes) {
        this.imagenes = imagenes;
    }
}