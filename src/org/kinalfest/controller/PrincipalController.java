package org.kinalfest.controller;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.kinalfest.system.Main;
import org.kinalfest.util.*;
public class PrincipalController {
    @FXML private Label sesion;
    @FXML private Button aprobacionBtn,ticketsBtn,eventosBtn;
    @FXML private StackPane contenido;
    @FXML private void initialize() {
        SessionContext s=SessionContext.actual();
        if(s==null) { Main.mostrar("LoginView.fxml");return; }
        sesion.setText("Usuario: "+s.getUsuario()+"  |  Rol: "+s.getRol()+"  |  Inicio: "+s.getInicio());
        aprobacionBtn.setDisable(!s.tieneRol("Director de Eventos"));
        ticketsBtn.setDisable(!s.tieneRol("Taquillero"));
        eventosBtn.setDisable(!(s.tieneRol("Director de Eventos")||s.tieneRol("Organizador")||s.tieneRol("Coordinador")));
    }
    private void cargar(String vista) {
        try { Parent p=FXMLLoader.load(getClass().getResource("/org/kinalfest/view/"+vista));
            contenido.getChildren().setAll(p);
        } catch(Exception e) { Alertas.error(e); }
    }
    @FXML private void asistentes() { CrudController.entidad="Asistente";cargar("CrudView.fxml"); }
    @FXML private void lugares() { CrudController.entidad="Lugar";cargar("CrudView.fxml"); }
    @FXML private void organizadores() { CrudController.entidad="Organizador";cargar("CrudView.fxml"); }
    @FXML private void recursos() { CrudController.entidad="Recurso";cargar("CrudView.fxml"); }
    @FXML private void eventos() { cargar("EventoView.fxml"); }
    @FXML private void aprobacion() { cargar("AprobacionView.fxml"); }
    @FXML private void tickets() { cargar("TicketView.fxml"); }
    @FXML private void reportes() { cargar("ReporteView.fxml"); }
    @FXML private void salir() { SessionContext.cerrar();Main.mostrar("LoginView.fxml"); }
}
