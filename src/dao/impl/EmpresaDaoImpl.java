package dao.impl;

import java.util.List;

import model.Empresa;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import dao.EmpresaDao;

@Component @RequestScoped
public class EmpresaDaoImpl extends GenericDaoImpl implements EmpresaDao {

	@SuppressWarnings("unchecked")
	public List<Empresa> list() {
		return getSession().createCriteria(Empresa.class)
			.addOrder(Order.asc("nome")).list();
	}

	public Empresa load(Long id) {
		return (Empresa) getSession().load(Empresa.class, id);
	}

	public Empresa loadByContexto(String contexto) {
			Criteria criteria = getSession().createCriteria(Empresa.class, "e");
			ProjectionList list = Projections.projectionList().create();
				list.add(Projections.property("e.id"), "id");
				list.add(Projections.property("e.nome"), "nome");
				list.add(Projections.property("e.logoFileName"), "logoFileName");
				criteria.add(Restrictions.eq("e.contexto", contexto));
				criteria.add(Restrictions.eq("e.ativa", true));
				criteria.setProjection(list).setResultTransformer(Transformers.aliasToBean(Empresa.class));
			return (Empresa) criteria.uniqueResult();
	}
}