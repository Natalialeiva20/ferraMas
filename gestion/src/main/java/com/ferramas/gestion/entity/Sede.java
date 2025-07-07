// filepath: c:\Users\diego\Desktop\ferraMas\gestion\src\main\java\com\ferramas\gestion\entity\Sede.java
package com.ferramas.gestion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sede")
public class Sede {
    @Id
    private int idsede;
    private String nombresede;

    // Foreign key relationships
    @ManyToOne
    @JoinColumn(name = "idcomuna", referencedColumnName = "idcomuna")
    private Comuna comuna;

    @ManyToOne
    @JoinColumn(name = "idciudad", referencedColumnName = "idciudad")
    private Ciudad ciudad;

    @ManyToOne
    @JoinColumn(name = "idempleado", referencedColumnName = "idempleado")
    private Empleado empleado;

    // Keep primitive fields for compatibility
    // private int idcomuna;
    // private int idempleado;

    public Sede() {
    }

    public Sede(int idsede, String nombresede, Comuna comuna, Empleado empleado) {
        this.idsede = idsede;
        this.nombresede = nombresede;
        this.comuna = comuna;
        this.empleado = empleado;
    }

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public String getNombresede() {
        return nombresede;
    }

    public void setNombresede(String nombresede) {
        this.nombresede = nombresede;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}