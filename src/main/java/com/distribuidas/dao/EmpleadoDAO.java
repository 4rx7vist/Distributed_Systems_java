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
        // Use the database view that exposes only the required fragment
        String sql = "SELECT EMPLEADOID, NOMBRE, APELLIDO, FECHA_NAC FROM INF_EMPLEADOS_PERSONAL ORDER BY EMPLEADOID";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // The view provides only the reduced set; leave other fields null
                lista.add(new Empleado(
                        rs.getInt("EMPLEADOID"),
                        null, // reportaA not available in view
                        rs.getString("NOMBRE"),
                        rs.getString("APELLIDO"),
                        rs.getDate("FECHA_NAC"),
                        null // extension not available in view
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean save(Empleado e) {
        String sql = "INSERT INTO INF_EMPLEADOS_PERSONAL (EMPLEADOID, REPORTA_A, NOMBRE, APELLIDO, FECHA_NAC, EXTENSION) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, e.getEmpleadoId());
            if (e.getReportaA() != null)
                stmt.setInt(2, e.getReportaA());
            else
                stmt.setNull(2, java.sql.Types.INTEGER);
            stmt.setString(3, e.getNombre());
            stmt.setString(4, e.getApellido());
            stmt.setDate(5, e.getFechaNac());
            if (e.getExtension() != null)
                stmt.setInt(6, e.getExtension());
            else
                stmt.setNull(6, java.sql.Types.INTEGER);
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(Empleado e) {
        String sql = "UPDATE INF_EMPLEADOS_PERSONAL SET REPORTA_A=?, NOMBRE=?, APELLIDO=?, FECHA_NAC=?, EXTENSION=? WHERE EMPLEADOID=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (e.getReportaA() != null)
                stmt.setInt(1, e.getReportaA());
            else
                stmt.setNull(1, java.sql.Types.INTEGER);
            stmt.setString(2, e.getNombre());
            stmt.setString(3, e.getApellido());
            stmt.setDate(4, e.getFechaNac());
            if (e.getExtension() != null)
                stmt.setInt(5, e.getExtension());
            else
                stmt.setNull(5, java.sql.Types.INTEGER);
            stmt.setInt(6, e.getEmpleadoId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM INF_EMPLEADOS_PERSONAL WHERE EMPLEADOID=?";
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
