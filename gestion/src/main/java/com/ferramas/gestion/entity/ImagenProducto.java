package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="imagenproducto")
public class ImagenProducto {
    @Id
    private int idimagen;
    private String idproducto;
    private String imagen;
    private String descripcion;

    public ImagenProducto() {
    }

    public ImagenProducto(int idimagen, String idproducto, String imagen, String descripcion) {
        this.idimagen = idimagen;
        this.idproducto = idproducto;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getIdimagen() {
        return idimagen;
    }

    public void setIdimagen(int idimagen) {
        this.idimagen = idimagen;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
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