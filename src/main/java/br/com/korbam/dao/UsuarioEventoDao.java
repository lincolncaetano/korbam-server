package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.UsuarioEvento;

@RequestScoped
public class UsuarioEventoDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<UsuarioEvento> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(UsuarioEvento.class);
    }
	
	public void adiciona(UsuarioEvento usuarioEvento) {
		 session.saveOrUpdate(usuarioEvento);
	}
	
	public void delete(UsuarioEvento usuarioEvento) {
		session.delete(usuarioEvento);
	}
	
	public void deletePorEvento(Long idEvento) {
		Query query = session.createQuery("delete UsuarioEvento where id.idEvento = :ID");
		query.setParameter("ID", idEvento); 
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioEvento> buscaUsuarioEventoPorIdUsuario(Long idUsuario) {
		return (List<UsuarioEvento>) createCriteria()
				.createAlias("evento", "et") 
				.add(Restrictions.eq("id.idUsuario", idUsuario))
				.add(Restrictions.eq("status", "A"))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioEvento> buscaUsuarioEventoPorIdUsuarioPendente(Long idUsuario) {
		return (List<UsuarioEvento>) createCriteria()
				.createAlias("evento", "et") 
				.add(Restrictions.eq("id.idUsuario", idUsuario))
				.add(Restrictions.eq("status", "P"))
				.addOrder(Order.desc("et.dtInicio"))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioEvento> buscaTodosUsuarioEventoPorIdUsuario(Long idUsuario) {
		return (List<UsuarioEvento>) createCriteria()
				.createAlias("evento", "et") 
				.add(Restrictions.eq("id.idUsuario", idUsuario))
				.addOrder(Order.desc("et.dtInicio"))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioEvento> buscaListaUsuarioPorEvento(Long idEvento) {
		return (List<UsuarioEvento>) createCriteria()
				.add(Restrictions.eq("id.idEvento", idEvento))
				.list();
	}
	
	public UsuarioEvento pesquisaUsuarioEventoPorId(Long idEvento, Long idUsuario) {
		return (UsuarioEvento) createCriteria()
				.add(Restrictions.eq("id.idUsuario", idUsuario))
				.add(Restrictions.eq("id.idEvento", idEvento))
				.uniqueResult();
	}


}
