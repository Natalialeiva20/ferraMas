package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "imagenproducto")
public class ImagenProducto {
    @Id
    private int idimagen;
    // private String idproducto;
    private String imagen;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "idproducto", referencedColumnName = "idproducto", insertable = false, updatable = false)
    private Producto producto;

    public ImagenProducto() {
    }

    public ImagenProducto(int idimagen, Producto producto, String imagen, String descripcion) {
        this.idimagen = idimagen;
        this.producto = producto;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getIdimagen() {
        return idimagen;
    }

    public void setIdimagen(int idimagen) {
        this.idimagen = idimagen;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}