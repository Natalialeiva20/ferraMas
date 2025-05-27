package com.ferramas.gestion.dto;

public class EmpleadoDTO {
    int idempleado;
    String nombreempledo;
    String cargoempleado;

    public EmpleadoDTO() {
    }

    public EmpleadoDTO(int idempleado, String nombreempledo, String cargoempleado) {
        this.idempleado = idempleado;
        this.nombreempledo = nombreempledo;
        this.cargoempleado = cargoempleado;
    }
    public int getIdempleado() {
        return idempleado;
    }
    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }
    public String getNombreempledo() {
        return nombreempledo;
    }
    public void setNombreempledo(String nombreempledo) {
        this.nombreempledo = nombreempledo;
    }
    public String getCargoempleado() {
        return cargoempleado;
    }
    public void setCargoempleado(String cargoempleado) {
        this.cargoempleado = cargoempleado;
    }
}