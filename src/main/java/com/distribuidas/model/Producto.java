package com.distribuidas.model;

public class Producto {
    private int productoId;
    private int categoriaId;
    private int proveedorId;
    private String descripcion;
    private double precioUnit;
    private int existencia;

    public Producto() {
    }

    public Producto(int productoId, int categoriaId, int proveedorId, String descripcion, double precioUnit,
            int existencia) {
        this.productoId = productoId;
        this.categoriaId = categoriaId;
        this.proveedorId = proveedorId;
        this.descripcion = descripcion;
        this.precioUnit = precioUnit;
        this.existencia = existencia;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(double precioUnit) {
        this.precioUnit = precioUnit;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
