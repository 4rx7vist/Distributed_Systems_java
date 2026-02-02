package com.distribuidas.dao.jorge;

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
        // Query specific to Jorge (Guayaquil) fragment
        String sql = "SELECT * FROM FRAGMENTO_PROVEEDOR_GUAYAQUIL@dblink_jrevelo ORDER BY PROVEEDORID";
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
