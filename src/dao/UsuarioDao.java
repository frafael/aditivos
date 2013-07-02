package dao;

import java.util.List;

import model.Usuario;

public interface UsuarioDao extends GenericDao {
	public List<Usuario> list(Long empresaId);
	public List<Usuario> search( Long filtro, String search, Long empresaId );
	public List<Usuario> aniversariantes( Long empresaId );
	public List<Usuario> aniversarios( int mes, Long empresaId );
	public Usuario load( Long id );
	public Usuario load( String login, Long empresaId );
	public Usuario show( Long id, Long empresaId );
	public Usuario login( String nome, String senha, Long idCli );
	public Usuario loginByEmail( String email, String senha, Long IdCli );
	public Usuario getNomeByUsuario( Long id );
	public boolean userExist( Long id );
	public boolean isLoginExiste( String login, Long empresaId );
}