package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.Evento;

@RequestScoped
public class EventoDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Evento> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Evento.class);
    }
	
	public void adiciona(Evento evento) {
		 session.saveOrUpdate(evento);
	 }
	
	public void delete(Evento evento) {
		 session.delete(evento);
	}
	
	@SuppressWarnings("unchecked")
	public List<Evento> pesquisaEventoPorIdUsuario(Long idUsuario) {
		return (List<Evento>) createCriteria().add(Restrictions.eq("usuario.id", idUsuario)).list();
	}
	

	public Evento pesquisaEventoPorId(Long id) {
		return (Evento) createCriteria().add(Restrictions.eq("id", id)).uniqueResult();
	}

}
