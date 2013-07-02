package dao;

import java.util.List;

import model.Papel;

public interface PapelDao extends GenericDao {
	public List<Papel> list();
	public List<Papel> listByOrdem();
	public Papel load(Long id);
}
