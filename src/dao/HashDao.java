package dao;

import model.Hash;

public interface HashDao extends GenericDao {
	public Hash load(Long id);
}