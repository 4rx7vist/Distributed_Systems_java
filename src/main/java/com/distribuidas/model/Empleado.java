package com.distribuidas.model;

import java.sql.Date;

public class Empleado {
    private int empleadoId;
    private Integer reportaA; // Can be null
    private String nombre;
    private String apellido;
    private Date fechaNac; // sql.Date for simplicity with DB
    private Integer extension;

    public Empleado() {}

    public Empleado(int empleadoId, Integer reportaA, String nombre, String apellido, Date fechaNac, Integer extension) {
        this.empleadoId = empleadoId;
        this.reportaA = reportaA;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.extension = extension;
    }

    public int getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(int empleadoId) { this.empleadoId = empleadoId; }

    public Integer getReportaA() { return reportaA; }
    public void setReportaA(Integer reportaA) { this.reportaA = reportaA; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Date getFechaNac() { return fechaNac; }
    public void setFechaNac(Date fechaNac) { this.fechaNac = fechaNac; }

    public Integer getExtension() { return extension; }
    public void setExtension(Integer extension) { this.extension = extension; }
    
    @Override
    public String toString() {
        return nombre + " " + apellido;
    }
}
