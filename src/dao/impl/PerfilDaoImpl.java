package dao.impl;

import java.util.List;

import model.Perfil;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import dao.PerfilDao;

@Component @RequestScoped
public class PerfilDaoImpl extends GenericDaoImpl implements PerfilDao {

	@SuppressWarnings("unchecked")
	public List<Perfil> list(Long empresaId) {
		return getSession().createCriteria(Perfil.class)
			.add(Restrictions.eq("empresa.id", empresaId))
			.addOrder(Order.asc("nome")).list();
	}

	public Perfil load(Long id) {
		return (Perfil) getSession().load(Perfil.class, id);
	}
}