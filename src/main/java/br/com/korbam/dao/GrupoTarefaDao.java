package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.GrupoTarefa;

@RequestScoped
public class GrupoTarefaDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<GrupoTarefa> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(GrupoTarefa.class);
    }
	
	public void adiciona(GrupoTarefa grupo) {
		 session.saveOrUpdate(grupo);
	 }
	
	public void delete(GrupoTarefa grupo) {
		 session.delete(grupo);
	 }
	
	@SuppressWarnings("unchecked")
	public List<GrupoTarefa> pesquisaGrupoTarefaPorIdUsuario(Long idUsuario) {
		return (List<GrupoTarefa>) createCriteria().add(Restrictions.eq("usuario.id", idUsuario)).list();
	}
	
	public GrupoTarefa pesquisaGrupoTarefaPorId(Long idGrupo) {
		return (GrupoTarefa) createCriteria().add(Restrictions.eq("id", idGrupo)).uniqueResult();
	}
	

}
