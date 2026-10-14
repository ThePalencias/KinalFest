package org.kinalfest.util;
import javafx.scene.control.Alert;
public final class Alertas {
    private Alertas() {}
    public static void info(String titulo,String mensaje) {
        Alert a=new Alert(Alert.AlertType.INFORMATION); a.setTitle(titulo);a.setHeaderText(null);a.setContentText(mensaje);a.showAndWait();
    }
    public static void error(Throwable e) {
        String msg=e.getMessage();
        if(e.getCause()!=null && e.getCause().getMessage()!=null) msg+="\n"+e.getCause().getMessage();
        Alert a=new Alert(Alert.AlertType.ERROR);a.setTitle("KinalFest - Error");a.setHeaderText("No se pudo completar la operación");
        a.setContentText(msg);a.showAndWait();
    }
}
