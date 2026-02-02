package com.distribuidas.dao.jorge;

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
        // Query specific to Jorge (Guayaquil) fragment
        String sql = "SELECT * FROM FRAGMENTO_CLIENTES_GUAYAQUIL@dblink_jrevelo ORDER BY CLIENTEID";
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
}
