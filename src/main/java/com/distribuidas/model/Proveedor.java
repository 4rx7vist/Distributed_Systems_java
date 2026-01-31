package com.distribuidas.model;

public class Proveedor {
    private int proveedorId;
    private String nombreProv;
    private String contacto;
    private String celuProv;
    private String ciudadProv;

    public Proveedor() {
    }

    public Proveedor(int proveedorId, String nombreProv, String contacto, String celuProv, String ciudadProv) {
        this.proveedorId = proveedorId;
        this.nombreProv = nombreProv;
        this.contacto = contacto;
        this.celuProv = celuProv;
        this.ciudadProv = ciudadProv;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getNombreProv() {
        return nombreProv;
    }

    public void setNombreProv(String nombreProv) {
        this.nombreProv = nombreProv;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCeluProv() {
        return celuProv;
    }

    public void setCeluProv(String celuProv) {
        this.celuProv = celuProv;
    }

    public String getCiudadProv() {
        return ciudadProv;
    }

    public void setCiudadProv(String ciudadProv) {
        this.ciudadProv = ciudadProv;
    }

    @Override
    public String toString() {
        return nombreProv;
    }
}
