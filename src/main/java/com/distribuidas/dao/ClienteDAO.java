package com.distribuidas.dao;

import com.distribuidas.db.DatabaseConnection;
import com.distribuidas.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    public ObservableList<Cliente> getAll() {
        ObservableList<Cliente> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM CLIENTES ORDER BY CLIENTEID";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("CLIENTEID"),
                        rs.getString("CEDULA_RUC"),
                        rs.getString("NOMBRECIA"),
                        rs.getString("NOMBRECONTACTO"),
                        rs.getString("DIRECCIONCLI"),
                        rs.getString("CELULAR"),
                        rs.getString("CIUDADCLI")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Additional methods (save, update, delete) would follow similar pattern
    public boolean save(Cliente c) {
        String sql = "INSERT INTO CLIENTES VALUES (?, ?, ?, ?, ?, ?, ?)"; // Assumes order matches table def
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, c.getClienteId());
            stmt.setString(2, c.getCedulaRuc());
            stmt.setString(3, c.getNombreCia());
            stmt.setString(4, c.getNombreContacto());
            stmt.setString(5, c.getDireccionCli());
            stmt.setString(6, c.getCelular());
            stmt.setString(7, c.getCiudadCli());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Cliente c) {
        String sql = "UPDATE CLIENTES SET CEDULA_RUC=?, NOMBRECIA=?, NOMBRECONTACTO=?, DIRECCIONCLI=?, CELULAR=?, CIUDADCLI=? WHERE CLIENTEID=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getCedulaRuc());
            stmt.setString(2, c.getNombreCia());
            stmt.setString(3, c.getNombreContacto());
            stmt.setString(4, c.getDireccionCli());
            stmt.setString(5, c.getCelular());
            stmt.setString(6, c.getCiudadCli());
            stmt.setInt(7, c.getClienteId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM CLIENTES WHERE CLIENTEID=?";
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
