package com.ferramas.gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="despacho")
public class Despacho {
    @Id
    private int numdespacho;
    private String rutcliente;
    private LocalDate fechadespacho;

    public Despacho() {
    }

    public Despacho(int numdespacho, String rutcliente, LocalDate fechadespacho) {
        this.numdespacho = numdespacho;
        this.rutcliente = rutcliente;
        this.fechadespacho = fechadespacho;
    }

    public int getNumdespacho() {
        return numdespacho;
    }

    public void setNumdespacho(int numdespacho) {
        this.numdespacho = numdespacho;
    }

    public String getRutcliente() {
        return rutcliente;
    }

    public void setRutcliente(String rutcliente) {
        this.rutcliente = rutcliente;
    }

    public LocalDate getFechadespacho() {
        return fechadespacho;
    }

    public void setFechadespacho(LocalDate fechadespacho) {
        this.fechadespacho = fechadespacho;
    }
}