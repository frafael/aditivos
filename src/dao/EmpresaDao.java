package dao;

import java.util.List;

import model.Empresa;

public interface EmpresaDao extends GenericDao {
	public List<Empresa> list();
	public Empresa load(Long id);
	public Empresa loadByContexto(String contexto);
}