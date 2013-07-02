package dao.impl;

import model.Hash;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import dao.HashDao;

@Component @RequestScoped
public class HashDaoImpl extends GenericDaoImpl implements HashDao {

	public Hash load(Long id) {
		Criteria criteria = getSession().createCriteria(Hash.class, "hash");
			criteria.add(Restrictions.eq("hash.usuario.id", id));
		
		return (Hash) criteria.uniqueResult();
	}
}