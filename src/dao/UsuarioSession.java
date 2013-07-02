package dao;

import model.Usuario;

public interface UsuarioSession {

	public Usuario getUsuario();

	public void setUsuario(Usuario usuario);
}