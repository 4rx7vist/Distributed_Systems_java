package com.distribuidas.dao;

import com.distribuidas.db.DatabaseConnection;
import com.distribuidas.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

public class ClienteDAO {

    public ObservableList<Cliente> getAll() {
        ObservableList<Cliente> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM FRAGMENTO_CLIENTES_GUAYAQUIL ORDER BY CLIENTEID";
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
        String sql = "INSERT INTO FRAGMENTO_CLIENTES_GUAYAQUIL VALUES (?, ?, ?, ?, ?, ?, ?)"; // Assumes order matches table def
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
        String sql = "UPDATE FRAGMENTO_CLIENTES_GUAYAQUIL SET CEDULA_RUC=?, NOMBRECIA=?, NOMBRECONTACTO=?, DIRECCIONCLI=?, CELULAR=?, CIUDADCLI=? WHERE CLIENTEID=?";
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
        String sql = "DELETE FROM FRAGMENTO_CLIENTES_GUAYAQUIL WHERE CLIENTEID=?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para suscribirse a notificaciones de cambio en tiempo real (Oracle
    // DCN)
    public void subscribeToUpdates(Runnable onUpdate) {
        try {
            // Usamos una conexión dedicada para mantener vivo el listener
            Connection conn = DatabaseConnection.getDedicatedConnection();

            if (conn.isWrapperFor(OracleConnection.class)) {
                OracleConnection oracleConn = conn.unwrap(OracleConnection.class);

                Properties prop = new Properties();
                // Optimización: Si quisieramos solo los ROWIDs cambiados
                prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
                prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION, "true");

                DatabaseChangeRegistration dcr = oracleConn.registerDatabaseChangeNotification(prop);

                dcr.addListener(new DatabaseChangeListener() {
                    @Override
                    public void onDatabaseChangeNotification(DatabaseChangeEvent e) {
                        System.out.println("Notificación de cambio recibida de Oracle: " + e.toString());
                        // Ejecutar la actualización en el hilo de JavaFX
                        javafx.application.Platform.runLater(onUpdate);
                    }
                });

                // Registrar la consulta
                try (Statement stmt = oracleConn.createStatement()) {
                    ((OracleStatement) stmt).setDatabaseChangeRegistration(dcr);
                    // Esta consulta registra el interés en la tabla CLIENTES
                    stmt.executeQuery("SELECT * FROM FRAGMENTO_CLIENTES_GUAYAQUIL");
                }

                System.out.println("Suscripción a notificaciones DCN exitosa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al registrar Oracle DCN: " + e.getMessage());
        }
    }
}
