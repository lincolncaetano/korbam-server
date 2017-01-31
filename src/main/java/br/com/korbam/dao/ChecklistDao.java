package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.Checklist;

@RequestScoped
public class ChecklistDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Checklist> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Checklist.class);
    }
	
	public void adiciona(Checklist checklist) {
		 session.saveOrUpdate(checklist);
	 }
	
	public void delete(Checklist checklist) {
		session.delete(checklist);
	}
	
	@SuppressWarnings("unchecked")
	public List<Checklist> pesquisaChecklistPorEvento(Long idEvento) {
		return (List<Checklist>) createCriteria().add(Restrictions.eq("evento.id", idEvento)).list();
	}

}
