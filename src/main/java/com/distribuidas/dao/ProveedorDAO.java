package com.distribuidas.dao;

import com.distribuidas.db.DatabaseConnection;
import com.distribuidas.model.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProveedorDAO {

    public ObservableList<Proveedor> getAll() {
        ObservableList<Proveedor> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FRAGMENTO_PROVEEDOR_GUAYAQUIL ORDER BY PROVEEDORID";
        
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Proveedor(
                        rs.getInt("PROVEEDORID"),
                        rs.getString("NOMBREPROV"),
                        rs.getString("CONTACTO"),
                        rs.getString("CELUPROV"),
                        rs.getString("CIUDADPROV")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean save(Proveedor p) {
        String sql = "INSERT INTO FRAGMENTO_PROVEEDOR_GUAYAQUIL (PROVEEDORID, NOMBREPROV, CONTACTO, CELUPROV, CIUDADPROV) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getProveedorId());
            stmt.setString(2, p.getNombreProv());
            stmt.setString(3, p.getContacto());
            stmt.setString(4, p.getCeluProv());
            stmt.setString(5, p.getCiudadProv());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Proveedor p) {
        String sql = "UPDATE FRAGMENTO_PROVEEDOR_GUAYAQUIL SET NOMBREPROV=?, CONTACTO=?, CELUPROV=?, CIUDADPROV=? WHERE PROVEEDORID=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombreProv());
            stmt.setString(2, p.getContacto());
            stmt.setString(3, p.getCeluProv());
            stmt.setString(4, p.getCiudadProv());
            stmt.setInt(5, p.getProveedorId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM FRAGMENTO_PROVEEDOR_GUAYAQUIL WHERE PROVEEDORID=?";
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
