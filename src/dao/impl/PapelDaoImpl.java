package dao.impl;

import java.util.List;

import model.Papel;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import dao.PapelDao;

@Component @RequestScoped
public class PapelDaoImpl extends GenericDaoImpl implements PapelDao {
	
	@SuppressWarnings("unchecked")
	public List<Papel> list() {
		return getSession().createCriteria(Papel.class)
			.addOrder(Order.asc("ordem")).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Papel> listByOrdem() {
		return getSession().createCriteria(Papel.class)
			.add(Restrictions.isNotNull("ordem"))
			.addOrder(Order.asc("ordem")).list();
	}

	public Papel load(Long id) {
		return (Papel) getSession().load(Papel.class, id);
	}
}
