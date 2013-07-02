package dao;

public interface GenericDao {
	public boolean save(Object object);
	public boolean update(Object object);
	public void delete(Object object);
}