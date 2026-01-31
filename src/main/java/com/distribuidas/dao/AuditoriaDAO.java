package com.distribuidas.dao;

import com.distribuidas.db.DatabaseConnection;
import com.distribuidas.model.Auditoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditoriaDAO {

    public ObservableList<Auditoria> getAll() {
        ObservableList<Auditoria> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM AUDITORIA ORDER BY FECHA DESC";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Auditoria(
                        rs.getString("USER_NAME"),
                        rs.getDate("FECHA"),
                        rs.getString("TIPO_OPERACION"),
                        rs.getString("NOMBRE_TABLE"),
                        rs.getString("ANTERIOR"),
                        rs.getString("NUEVO")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
