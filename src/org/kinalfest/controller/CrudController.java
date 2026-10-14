package org.kinalfest.controller;
import java.lang.reflect.*;
import java.util.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.kinalfest.dao.CrudDao;
import org.kinalfest.dao.impl.*;
import org.kinalfest.model.*;
import org.kinalfest.util.Alertas;
/** Vista reutilizable con TableView y ObservableList para las cuatro entidades CRUD. */
public class CrudController {
    public static String entidad="Asistente";
    @FXML private BorderPane raiz;
    private CrudDao dao;
    private Class<?> tipo;
    private TableView<Object> tabla=new TableView<>();
    private Map<String,TextField> entradas=new LinkedHashMap<>();
    private Field[] campos;
    @FXML private void initialize() {
        try {
            switch(entidad) {
                case "Asistente" -> { tipo=Asistente.class;dao=new AsistenteDaoImpl(); }
                case "Lugar" -> { tipo=Lugar.class;dao=new LugarDaoImpl(); }
                case "Organizador" -> { tipo=Organizador.class;dao=new OrganizadorDaoImpl(); }
                case "Recurso" -> { tipo=Recurso.class;dao=new RecursoDaoImpl(); }
                default -> throw new IllegalArgumentException("Entidad desconocida");
            }
            campos=tipo.getDeclaredFields();
            VBox formulario=new VBox(8);
            formulario.setStyle("-fx-padding: 12;");
            Label titulo=new Label("Administración de "+entidad);
            titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
            formulario.getChildren().add(titulo);
            GridPane grilla=new GridPane();grilla.setHgap(10);grilla.setVgap(8);
            int fila=0;
            for(Field campo:campos) {
                TextField input=new TextField();
                input.setPromptText(campo.getName());
                entradas.put(campo.getName(),input);
                grilla.addRow(fila++,new Label(campo.getName()+":"),input);
            }
            entradas.get(campos[0].getName()).setDisable(true);
            formulario.getChildren().add(grilla);
            HBox botones=new HBox(8);
            Button guardar=new Button("Agregar"),actualizar=new Button("Actualizar"),eliminar=new Button("Eliminar"),
                limpiar=new Button("Limpiar"),refrescar=new Button("Recargar");
            guardar.setOnAction(e->operar("agregar"));
            actualizar.setOnAction(e->operar("actualizar"));
            eliminar.setOnAction(e->operar("eliminar"));
            limpiar.setOnAction(e->limpiar());
            refrescar.setOnAction(e->cargar());
            botones.getChildren().addAll(guardar,actualizar,eliminar,limpiar,refrescar);
            formulario.getChildren().add(botones);
            for(Field campo:campos) {
                TableColumn<Object,String> col=new TableColumn<>(campo.getName());
                col.setCellValueFactory(data->{
                    try {
                        Method getter=tipo.getMethod("get"+capitalizar(campo.getName()));
                        return new ReadOnlyStringWrapper(String.valueOf(getter.invoke(data.getValue())));
                    } catch(Exception ex) { return new ReadOnlyStringWrapper(""); }
                });
                col.setPrefWidth(135);
                tabla.getColumns().add(col);
            }
            tabla.getSelectionModel().selectedItemProperty().addListener((obs,old,nuevo)->{
                if(nuevo!=null) {
                    for(Field campo:campos) try {
                        Method getter=tipo.getMethod("get"+capitalizar(campo.getName()));
                        Object valor=getter.invoke(nuevo);
                        entradas.get(campo.getName()).setText(String.valueOf(valor));
                    } catch(Exception ex) { Alertas.error(ex); }
                }
            });
            raiz.setTop(formulario);raiz.setCenter(tabla);cargar();
        } catch(Exception e) { Alertas.error(e); }
    }
    private static String capitalizar(String s) { return Character.toUpperCase(s.charAt(0))+s.substring(1); }
    private Object convertir(String valor,Class<?> tipo) {
        if(valor==null || valor.isBlank()) throw new IllegalArgumentException("Completa todos los campos");
        if(tipo==int.class) return Integer.parseInt(valor.trim());
        if(tipo==double.class) return Double.parseDouble(valor.trim());
        return valor.trim();
    }
    private Object construir() throws Exception {
        Object x=tipo.getConstructor().newInstance();
        for(Field campo:campos) {
            if(campo==campos[0] && entradas.get(campo.getName()).getText().isBlank()) continue;
            Method setter=tipo.getMethod("set"+capitalizar(campo.getName()),campo.getType());
            setter.invoke(x,convertir(entradas.get(campo.getName()).getText(),campo.getType()));
        }
        return x;
    }
    private void operar(String accion) {
        try {
            if(accion.equals("eliminar")) {
                Object sel=tabla.getSelectionModel().getSelectedItem();
                if(sel==null) throw new IllegalArgumentException("Selecciona un registro");
                Alert confirmacion=new Alert(Alert.AlertType.CONFIRMATION,"¿Eliminar el registro seleccionado?",ButtonType.YES,ButtonType.NO);
                if(confirmacion.showAndWait().orElse(ButtonType.NO)!=ButtonType.YES) return;
                int id=(int)tipo.getMethod("get"+capitalizar(campos[0].getName())).invoke(sel);
                dao.eliminar(id);
            } else if(accion.equals("agregar")) dao.insertar(construir());
            else {
                if(tabla.getSelectionModel().getSelectedItem()==null) throw new IllegalArgumentException("Selecciona un registro para actualizar");
                dao.actualizar(construir());
            }
            limpiar();cargar();Alertas.info("KinalFest","Operación completada");
        } catch(Exception e) { Alertas.error(e); }
    }
    private void cargar() {
        try { tabla.setItems(FXCollections.observableArrayList(dao.listar())); }
        catch(Exception e) { Alertas.error(e); }
    }
    private void limpiar() {
        entradas.values().forEach(t->t.clear());
        tabla.getSelectionModel().clearSelection();
    }
}
