package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

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
	
	public Amizade pesquisaAmizadePorId(Amizade amizade) {
		
		Criterion cri1 = Restrictions.and(Restrictions.eq("id.idUsuario", amizade.getId().getIdUsuario()),
				Restrictions.eq("id.idUsuarioSolicitato", amizade.getId().getIdUsuarioSolicitato()));
		
		Criterion cri2 = Restrictions.and(Restrictions.eq("id.idUsuario", amizade.getId().getIdUsuarioSolicitato()),
				Restrictions.eq("id.idUsuarioSolicitato", amizade.getId().getIdUsuario()));
	
		return (Amizade) createCriteria().add(Restrictions.or(cri1, cri2)).uniqueResult();
	}
	
	public void adiciona(Amizade amizade) {
		 session.saveOrUpdate(amizade);
	 }
	
	public void delete(Amizade amizade) {
		 session.delete(amizade);
	 }
	
	
	@SuppressWarnings("unchecked")
	public List<Amizade> listaTodosAmigos(Long idUsuario) {
		
		Criterion cri1 = Restrictions.and(Restrictions.eq("id.idUsuario",idUsuario));
		Criterion cri2 = Restrictions.and(Restrictions.eq("id.idUsuarioSolicitato", idUsuario));
	
		return (List<Amizade>) createCriteria().add(Restrictions.or(cri1, cri2))
				.add(Restrictions.eq("status", "A")).list();
	}
	
	
   
}
