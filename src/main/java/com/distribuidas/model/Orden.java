package com.distribuidas.model;

import java.sql.Date;

public class Orden {
    private int ordenId;
    private int clienteId;
    private int empleadoId;
    private Date fechaOrden;
    private Integer descuento; // Can be null

    public Orden() {
    }

    public Orden(int ordenId, int clienteId, int empleadoId, Date fechaOrden, Integer descuento) {
        this.ordenId = ordenId;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.fechaOrden = fechaOrden;
        this.descuento = descuento;
    }

    public int getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(int ordenId) {
        this.ordenId = ordenId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Date getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }
}
