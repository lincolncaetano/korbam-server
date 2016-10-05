package br.com.korbam.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.korbam.model.UsuarioDevice;

@RequestScoped
public class UsuarioDeviceDao {

	@Inject
    private Session session;

	@SuppressWarnings("unchecked")
	public List<UsuarioDevice> listaTodos() {
        return createCriteria().list();
    }
	
	private Criteria createCriteria() {
        return session.createCriteria(UsuarioDevice.class);
    }
	
	public void adiciona(UsuarioDevice usuarioDevice) {
		 session.saveOrUpdate(usuarioDevice);
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDevice> pesquisaUsuarioPorIdUsuario(Long idUsuario) {
		return createCriteria().add(Restrictions.eq("usuario.id", idUsuario)).list();	
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDevice> pesquisaUsuarioPorToken(String token) {
		return createCriteria().add(Restrictions.eq("tokenDevice", token)).list();	
	}
	
	public void delete(UsuarioDevice usuarioDevice) {
		 session.delete(usuarioDevice);
	}
	
	public void deletePorToken(String token) {
		Query query = session.createQuery("delete UsuarioDevice where tokenDevice = :TOKEN");
		query.setParameter("TOKEN", token); 
		query.executeUpdate();
	}
	

}
