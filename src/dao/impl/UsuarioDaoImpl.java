package dao.impl;

import java.util.Calendar;
import java.util.List;

import model.Usuario;

import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import dao.UsuarioDao;

@Component @RequestScoped
public class UsuarioDaoImpl extends GenericDaoImpl implements UsuarioDao {

	@SuppressWarnings("unchecked")
	public List<Usuario> list(Long empresaId) {
		Criteria criteria = criteriosDeBuscaDeUsuario();
			criteria.add(Restrictions.eq("user.empresa.id", empresaId));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> search( Long filtro, String search, Long empresaId ) {
		Criteria criteria = criteriosDeBuscaDeUsuario();
			criteria.add(Restrictions.eq("user.empresa.id", empresaId));
			criteria.add(Restrictions.not(Restrictions.eq("user.id", 4538L)));
			
		if( search.equals("todos") ) return criteria.list();

		if( filtro == 1 ) {	
			criteria.add( Restrictions.sqlRestriction("SEM_ACENTO({alias}.nome) ilike SEM_ACENTO(?)", "%" + search + "%", StringType.INSTANCE));
		}
		else criteria.add(Restrictions.ilike("user.placa", "%" + search + "%"));
		
		criteria.setResultTransformer(new AliasToBeanResultTransformer(Usuario.class)); 

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> aniversariantes( Long empresaId ) {
		int mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1;
		StringBuilder hql = new StringBuilder();
			hql.append("select u.id as id, u.nome as nome, u.dataDeNascimento as dataDeNascimento, ");
			hql.append("(select count(*) from Recado as r where r.destinatario.id=u.id and r.cadastradoEm >= '01-07-2012') as qtdRecados ");
			hql.append("from Usuario as u where u.ativo = true and u.empresa.id = :empresaId and month(u.dataDeNascimento) = :mesAtual and u.id != :idAdmin ");
			hql.append("order by day(u.dataDeNascimento), u.nome");
		Query query = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Usuario.class));
			query.setParameter("mesAtual", mesAtual);
			query.setParameter("empresaId", empresaId);
			query.setParameter("idAdmin", 4538L);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> aniversarios( int mes, Long empresaId ) {
		StringBuilder hql = new StringBuilder();
			hql.append("select u.id as id, u.nome as nome, u.dataDeNascimento as dataDeNascimento, ");
			hql.append("(select count(*) from Recado as r where r.destinatario.id=u.id and r.cadastradoEm >= '01-07-2012') as qtdRecados ");
			hql.append("from Usuario as u where u.ativo = true and u.empresa.id = :empresaId and month(u.dataDeNascimento) = :mesAtual and u.id != :idAdmin ");
			hql.append("order by day(u.dataDeNascimento), u.nome");
		Query query = getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Usuario.class));
			query.setParameter("mesAtual", mes);
			query.setParameter("empresaId", empresaId);
			query.setParameter("idAdmin", 4538L);
		return query.list();
	}

	public Usuario load( Long id ) {
		return (Usuario) getSession().load(Usuario.class, id);
	}

	public Usuario load( String login, Long empresaId ) {
		try {
			Criteria criteria = getSession().createCriteria(Usuario.class, "u");
			ProjectionList list = Projections.projectionList().create();
				list.add(Projections.property("u.id"), "id");
				list.add(Projections.property("u.nome"), "nome");
				list.add(Projections.property("u.login"), "login");
				list.add(Projections.property("u.email"), "email");
				list.add(Projections.property("u.empresa"), "empresa");
				criteria.setProjection(list).setResultTransformer(Transformers.aliasToBean(Usuario.class));

				criteria.add(Restrictions.eq("u.login", login));
				criteria.add(Restrictions.eq("u.ativo", true));

				criteria.add(Restrictions.eq("u.empresa.id", empresaId));

			return (Usuario) criteria.uniqueResult();
		} catch(NonUniqueResultException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Usuario getNomeByUsuario( Long id ) {
		Criteria criteria = getSession().createCriteria(Usuario.class, "u");
		ProjectionList list = Projections.projectionList().create();
			list.add(Projections.property("u.nome"), "nome");
			criteria.add(Restrictions.eq("u.id", id));
			criteria.setProjection(list).setResultTransformer(Transformers.aliasToBean(Usuario.class));
		return (Usuario) criteria.uniqueResult();
	}

	public Usuario show( Long id, Long empresaId ) {
		Criteria criteria = criteriosDeBuscaDeUsuario();
		criteria.add(Restrictions.eq("user.id", id));
		criteria.add(Restrictions.eq("user.empresa.id", empresaId));

		return (Usuario) criteria.uniqueResult();
	}

	public boolean isLoginExiste( String login, Long empresaId ) {
		Criteria criteria = getSession().createCriteria(Usuario.class, "user");
			criteria.add(Restrictions.eq("user.login", login.toLowerCase()));
			criteria.add(Restrictions.eq("user.empresa.id", empresaId));
			criteria.add(Restrictions.eq("user.ativo", true));
		ProjectionList list = Projections.projectionList().create();
			list.add(Projections.property("user.login"), "login");
		criteria.setProjection(list).setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));

		return ( criteria.uniqueResult() != null );
	}

	public Usuario login( String login, String senha, Long IdCli ) {
		try {
			Criteria criteria = getSession().createCriteria(Usuario.class, "user");
				criteria.add(Restrictions.eq("user.ativo", true));
				criteria.add(Restrictions.eq("user.login", login));
				criteria.add(Restrictions.eq("user.senha", senha));
				
				criteria.add(Restrictions.eq("user.empresa.id", IdCli));
				
				//-----------------------//
			return (Usuario) criteria.uniqueResult();
		} catch(NonUniqueResultException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Não foi possível acessar o sistema.");
		}
	}
	
	public Usuario loginByEmail( String email, String senha, Long empresaId ) {
		try {
			Criteria criteria = getSession().createCriteria(Usuario.class, "user");
				criteria.add(Restrictions.eq("user.ativo", true));
				criteria.add(Restrictions.eq("user.email", email));
				criteria.add(Restrictions.eq("user.senha", senha));
				
				criteria.add(Restrictions.eq("user.empresa.id", empresaId));
				
			return (Usuario) criteria.uniqueResult();
		} catch(NonUniqueResultException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Não foi possível acessar o sistema.");
		}
	}
	
	public boolean userExist( Long id ) {
		Criteria criteria = criteriosDeBuscaDeUsuario();
			criteria.add(Restrictions.eq("user.id", id));
		
		return criteria.list().size() > 0;
	}

	private Criteria criteriosDeBuscaDeUsuario() {
		Criteria criteria = getSession().createCriteria(Usuario.class, "user");
			criteria.createCriteria("user.cargo", "car");
			criteria.createCriteria("user.setor", "set");
			criteria.createCriteria("user.unidade", "uni");
			criteria.createCriteria("user.empresa", "emp");
		criteria.setProjection(projectionsDeBuscaDeUsuario()).setResultTransformer(new AliasToBeanResultTransformer(Usuario.class));

		return criteria.add(Restrictions.eq("user.ativo", true)).addOrder(Order.asc("user.nome"));
	}

	private ProjectionList projectionsDeBuscaDeUsuario() {
		ProjectionList list = Projections.projectionList().create();
			list.add(Projections.property("user.id"), "id");
			list.add(Projections.property("user.nome"), "nome");
			list.add(Projections.property("user.ramal"), "ramal");
			list.add(Projections.property("user.ativo"), "ativo");
			list.add(Projections.property("user.email"), "email");
			list.add(Projections.property("user.placa"), "userPlaca");
			list.add(Projections.property("user.celular"), "userCelular");
			list.add(Projections.property("user.ultimoLogin"), "ultimoLogin");
			list.add(Projections.property("user.fotoFileName"), "fotoFileName");

			list.add(Projections.property("emp.id"), "empresaId");
			list.add(Projections.property("set.nome"), "setorNome");
			list.add(Projections.property("car.nome"), "cargoNome");
			list.add(Projections.property("uni.nome"), "unidadeNome");

		return list;
	}
}