package org.kinalfest.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.kinalfest.dao.impl.EventoDaoImpl;
import org.kinalfest.util.*;
/** Flujo protegido para Taquillero. */
public class TicketController {
    @FXML private TextField idEvento;
    @FXML private void procesar() {
        try {
            SessionContext s=SessionContext.actual();
            if(s==null||!s.tieneRol("Taquillero"))
                throw new IllegalArgumentException("Acceso permitido únicamente a Taquillero");
            int id=Integer.parseInt(idEvento.getText().trim());
            if(id<=0)throw new IllegalArgumentException("El ID debe ser positivo");
            new EventoDaoImpl().emitirTicket(id,s.getId());
            Alertas.info("KinalFest","Operación completada");
            idEvento.clear();
        } catch(Exception e) { Alertas.error(e); }
    }
}
