package org.kinalfest.dao;
import org.kinalfest.model.UsuarioSesion;
public interface LoginDao { UsuarioSesion autenticar(String usuario, String clave); }
