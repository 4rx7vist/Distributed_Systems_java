package com.distribuidas.model;

public class Cliente {
    private int clienteId;
    private String cedulaRuc;
    private String nombreCia;
    private String nombreContacto;
    private String direccionCli;
    private String celular;
    private String ciudadCli;

    public Cliente() {
    }

    public Cliente(int clienteId, String cedulaRuc, String nombreCia, String nombreContacto, String direccionCli,
            String celular, String ciudadCli) {
        this.clienteId = clienteId;
        this.cedulaRuc = cedulaRuc;
        this.nombreCia = nombreCia;
        this.nombreContacto = nombreContacto;
        this.direccionCli = direccionCli;
        this.celular = celular;
        this.ciudadCli = ciudadCli;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getCedulaRuc() {
        return cedulaRuc;
    }

    public void setCedulaRuc(String cedulaRuc) {
        this.cedulaRuc = cedulaRuc;
    }

    public String getNombreCia() {
        return nombreCia;
    }

    public void setNombreCia(String nombreCia) {
        this.nombreCia = nombreCia;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getDireccionCli() {
        return direccionCli;
    }

    public void setDireccionCli(String direccionCli) {
        this.direccionCli = direccionCli;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCiudadCli() {
        return ciudadCli;
    }

    public void setCiudadCli(String ciudadCli) {
        this.ciudadCli = ciudadCli;
    }
}
