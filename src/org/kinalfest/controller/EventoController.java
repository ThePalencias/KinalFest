package org.kinalfest.controller;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.kinalfest.dao.impl.EventoDaoImpl;
import org.kinalfest.model.Evento;
import org.kinalfest.util.Alertas;
import java.time.LocalDate;
/** Registro de eventos y asignación transaccional de recursos. */
public class EventoController {
    @FXML private BorderPane raiz;
    private final EventoDaoImpl dao=new EventoDaoImpl();
    private final TableView<Evento> tabla=new TableView<>();
    private final TextField lugar=new TextField(),organizador=new TextField(),descripcion=new TextField(),
        costo=new TextField(),aforo=new TextField(),afiche=new TextField(),
        idEvento=new TextField(),idRecurso=new TextField(),cantidad=new TextField();
    private final DatePicker fecha=new DatePicker(LocalDate.now().plusDays(1));
    private void fila(GridPane g,int row,String nombre,javafx.scene.Node control) {
        g.add(new Label(nombre),0,row);g.add(control,1,row);
    }
    @FXML private void initialize() {
        VBox superior=new VBox(10);superior.setStyle("-fx-padding: 12;");
        Label titulo=new Label("Eventos y recursos");titulo.setStyle("-fx-font-size: 23px; -fx-font-weight: bold;");
        GridPane g=new GridPane();g.setHgap(12);g.setVgap(7);
        fila(g,0,"ID lugar",lugar);fila(g,1,"ID organizador",organizador);fila(g,2,"Fecha",fecha);
        fila(g,3,"Descripción",descripcion);fila(g,4,"Costo entrada",costo);
        fila(g,5,"Aforo esperado",aforo);fila(g,6,"Referencia de afiche (opcional)",afiche);
        Button registrar=new Button("Registrar evento");registrar.setOnAction(e->registrar());
        HBox asignacion=new HBox(8,new Label("ID evento"),idEvento,new Label("ID recurso"),idRecurso,new Label("Cantidad"),cantidad);
        Button asignar=new Button("Asignar recurso");asignar.setOnAction(e->asignar());
        Button recargar=new Button("Recargar tabla");recargar.setOnAction(e->cargar());
        superior.getChildren().addAll(titulo,g,registrar,new Separator(),new Label("Asignación de recursos (descuenta stock)"),asignacion,asignar,recargar);
        raiz.setTop(superior);
        for(String nombre:new String[]{"idEvento","idLugar","idOrganizador","fechaEvento","descripcion","costoEntrada","estado"}) {
            TableColumn<Evento,String> col=new TableColumn<>(nombre);col.setPrefWidth(145);
            col.setCellValueFactory(d->{
                Evento x=d.getValue();
                return new ReadOnlyStringWrapper(switch(nombre) {
                    case "idEvento" -> ""+x.getIdEvento();
                    case "idLugar" -> ""+x.getIdLugar();
                    case "idOrganizador" -> ""+x.getIdOrganizador();
                    case "fechaEvento" -> ""+x.getFechaEvento();
                    case "descripcion" -> x.getDescripcion();
                    case "costoEntrada" -> ""+x.getCostoEntrada();
                    default -> x.getEstado();
                });
            });tabla.getColumns().add(col);
        }
        tabla.getSelectionModel().selectedItemProperty().addListener((o,a,b)->{
            if(b!=null)idEvento.setText(""+b.getIdEvento());
        });
        raiz.setCenter(tabla);cargar();
    }
    private int positivo(TextField f) {
        int n=Integer.parseInt(f.getText().trim());
        if(n<=0)throw new IllegalArgumentException("Los identificadores y cantidades deben ser mayores a cero");
        return n;
    }
    private void registrar() {
        try {
            if(fecha.getValue()==null||descripcion.getText().isBlank()) throw new IllegalArgumentException("Completa fecha y descripción");
            double monto=Double.parseDouble(costo.getText().trim());
            int esperado=Integer.parseInt(aforo.getText().trim());
            if(monto<0||esperado<0)throw new IllegalArgumentException("Costo y aforo no pueden ser negativos");
            dao.registrar(positivo(lugar),positivo(organizador),fecha.getValue(),descripcion.getText().trim(),
                monto,esperado,afiche.getText().trim());
            cargar();Alertas.info("KinalFest","Evento registrado");
        } catch(Exception e) { Alertas.error(e); }
    }
    private void asignar() {
        try {
            dao.asignarRecurso(positivo(idEvento),positivo(idRecurso),positivo(cantidad));
            cargar();Alertas.info("KinalFest","Recurso asignado y stock actualizado");
        } catch(Exception e) { Alertas.error(e); }
    }
    private void cargar() {
        try {tabla.setItems(FXCollections.observableArrayList(dao.listar()));}
        catch(Exception e){Alertas.error(e);}
    }
}
