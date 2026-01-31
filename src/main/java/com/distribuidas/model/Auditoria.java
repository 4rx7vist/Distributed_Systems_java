package com.distribuidas.model;

import java.sql.Date;

public class Auditoria {
    private String userName;
    private Date fecha;
    private String tipoOperacion;
    private String nombreTable;
    private String anterior;
    private String nuevo;

    public Auditoria() {
    }

    public Auditoria(String userName, Date fecha, String tipoOperacion, String nombreTable, String anterior,
            String nuevo) {
        this.userName = userName;
        this.fecha = fecha;
        this.tipoOperacion = tipoOperacion;
        this.nombreTable = nombreTable;
        this.anterior = anterior;
        this.nuevo = nuevo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getNombreTable() {
        return nombreTable;
    }

    public void setNombreTable(String nombreTable) {
        this.nombreTable = nombreTable;
    }

    public String getAnterior() {
        return anterior;
    }

    public void setAnterior(String anterior) {
        this.anterior = anterior;
    }

    public String getNuevo() {
        return nuevo;
    }

    public void setNuevo(String nuevo) {
        this.nuevo = nuevo;
    }
}
