package com.distribuidas.dao;

import com.distribuidas.db.DatabaseConnection;
import com.distribuidas.model.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadoDAO {

    public ObservableList<Empleado> getAll() {
        ObservableList<Empleado> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM EMPLEADOS ORDER BY EMPLEADOID";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // reporta_a can be null
                Integer reportaA = rs.getInt("REPORTA_A");
                if (rs.wasNull())
                    reportaA = null;

                Integer extension = rs.getInt("EXTENSION");
                if (rs.wasNull())
                    extension = null;

                lista.add(new Empleado(
                        rs.getInt("EMPLEADOID"),
                        reportaA,
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getDate("FECHA_NAC"),
                        extension));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
