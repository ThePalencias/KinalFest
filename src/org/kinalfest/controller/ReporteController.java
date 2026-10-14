package org.kinalfest.controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import org.kinalfest.dao.impl.EventoDaoImpl;
import org.kinalfest.model.Evento;
import org.kinalfest.util.Alertas;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
/** Reporte básico de eventos, filtrado por estado y exportación CSV. */
public class ReporteController {
    @FXML private BorderPane raiz;
    private final EventoDaoImpl dao=new EventoDaoImpl();
    private final TableView<Evento> tabla=new TableView<>();
    private final ComboBox<String> filtro=new ComboBox<>();
    private final Label resumen=new Label();
    private List<Evento> todos=List.of();
    @FXML private void initialize() {
        Label titulo=new Label("Reporte de eventos");titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        filtro.getItems().addAll("TODOS","PROGRAMADO","EN_REVISION","APROBADO","CONFIRMADO");
        filtro.setValue("TODOS");filtro.setOnAction(e->filtrar());
        Button actualizar=new Button("Actualizar");actualizar.setOnAction(e->cargar());
        Button exportar=new Button("Exportar CSV");exportar.setOnAction(e->exportar());
        VBox arriba=new VBox(12,titulo,new HBox(10,new Label("Estado:"),filtro,actualizar,exportar),resumen);
        arriba.setStyle("-fx-padding: 15;");raiz.setTop(arriba);
        for(String nombre:new String[]{"ID","Fecha","Descripción","Costo","Estado"}) {
            TableColumn<Evento,String> col=new TableColumn<>(nombre);col.setPrefWidth(170);
            col.setCellValueFactory(d->{
                Evento x=d.getValue();
                return new ReadOnlyStringWrapper(switch(nombre) {
                    case "ID" -> ""+x.getIdEvento();
                    case "Fecha" -> ""+x.getFechaEvento();
                    case "Descripción" -> x.getDescripcion();
                    case "Costo" -> ""+x.getCostoEntrada();
                    default -> x.getEstado();
                });
            });tabla.getColumns().add(col);
        }
        raiz.setCenter(tabla);cargar();
    }
    private void cargar() {
        try { todos=dao.listar();filtrar(); }
        catch(Exception e) { Alertas.error(e); }
    }
    private void filtrar() {
        String estado=filtro.getValue();
        List<Evento> visibles=todos.stream().filter(e->"TODOS".equals(estado)||estado.equals(e.getEstado())).collect(Collectors.toList());
        tabla.setItems(FXCollections.observableArrayList(visibles));
        double total=visibles.stream().mapToDouble(Evento::getCostoEntrada).sum();
        resumen.setText("Eventos: "+visibles.size()+"  |  Suma de precios de entrada: Q"+String.format("%.2f",total));
    }
    private static String csv(String s) { return "\""+s.replace("\"","\"\"")+"\""; }
    private void exportar() {
        FileChooser dialogo=new FileChooser();
        dialogo.setTitle("Guardar reporte");
        dialogo.setInitialFileName("reporte_kinalfest.csv");
        dialogo.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV","*.csv"));
        java.io.File destino=dialogo.showSaveDialog(raiz.getScene().getWindow());
        if(destino==null)return;
        List<String> lineas=new ArrayList<>();
        lineas.add("ID,Fecha,Descripcion,CostoEntrada,Estado");
        for(Evento e:tabla.getItems())
            lineas.add(e.getIdEvento()+","+e.getFechaEvento()+","+csv(e.getDescripcion())+","+e.getCostoEntrada()+","+csv(e.getEstado()));
        try { Files.write(destino.toPath(),lineas,StandardCharsets.UTF_8);Alertas.info("Reporte","Archivo CSV guardado"); }
        catch(Exception e) { Alertas.error(e); }
    }
}
