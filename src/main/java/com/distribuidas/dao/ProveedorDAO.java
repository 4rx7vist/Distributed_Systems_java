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
        String sql = "SELECT * FROM PROVEEDORES ORDER BY PROVEEDORID";
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
}
