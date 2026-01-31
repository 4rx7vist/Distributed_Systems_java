package com.distribuidas.dao;

import com.distribuidas.db.DatabaseConnection;
import com.distribuidas.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {

    public ObservableList<Producto> getAll() {
        ObservableList<Producto> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM PRODUCTOS ORDER BY PRODUCTOID";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("PRODUCTOID"),
                        rs.getInt("CATEGORIAID"),
                        rs.getInt("PROVEEDORID"),
                        rs.getString("DESCRIPCION"),
                        rs.getDouble("PRECIOUNIT"),
                        rs.getInt("EXISTENCIA")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean save(Producto p) {
        String sql = "INSERT INTO PRODUCTOS (PRODUCTOID, CATEGORIAID, PROVEEDORID, DESCRIPCION, PRECIOUNIT, EXISTENCIA) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getProductoId());
            stmt.setInt(2, p.getCategoriaId());
            stmt.setInt(3, p.getProveedorId());
            stmt.setString(4, p.getDescripcion());
            stmt.setDouble(5, p.getPrecioUnit());
            stmt.setInt(6, p.getExistencia());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Producto p) {
        String sql = "UPDATE PRODUCTOS SET CATEGORIAID=?, PROVEEDORID=?, DESCRIPCION=?, PRECIOUNIT=?, EXISTENCIA=? WHERE PRODUCTOID=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getCategoriaId());
            stmt.setInt(2, p.getProveedorId());
            stmt.setString(3, p.getDescripcion());
            stmt.setDouble(4, p.getPrecioUnit());
            stmt.setInt(5, p.getExistencia());
            stmt.setInt(6, p.getProductoId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM PRODUCTOS WHERE PRODUCTOID=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
