package br.com.korbam.dao;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.Usuario;
import br.com.korbam.model.UsuarioEvento;

@RequestScoped
public class UsuarioEventoDao {
	
	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<UsuarioEvento> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(UsuarioEvento.class);
    }
	
	public void adiciona(UsuarioEvento usuarioEvento) {
		 session.saveOrUpdate(usuarioEvento);
	}
	
	public void delete(UsuarioEvento usuarioEvento) {
		session.delete(usuarioEvento);
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioEvento> buscaUsuarioEventoPorIdUsuario(Long idUsuario) {
		return (List<UsuarioEvento>) createCriteria()
				.createAlias("evento", "et") 
				.add(Restrictions.eq("id.idUsuario", idUsuario)).list();
	}


}
