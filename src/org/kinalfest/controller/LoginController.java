package org.kinalfest.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.kinalfest.dao.impl.LoginDaoImpl;
import org.kinalfest.model.UsuarioSesion;
import org.kinalfest.system.Main;
import org.kinalfest.util.*;
public class LoginController {
    @FXML private TextField usuario;
    @FXML private PasswordField clave;
    @FXML private void ingresar() {
        if(usuario.getText().isBlank() || clave.getText().isBlank()) {
            Alertas.info("Validación","Completa usuario y contraseña.");return;
        }
        try {
            UsuarioSesion u=new LoginDaoImpl().autenticar(usuario.getText().trim(),clave.getText());
            if(u==null) { Alertas.info("Acceso denegado","Credenciales incorrectas.");return; }
            SessionContext.iniciar(u.getIdUsuario(),u.getUsuario(),u.getRol());
            Main.mostrar("PrincipalView.fxml");
        } catch(RuntimeException e) { Alertas.error(e); }
    }
}
