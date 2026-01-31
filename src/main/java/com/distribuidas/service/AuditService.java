package com.distribuidas.service;

import com.distribuidas.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AuditService {

    public void initializeAudit() {
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement()) {

            // 1. Create AUDITORIA table if not exists
            try {
                stmt.execute("SELECT 1 FROM AUDITORIA WHERE 1=0");
            } catch (SQLException e) {
                // Table likely doesn't exist, create it
                System.out.println("Creating AUDITORIA table...");
                String createTable = "CREATE TABLE AUDITORIA (" +
                        "user_name VARCHAR2(20), " +
                        "fecha DATE, " +
                        "tipo_operacion VARCHAR2(1), " +
                        "nombre_table VARCHAR2(30), " +
                        "anterior VARCHAR2(800), " +
                        "nuevo VARCHAR2(800))";
                stmt.executeUpdate(createTable);
                System.out.println("AUDITORIA table created.");
            }

            // 2. Create Triggers for each table
            // We use a helper method to robustly create triggers (dropping if exists for
            // update)
            createTrigger(stmt, "CATEGORIAS", "CATEGORIAID || '|' || NOMBRECAT");
            createTrigger(stmt, "CLIENTES", "CLIENTEID || '|' || NOMBRECIA");
            createTrigger(stmt, "EMPLEADOS", "EMPLEADOID || '|' || APELIDO");
            createTrigger(stmt, "PROVEEDORES", "PROVEEDORID || '|' || NOMBREPROV");
            createTrigger(stmt, "PRODUCTOS", "PRODUCTOID || '|' || DESCRIPCION");
            createTrigger(stmt, "ORDENES", "ORDENID || '|' || FECHAORDEN");
            createTrigger(stmt, "DETALLE_ORDENES", "ORDENID || '-' || DETALLEID || '|' || PRODUCTOID");

        } catch (SQLException e) {
            System.err.println("Error initializing Audit: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createTrigger(Statement stmt, String tableName, String concatFields) throws SQLException {
        // We defer to specific trigger creation for safety
        createSpecificTrigger(stmt, tableName);
    }

    private void createSpecificTrigger(Statement stmt, String tableName) throws SQLException {
        String triggerBody = "";
        switch (tableName) {
            case "CATEGORIAS":
                triggerBody = buildTriggerBody(tableName, "CATEGORIAID || '|' || NOMBRECAT");
                break;
            case "CLIENTES":
                triggerBody = buildTriggerBody(tableName, "CLIENTEID || '|' || NOMBRECIA");
                break;
            case "EMPLEADOS":
                triggerBody = buildTriggerBody(tableName, "EMPLEADOID || '|' || APELLIDO");
                break;
            case "PROVEEDORES":
                triggerBody = buildTriggerBody(tableName, "PROVEEDORID || '|' || NOMBREPROV");
                break;
            case "PRODUCTOS":
                triggerBody = buildTriggerBody(tableName, "PRODUCTOID || '|' || DESCRIPCION");
                break;
            case "ORDENES":
                triggerBody = buildTriggerBody(tableName, "ORDENID || '|' || FECHAORDEN");
                break;
            case "DETALLE_ORDENES":
                triggerBody = buildTriggerBody(tableName, "ORDENID || '-' || DETALLEID || '|' || PRODUCTOID");
                break;
        }

        if (!triggerBody.isEmpty()) {
            stmt.executeUpdate(triggerBody);
            System.out.println("Trigger for " + tableName + " updated.");
        }
    }

    private String buildTriggerBody(String tableName, String fields) {
        // Need to prefix fields with :NEW or :OLD
        // This simple string manipulation is fragile.
        // Let's just create standard robust SQL strings.

        String oldFields = fields.replaceAll("([A-Z_]+)", ":OLD.$1");
        String newFields = fields.replaceAll("([A-Z_]+)", ":NEW.$1");

        // Fix for reserved words or partial matches if any (simple regex might be safe
        // enough for these specific column names)

        return "CREATE OR REPLACE TRIGGER TRG_AUDIT_" + tableName + " " +
                "AFTER INSERT OR UPDATE OR DELETE ON " + tableName + " " +
                "FOR EACH ROW " +
                "BEGIN " +
                "  IF INSERTING THEN " +
                "    INSERT INTO AUDITORIA VALUES (USER, SYSDATE, 'I', '" + tableName + "', NULL, " + newFields + "); "
                +
                "  ELSIF UPDATING THEN " +
                "    INSERT INTO AUDITORIA VALUES (USER, SYSDATE, 'U', '" + tableName + "', " + oldFields + ", "
                + newFields + "); " +
                "  ELSIF DELETING THEN " +
                "    INSERT INTO AUDITORIA VALUES (USER, SYSDATE, 'D', '" + tableName + "', " + oldFields + ", NULL); "
                +
                "  END IF; " +
                "END;";
    }
}
