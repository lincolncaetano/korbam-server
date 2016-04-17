package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.korbam.model.GrupoUsuario;

@RequestScoped
public class GrupoUsuarioDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<GrupoUsuario> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(GrupoUsuario.class);
    }
	
	public void adiciona(GrupoUsuario grupoUsuario) {
		 session.saveOrUpdate(grupoUsuario);
	 }
	
	public void delete(GrupoUsuario grupoUsuario) {
		 session.delete(grupoUsuario);
	 }

}
