package com.distribuidas.view;

import com.distribuidas.dao.*;
import com.distribuidas.model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {

    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    private CategoriaDAO categoriaDAO;
    private ProveedorDAO proveedorDAO;
    private EmpleadoDAO empleadoDAO;
    private OrdenDAO ordenDAO;
    private AuditoriaDAO auditoriaDAO;

    public MainView() {
        // Initialize DAOs
        productoDAO = new ProductoDAO();
        clienteDAO = new ClienteDAO();
        categoriaDAO = new CategoriaDAO();
        proveedorDAO = new ProveedorDAO();
        empleadoDAO = new EmpleadoDAO();
        ordenDAO = new OrdenDAO();
        auditoriaDAO = new AuditoriaDAO();

        // UI Setup
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        tabPane.getTabs().addAll(
                createTab("Productos", createProductoTable()),
                createTab("Clientes", createClienteTable()),
                createTab("Categorias", createCategoriaTable()),
                createTab("Proveedores", createProveedorTable()),
                createTab("Empleados", createEmpleadoTable()),
                createTab("Ordenes", createOrdenTable()),
                createTab("Auditoria", createAuditoriaTable()));

        setCenter(tabPane);

        // Header
        Label title = new Label("Gestión Distribuidas - Oracle");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        title.setPadding(new Insets(10));
        setTop(title);
    }

    private Tab createTab(String title, javafx.scene.Node content) {
        Tab tab = new Tab(title);
        tab.setContent(content);
        return tab;
    }

    private TableView<Producto> createProductoTable() {
        TableView<Producto> table = new TableView<>();
        table.getColumns().add(createColumn("ID", "productoId", 50));
        table.getColumns().add(createColumn("Descripcion", "descripcion", 200));
        table.getColumns().add(createColumn("Precio", "precioUnit", 100));
        table.getColumns().add(createColumn("Existencia", "existencia", 80));

        // Refresh mechanism
        refreshProductoTable(table);
        return table;
    }

    private void refreshProductoTable(TableView<Producto> table) {
        try {
            table.setItems(productoDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TableView<Cliente> createClienteTable() {
        TableView<Cliente> table = new TableView<>();
        table.getColumns().add(createColumn("ID", "clienteId", 50));
        table.getColumns().add(createColumn("Nombre Cia", "nombreCia", 150));
        table.getColumns().add(createColumn("Contacto", "nombreContacto", 150));
        table.getColumns().add(createColumn("Ciudad", "ciudadCli", 100));

        try {
            table.setItems(clienteDAO.getAll());
        } catch (Exception e) {
        }
        return table;
    }

    private TableView<Categoria> createCategoriaTable() {
        TableView<Categoria> table = new TableView<>();
        table.getColumns().add(createColumn("ID", "categoriaId", 50));
        table.getColumns().add(createColumn("Nombre", "nombreCat", 200));
        try {
            table.setItems(categoriaDAO.getAll());
        } catch (Exception e) {
        }
        return table;
    }

    private TableView<Proveedor> createProveedorTable() {
        TableView<Proveedor> table = new TableView<>();
        table.getColumns().add(createColumn("ID", "proveedorId", 50));
        table.getColumns().add(createColumn("Nombre", "nombreProv", 150));
        table.getColumns().add(createColumn("Ciudad", "ciudadProv", 100));
        try {
            table.setItems(proveedorDAO.getAll());
        } catch (Exception e) {
        }
        return table;
    }

    private TableView<Empleado> createEmpleadoTable() {
        TableView<Empleado> table = new TableView<>();
        table.getColumns().add(createColumn("ID", "empleadoId", 50));
        table.getColumns().add(createColumn("Nombre", "nombre", 100));
        table.getColumns().add(createColumn("Apellido", "apellido", 100));
        try {
            table.setItems(empleadoDAO.getAll());
        } catch (Exception e) {
        }
        return table;
    }

    private VBox createOrdenTable() {
        TableView<Orden> table = new TableView<>();
        table.getColumns().add(createColumn("Orden ID", "ordenId", 70));
        table.getColumns().add(createColumn("Cliente ID", "clienteId", 70));
        table.getColumns().add(createColumn("Emp ID", "empleadoId", 70));
        table.getColumns().add(createColumn("Fecha", "fechaOrden", 100));

        TableView<DetalleOrden> detailTable = new TableView<>();
        detailTable.getColumns().add(createColumn("Prop ID", "productoId", 70));
        detailTable.getColumns().add(createColumn("Cant", "cantidad", 70));

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                detailTable.setItems(ordenDAO.getDetalles(newVal.getOrdenId()));
            }
        });

        try {
            table.setItems(ordenDAO.getAll());
        } catch (Exception e) {
        }

        VBox layout = new VBox(10, new Label("Ordenes"), table, new Label("Detalle Orden"), detailTable);
        layout.setPadding(new Insets(10));
        return layout;
    }

    private TableView<Auditoria> createAuditoriaTable() {
        TableView<Auditoria> table = new TableView<>();
        table.getColumns().add(createColumn("User", "userName", 80));
        table.getColumns().add(createColumn("Fecha", "fecha", 120));
        table.getColumns().add(createColumn("Op", "tipoOperacion", 50));
        table.getColumns().add(createColumn("Tabla", "nombreTable", 100));
        table.getColumns().add(createColumn("Anterior", "anterior", 200));
        table.getColumns().add(createColumn("Nuevo", "nuevo", 200));

        // Refresh button for Audit
        table.setPlaceholder(new Label("No audit logs found"));
        // Simulating auto-refresh on load
        try {
            table.setItems(auditoriaDAO.getAll());
        } catch (Exception e) {
        }

        return table;
    }

    private <S, T> TableColumn<S, T> createColumn(String title, String property, double width) {
        TableColumn<S, T> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        col.setMinWidth(width);
        return col;
    }
}
