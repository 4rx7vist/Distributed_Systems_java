package com.distribuidas.dao.master;

import com.distribuidas.db.DatabaseConnection;
import com.distribuidas.model.Orden;
import com.distribuidas.model.DetalleOrden;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdenDAO {

    public ObservableList<Orden> getAll() {
        ObservableList<Orden> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ORDENES ORDER BY ORDENID";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Integer descuento = rs.getInt("DESCUENTO");
                if (rs.wasNull())
                    descuento = null;

                lista.add(new Orden(
                        rs.getInt("ORDENID"),
                        rs.getInt("CLIENTEID"),
                        rs.getInt("EMPLEADOID"),
                        rs.getDate("FECHAORDEN"),
                        descuento));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ObservableList<DetalleOrden> getDetalles(int ordenId) {
        ObservableList<DetalleOrden> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM DETALLE_ORDENES WHERE ORDENID = ? ORDER BY DETALLEID";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ordenId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DetalleOrden(
                            rs.getInt("ORDENID"),
                            rs.getInt("DETALLEID"),
                            rs.getInt("PRODUCTOID"),
                            rs.getInt("CANTIDAD")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
