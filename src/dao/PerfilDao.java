package dao;

import java.util.List;

import model.Perfil;

public interface PerfilDao extends GenericDao {
	public List<Perfil> list(Long empresaId);
	public Perfil load(Long id);
}