package com.distribuidas.dao;

import com.distribuidas.db.DatabaseConnection;
import com.distribuidas.model.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoriaDAO {

    public ObservableList<Categoria> getAll() {
        ObservableList<Categoria> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CATEGORIAS ORDER BY CATEGORIAID";
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("CATEGORIAID"), rs.getString("NOMBRECAT")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Additional methods (insert, update, delete) if needed, but categories are
    // usually static ref data in this context.
    // Implementing basic ones for completeness if requested.
}
