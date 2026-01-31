package com.distribuidas.model;

public class Categoria {
    private int categoriaId;
    private String nombreCat;

    public Categoria() {
    }

    public Categoria(int categoriaId, String nombreCat) {
        this.categoriaId = categoriaId;
        this.nombreCat = nombreCat;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombreCat() {
        return nombreCat;
    }

    public void setNombreCat(String nombreCat) {
        this.nombreCat = nombreCat;
    }

    @Override
    public String toString() {
        return nombreCat; // Useful for ComboBoxes
    }
}
