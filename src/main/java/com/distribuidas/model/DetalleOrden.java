package com.distribuidas.model;

public class DetalleOrden {
    private int ordenId;
    private int detalleId;
    private int productoId;
    private int cantidad;

    public DetalleOrden() {
    }

    public DetalleOrden(int ordenId, int detalleId, int productoId, int cantidad) {
        this.ordenId = ordenId;
        this.detalleId = detalleId;
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public int getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(int ordenId) {
        this.ordenId = ordenId;
    }

    public int getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(int detalleId) {
        this.detalleId = detalleId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
