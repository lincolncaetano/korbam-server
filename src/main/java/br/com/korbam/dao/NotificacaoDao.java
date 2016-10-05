package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.Notificacao;


@RequestScoped
public class NotificacaoDao {
	
	@Inject
    private Session session;
	
	private Criteria createCriteria() {
        return session.createCriteria(Notificacao.class);
    }
	
	public void adiciona(Notificacao notificacao) {
		 session.saveOrUpdate(notificacao);
	}
	
	public void delete(Notificacao notificacao) {
		 session.delete(notificacao);
	}
	
	public void deletePorEvento(Long idEvento) {
		Query query = session.createQuery("delete Notificacao where evento.id = :ID");
		query.setParameter("ID", idEvento); 
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Notificacao> pesquisaEventoPorIdUsuario(Long idUsuario) {
		return (List<Notificacao>) createCriteria()
				.add(Restrictions.eq("usuarioNotificado.id", idUsuario))
				.setMaxResults(15)
				.addOrder(Order.desc("dtNotificacao")).list();
	}

}
