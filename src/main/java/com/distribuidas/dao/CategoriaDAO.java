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
        String sql = "SELECT * FROM VMCATEGORIAS ORDER BY CATEGORIAID";
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

    public boolean save(Categoria c) {
        String sql = "INSERT INTO VMCATEGORIAS (CATEGORIAID, NOMBRECAT) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getCategoriaId());
            stmt.setString(2, c.getNombreCat());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Categoria c) {
        String sql = "UPDATE VMCATEGORIAS SET NOMBRECAT=? WHERE CATEGORIAID=?";
        try (Connection conn = DatabaseConnection.getConnection();
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNombreCat());
            stmt.setInt(2, c.getCategoriaId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM CATEGORIAS WHERE CATEGORIAID=?";
        try (Connection conn = DatabaseConnection.getConnection();
                java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
