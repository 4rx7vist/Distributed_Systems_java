package com.distribuidas.view;

import com.distribuidas.dao.*;
import com.distribuidas.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

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
        tabPane.getStyleClass().add("tab-pane");

        // Products Tab
        TableView<Producto> prodTable = createProductoTable();
        tabPane.getTabs().add(createTab("Productos", createCrudLayout(prodTable, "Producto",
                () -> showProductoDialog(null, prodTable),
                p -> showProductoDialog(p, prodTable),
                p -> {
                    if (confirmDelete()) {
                        productoDAO.delete(p.getProductoId());
                        refreshTable(prodTable, productoDAO::getAll);
                    }
                })));

        // Clientes Tab
        TableView<Cliente> cliTable = createClienteTable();
        tabPane.getTabs().add(createTab("Clientes", createCrudLayout(cliTable, "Cliente",
                () -> showClienteDialog(null, cliTable),
                c -> showClienteDialog(c, cliTable),
                c -> {
                    if (confirmDelete()) {
                        clienteDAO.delete(c.getClienteId());
                        refreshTable(cliTable, clienteDAO::getAll);
                    }
                })));

        // Categorias Tab
        TableView<Categoria> catTable = createCategoriaTable();
        tabPane.getTabs().add(createTab("Categorias", createCrudLayout(catTable, "Categoría",
                () -> showCategoriaDialog(null, catTable),
                c -> showCategoriaDialog(c, catTable),
                c -> {
                    if (confirmDelete()) {
                        categoriaDAO.delete(c.getCategoriaId());
                        refreshTable(catTable, categoriaDAO::getAll);
                    }
                })));

        // Proveedores Tab
        TableView<Proveedor> provTable = createProveedorTable();
        tabPane.getTabs().add(createTab("Proveedores", createCrudLayout(provTable, "Proveedor",
                () -> showProveedorDialog(null, provTable),
                p -> showProveedorDialog(p, provTable),
                p -> {
                    if (confirmDelete()) {
                        proveedorDAO.delete(p.getProveedorId());
                        refreshTable(provTable, proveedorDAO::getAll);
                    }
                })));

        // Empleados Tab
        TableView<Empleado> empTable = createEmpleadoTable();
        tabPane.getTabs().add(createTab("Empleados", createCrudLayout(empTable, "Empleado",
                () -> showEmpleadoDialog(null, empTable),
                e -> showEmpleadoDialog(e, empTable),
                e -> {
                    if (confirmDelete()) {
                        empleadoDAO.delete(e.getEmpleadoId());
                        refreshTable(empTable, empleadoDAO::getAll);
                    }
                })));

        // Ordenes Tab
        tabPane.getTabs().add(createTab("Ordenes", createOrdenesLayout()));

        // Audit Tab
        tabPane.getTabs().add(createTab("Auditoria", createAuditoriaTable()));

        setCenter(tabPane);

        // Header
        HBox header = new HBox(20);
        header.getStyleClass().add("header-panel");
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Gestión Distribuidas - Guayaquil");
        title.getStyleClass().add("header-title");

        // Sucursal Selector (Placeholder for fragmentation)
        ComboBox<String> cmbSucursal = new ComboBox<>();
            //cmbSucursal.getItems().addAll("Sucursal Central (Master)", "Sucursal Quito", "Sucursal Guayaquil");
        cmbSucursal.getSelectionModel().selectFirst();
        cmbSucursal.setStyle("-fx-base: #ecf0f1;");

        header.getChildren().addAll(title, new Separator(javafx.geometry.Orientation.VERTICAL), new Label("Vista:"),
                cmbSucursal);
        setTop(header);
    }

    private void refreshTable(TableView table, java.util.function.Supplier<javafx.collections.ObservableList> loader) {
        try {
            table.setItems(loader.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Está seguro de que desea eliminar este registro?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    // --- Dialogs ---

    private void showProductoDialog(Producto p, TableView<Producto> table) {
        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle(p == null ? "Nuevo Producto" : "Editar Producto");

        ButtonType saveBtnType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField txtId = new TextField();
        TextField txtCatId = new TextField();
        TextField txtProvId = new TextField();
        TextField txtDesc = new TextField();
        TextField txtPrecio = new TextField();
        TextField txtExist = new TextField();

        if (p != null) {
            txtId.setText(String.valueOf(p.getProductoId()));
            txtId.setDisable(true); // Can't change ID on edit
            txtCatId.setText(String.valueOf(p.getCategoriaId()));
            txtProvId.setText(String.valueOf(p.getProveedorId()));
            txtDesc.setText(p.getDescripcion());
            txtPrecio.setText(String.valueOf(p.getPrecioUnit()));
            txtExist.setText(String.valueOf(p.getExistencia()));
        }

        grid.add(new Label("ID Producto:"), 0, 0);
        grid.add(txtId, 1, 0);
        grid.add(new Label("ID Categoría:"), 0, 1);
        grid.add(txtCatId, 1, 1);
        grid.add(new Label("ID Proveedor:"), 0, 2);
        grid.add(txtProvId, 1, 2);
        grid.add(new Label("Descripción:"), 0, 3);
        grid.add(txtDesc, 1, 3);
        grid.add(new Label("Precio:"), 0, 4);
        grid.add(txtPrecio, 1, 4);
        grid.add(new Label("Existencia:"), 0, 5);
        grid.add(txtExist, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveBtnType) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    int catId = Integer.parseInt(txtCatId.getText());
                    int provId = Integer.parseInt(txtProvId.getText());
                    double precio = Double.parseDouble(txtPrecio.getText());
                    int exist = Integer.parseInt(txtExist.getText());
                    return new Producto(id, catId, provId, txtDesc.getText(), precio, exist);
                } catch (Exception e) {
                    showAlert("Error en datos: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (result == null)
                return;

            boolean success;
            if (p == null)
                success = productoDAO.save(result);
            else
                success = productoDAO.update(result);

            if (success)
                refreshTable(table, productoDAO::getAll);
            else
                showAlert("Error al guardar en base de datos");
        });
    }

    private void showClienteDialog(Cliente c, TableView<Cliente> table) {
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle(c == null ? "Nuevo Cliente" : "Editar Cliente");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField txtId = new TextField();
        TextField txtRuc = new TextField();
        TextField txtCia = new TextField();
        TextField txtContacto = new TextField();
        TextField txtDir = new TextField();
        TextField txtCel = new TextField();
        TextField txtCiudad = new TextField();

        if (c != null) {
            txtId.setText(String.valueOf(c.getClienteId()));
            txtId.setDisable(true);
            txtRuc.setText(c.getCedulaRuc());
            txtCia.setText(c.getNombreCia());
            txtContacto.setText(c.getNombreContacto());
            txtDir.setText(c.getDireccionCli());
            txtCel.setText(c.getCelular());
            txtCiudad.setText(c.getCiudadCli());
        }

        grid.addRow(0, new Label("ID:"), txtId);
        grid.addRow(1, new Label("RUC/Cedula:"), txtRuc);
        grid.addRow(2, new Label("Nombre Cía:"), txtCia);
        grid.addRow(3, new Label("Contacto:"), txtContacto);
        grid.addRow(4, new Label("Dirección:"), txtDir);
        grid.addRow(5, new Label("Celular:"), txtCel);
        grid.addRow(6, new Label("Ciudad:"), txtCiudad);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return new Cliente(Integer.parseInt(txtId.getText()), txtRuc.getText(), txtCia.getText(),
                            txtContacto.getText(), txtDir.getText(), txtCel.getText(), txtCiudad.getText());
                } catch (Exception e) {
                    showAlert("Error en datos: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (result != null) {
                boolean ok = (c == null) ? clienteDAO.save(result) : clienteDAO.update(result);
                if (ok)
                    refreshTable(table, clienteDAO::getAll);
                else
                    showAlert("Error al guardar cliente.");
            }
        });
    }

    private void showCategoriaDialog(Categoria c, TableView<Categoria> table) {
        Dialog<Categoria> dialog = new Dialog<>();
        dialog.setTitle(c == null ? "Nueva Categoría" : "Editar Categoría");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField txtId = new TextField();
        TextField txtNom = new TextField();
        if (c != null) {
            txtId.setText(String.valueOf(c.getCategoriaId()));
            txtId.setDisable(true);
            txtNom.setText(c.getNombreCat());
        }

        grid.addRow(0, new Label("ID:"), txtId);
        grid.addRow(1, new Label("Nombre:"), txtNom);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return new Categoria(Integer.parseInt(txtId.getText()), txtNom.getText());
                } catch (Exception e) {
                    showAlert("Error en datos: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(res -> {
            if (res != null) {
                boolean ok = (c == null ? categoriaDAO.save(res) : categoriaDAO.update(res));
                if (ok)
                    refreshTable(table, categoriaDAO::getAll);
                else
                    showAlert("Error al guardar categoría.");
            }
        });
    }

    private void showProveedorDialog(Proveedor p, TableView<Proveedor> table) {
        Dialog<Proveedor> dialog = new Dialog<>();
        dialog.setTitle(p == null ? "Nuevo Proveedor" : "Editar Proveedor");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField txtId = new TextField();
        TextField txtNom = new TextField();
        TextField txtCont = new TextField();
        TextField txtCel = new TextField();
        TextField txtCiu = new TextField();

        if (p != null) {
            txtId.setText(String.valueOf(p.getProveedorId()));
            txtId.setDisable(true);
            txtNom.setText(p.getNombreProv());
            txtCont.setText(p.getContacto());
            txtCel.setText(p.getCeluProv());
            txtCiu.setText(p.getCiudadProv());
        }

        grid.addRow(0, new Label("ID:"), txtId);
        grid.addRow(1, new Label("Nombre:"), txtNom);
        grid.addRow(2, new Label("Contacto:"), txtCont);
        grid.addRow(3, new Label("Celular:"), txtCel);
        grid.addRow(4, new Label("Ciudad:"), txtCiu);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return new Proveedor(Integer.parseInt(txtId.getText()), txtNom.getText(), txtCont.getText(),
                            txtCel.getText(), txtCiu.getText());
                } catch (Exception e) {
                    showAlert("Error en datos: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(res -> {
            if (res != null) {
                boolean ok = (p == null ? proveedorDAO.save(res) : proveedorDAO.update(res));
                if (ok)
                    refreshTable(table, proveedorDAO::getAll);
                else
                    showAlert("Error al guardar proveedor.");
            }
        });
    }

    private void showEmpleadoDialog(Empleado e, TableView<Empleado> table) {
        Dialog<Empleado> dialog = new Dialog<>();
        dialog.setTitle(e == null ? "Nuevo Empleado" : "Editar Empleado");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField txtId = new TextField();
        TextField txtNom = new TextField();
        TextField txtApe = new TextField();
        DatePicker dpNac = new DatePicker();

        if (e != null) {
            txtId.setText(String.valueOf(e.getEmpleadoId()));
            txtId.setDisable(true);
            txtNom.setText(e.getNombre());
            txtApe.setText(e.getApellido());
            if (e.getFechaNac() != null)
                dpNac.setValue(e.getFechaNac().toLocalDate());
        }

        grid.addRow(0, new Label("ID:"), txtId);
        grid.addRow(1, new Label("Nombre:"), txtNom);
        grid.addRow(2, new Label("Apellido:"), txtApe);
        grid.addRow(3, new Label("F. Nacimiento:"), dpNac);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    java.sql.Date date = dpNac.getValue() != null ? java.sql.Date.valueOf(dpNac.getValue()) : null;
                    return new Empleado(Integer.parseInt(txtId.getText()), null, txtNom.getText(), txtApe.getText(),
                            date, null);
                } catch (Exception ex) {
                    showAlert("Datos inválidos: " + ex.getMessage());
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(res -> {
            if (res != null) {
                boolean ok = (e == null) ? empleadoDAO.save(res) : empleadoDAO.update(res);
                if (ok)
                    refreshTable(table, empleadoDAO::getAll);
                else
                    showAlert("Error al guardar empleado.");
            }
        });
    }

    private Tab createTab(String title, Node content) {
        Tab tab = new Tab(title);
        tab.setContent(content);
        return tab;
    }

    /**
     * Creates a standard CRUD layout with a Toolbar and a Table.
     */
    private <T> VBox createCrudLayout(TableView<T> table, String entityName,
            Runnable onAdd, Consumer<T> onEdit, Consumer<T> onDelete) {
        VBox layout = new VBox();
        layout.setSpacing(0);

        // Toolbar
        HBox toolbar = new HBox(10);
        toolbar.getStyleClass().add("crud-toolbar");
        toolbar.setAlignment(Pos.CENTER_LEFT);

        Button btnAdd = new Button("Nuevo " + entityName);
        btnAdd.setOnAction(e -> onAdd.run());

        Button btnEdit = new Button("Editar");
        btnEdit.setOnAction(e -> {
            T selected = table.getSelectionModel().getSelectedItem();
            if (selected != null)
                onEdit.accept(selected);
            else
                showAlert("Selecciona un elemento para editar.");
        });

        Button btnDelete = new Button("Eliminar");
        btnDelete.getStyleClass().add("button-delete");
        btnDelete.setOnAction(e -> {
            T selected = table.getSelectionModel().getSelectedItem();
            if (selected != null)
                onDelete.accept(selected);
            else
                showAlert("Selecciona un elemento para eliminar.");
        });

        toolbar.getChildren().addAll(btnAdd, btnEdit, btnDelete);

        // Table
        VBox.setVgrow(table, Priority.ALWAYS);

        layout.getChildren().addAll(toolbar, table);
        return layout;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.show();
    }

    // --- Table Creation Helpers (Keeping columns as defined before) ---

    private TableView<Producto> createProductoTable() {
        TableView<Producto> table = new TableView<>();
        table.getColumns().add(createColumn("ID", "productoId", 50));
        table.getColumns().add(createColumn("Descripcion", "descripcion", 200));
        table.getColumns().add(createColumn("Precio", "precioUnit", 100));
        table.getColumns().add(createColumn("Existencia", "existencia", 80));
        try {
            table.setItems(productoDAO.getAll());
        } catch (Exception e) {
        }
        return table;
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
        table.getColumns().add(createColumn("F. Nacimiento", "fechaNac", 120));
        try {
            table.setItems(empleadoDAO.getAll());
        } catch (Exception e) {
        }
        return table;
    }

    private VBox createOrdenesLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Label lblMaster = new Label("Listado de Ordenes");
        lblMaster.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TableView<Orden> table = new TableView<>();
        table.getColumns().add(createColumn("Orden ID", "ordenId", 80));
        table.getColumns().add(createColumn("Cliente ID", "clienteId", 80));
        table.getColumns().add(createColumn("Emp ID", "empleadoId", 80));
        table.getColumns().add(createColumn("Fecha", "fechaOrden", 120));
        VBox.setVgrow(table, Priority.ALWAYS);
        table.setPlaceholder(new Label("Seleccione una orden para ver detalles"));

        Label lblDetail = new Label("Detalle de la Orden");
        lblDetail.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        TableView<DetalleOrden> detailTable = new TableView<>();
        detailTable.getColumns().add(createColumn("Producto ID", "productoId", 100));
        detailTable.getColumns().add(createColumn("Cantidad", "cantidad", 100));
        VBox.setVgrow(detailTable, Priority.ALWAYS);

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                detailTable.setItems(ordenDAO.getDetalles(newVal.getOrdenId()));
            }
        });

        try {
            table.setItems(ordenDAO.getAll());
        } catch (Exception e) {
        }

        // Add a simple toolbar for orders too?
        Button btnRefresh = new Button("Refrescar");
        btnRefresh.setOnAction(e -> {
            try {
                table.setItems(ordenDAO.getAll());
            } catch (Exception ex) {
            }
        });

        layout.getChildren().addAll(lblMaster, btnRefresh, table, lblDetail, detailTable);
        return layout;
    }

    private TableView<Auditoria> createAuditoriaTable() {
        TableView<Auditoria> table = new TableView<>();
        table.getStyleClass().add("audit-table");
        table.getColumns().add(createColumn("User", "userName", 100));
        table.getColumns().add(createColumn("Fecha", "fecha", 150));
        table.getColumns().add(createColumn("Op", "tipoOperacion", 50));
        table.getColumns().add(createColumn("Tabla", "nombreTable", 120));
        table.getColumns().add(createColumn("Anterior", "anterior", 250));
        table.getColumns().add(createColumn("Nuevo", "nuevo", 250));

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
