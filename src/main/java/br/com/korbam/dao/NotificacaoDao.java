package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
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
	
	@SuppressWarnings("unchecked")
	public List<Notificacao> pesquisaEventoPorIdUsuario(Long idUsuario) {
		return (List<Notificacao>) createCriteria().add(Restrictions.eq("usuarioNotificado.id", idUsuario))
				.setMaxResults(15).addOrder(Order.desc("dtNotificacao")).list();
	}

}
