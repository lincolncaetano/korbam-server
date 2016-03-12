package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.korbam.model.Amizade;


@RequestScoped
public class AmizadeDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Amizade> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Amizade.class);
    }
	
	public void adiciona(Amizade amizade) {
		 session.saveOrUpdate(amizade);
	 }
	
	public void delete(Amizade amizade) {
		 session.delete(amizade);
	 }
   
}
