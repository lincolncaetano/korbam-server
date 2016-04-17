package br.com.korbam.controller;


import javax.inject.Inject;


import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.AmizadeDao;
import br.com.korbam.dao.EventoDao;
import br.com.korbam.dao.GrupoDao;
import br.com.korbam.dao.GrupoUsuarioDao;
import br.com.korbam.dao.UsuarioEventoDao;
import br.com.korbam.model.Amizade;
import br.com.korbam.model.Evento;
import br.com.korbam.model.Grupo;
import br.com.korbam.model.GrupoUsuario;
import br.com.korbam.model.Usuario;
import br.com.korbam.model.UsuarioEvento;



@Controller
public class GrupoController {
	
	@Inject 
	private Result result;
	
	@Inject
    private GrupoDao grupoDao;
	
	@Inject
    private GrupoUsuarioDao grupoUsuarioDao;
	
	@Post("/salvarGrupo")
	@Consumes(value="application/json")
	public void salvarGrupo(Grupo grupo) {
		
		try{
			grupoDao.adiciona(grupo);
			result.use(Results.json()).withoutRoot().from(true).serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
	
    }
	
	@Post("/salvarGrupoUsuario")
	@Consumes(value="application/json")
	public void salvarGrupoUsuario(GrupoUsuario grupoUsuario) {

		try{
			grupoUsuarioDao.adiciona(grupoUsuario);
			result.use(Results.json()).withoutRoot().from(true).serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
	
    }

}