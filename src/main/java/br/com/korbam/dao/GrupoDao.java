package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.Grupo;

@RequestScoped
public class GrupoDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<Grupo> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(Grupo.class);
    }
	
	public void adiciona(Grupo grupo) {
		 session.saveOrUpdate(grupo);
	 }
	
	public void delete(Grupo grupo) {
		 session.delete(grupo);
	 }
	
	@SuppressWarnings("unchecked")
	public List<Grupo> pesquisaGruposPorIdUsuario(Long idUsuario) {
		return (List<Grupo>) createCriteria().add(Restrictions.eq("usuario.id", idUsuario)).list();
	}
	
	public Grupo pesquisaGrupoPorId(Long idGrupo) {
		return (Grupo) createCriteria().add(Restrictions.eq("id", idGrupo)).uniqueResult();
	}
	
}
