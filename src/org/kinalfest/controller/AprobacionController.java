package org.kinalfest.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.kinalfest.dao.impl.EventoDaoImpl;
import org.kinalfest.util.*;
/** Flujo protegido para Director de Eventos. */
public class AprobacionController {
    @FXML private TextField idEvento;
    @FXML private void procesar() {
        try {
            SessionContext s=SessionContext.actual();
            if(s==null||!s.tieneRol("Director de Eventos"))
                throw new IllegalArgumentException("Acceso permitido únicamente a Director de Eventos");
            int id=Integer.parseInt(idEvento.getText().trim());
            if(id<=0)throw new IllegalArgumentException("El ID debe ser positivo");
            new EventoDaoImpl().aprobar(id,s.getId());
            Alertas.info("KinalFest","Operación completada");
            idEvento.clear();
        } catch(Exception e) { Alertas.error(e); }
    }
}
