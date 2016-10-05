package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.GrupoEvento;

@RequestScoped
public class GrupoEventoDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<GrupoEvento> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(GrupoEvento.class);
    }
	
	public void adiciona(GrupoEvento grupoEvento) {
		 session.saveOrUpdate(grupoEvento);
	}
	
	public void delete(GrupoEvento grupoEvento) {
		 session.delete(grupoEvento);
	}
	
	public void deletePorEvento(Long idEvento) {
		Query query = session.createQuery("delete GrupoEvento where id.idEvento = :ID");
		query.setParameter("ID", idEvento); 
		query.executeUpdate();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<GrupoEvento> listaEventosPorIdGrupo(Long idGrupo) {
        return createCriteria()
				.add(Restrictions.eq("id.idGrupo", idGrupo))
				.list();
    }


}
