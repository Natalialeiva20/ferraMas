package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name="stocksede")
public class StockSede {
    @Id
    private int idstock;
    private String idproducto;
    private int idsede;
    private int stock;

    public StockSede() {
    }

    public StockSede(int idstock, String idproducto, int idsede, int stock) {
        this.idstock = idstock;
        this.idproducto = idproducto;
        this.idsede = idsede;
        this.stock = stock;
    }

    public int getIdstock() {
        return idstock;
    }

    public void setIdstock(int idstock) {
        this.idstock = idstock;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}