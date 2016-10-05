package br.com.korbam.controller;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.GrupoDao;
import br.com.korbam.dao.GrupoEventoDao;
import br.com.korbam.dao.GrupoUsuarioDao;
import br.com.korbam.model.Evento;
import br.com.korbam.model.Grupo;
import br.com.korbam.model.GrupoEvento;
import br.com.korbam.model.GrupoUsuario;
import br.com.korbam.model.GrupoUsuarioId;
import br.com.korbam.model.Usuario;



@Controller
public class GrupoController {
	
	@Inject 
	private Result result;
	
	@Inject
    private GrupoDao grupoDao;
	
	@Inject
    private GrupoUsuarioDao grupoUsuarioDao;
	
	@Inject
    private GrupoEventoDao grupoEventoDao;
	
	@Post("/salvarGrupo")
	@Consumes(value="application/json")
	public void salvarGrupo(Grupo grupo) {
		
		try{
			grupoDao.adiciona(grupo);
			
			if(!grupo.getListaUsuario().isEmpty()){
				for (Usuario usr : grupo.getListaUsuario()) {
					GrupoUsuario gu = new GrupoUsuario(new GrupoUsuarioId(usr.getId(), grupo.getId()));
					gu.setGrupo(grupo);
					gu.setUsuario(usr);
					grupoUsuarioDao.adiciona(gu);
				}
			}
			
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
	
	@Get("/pesquisaGrupoPorUsuario/{idUsuario}")
	public void pesquisaEventoPorUsuario(Long idUsuario) {
		
		List<Grupo> lista = new ArrayList<>();
		List<GrupoUsuario> listaGrupoUsuario = grupoUsuarioDao.listaGruposPorIdUsuario(idUsuario);
		for (GrupoUsuario grupoUsuario : listaGrupoUsuario) {
			lista.add(grupoUsuario.getGrupo());
		}
		
		List<Grupo> listaGrupo= grupoDao.pesquisaGruposPorIdUsuario(idUsuario);
		lista.addAll(listaGrupo);
		
		if(!lista.isEmpty()){
			result.use(Results.json()).withoutRoot().from(lista).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
    }
	
	@Get("/pesquisaGrupoPorId/{idGrupo}")
	public void pesquisaGrupoPorId(Long idGrupo) {
		

		Grupo grupo = grupoDao.pesquisaGrupoPorId(idGrupo);
	
		if(grupo!= null){
			
			List<Usuario> listaUsuario = new ArrayList<>();
			listaUsuario.add(grupo.getUsuario());
			List<GrupoUsuario> listaGrupoUsuario = grupoUsuarioDao.listaGruposPorIdGrupo(idGrupo);
			for (GrupoUsuario grupoUsuario : listaGrupoUsuario) {
				listaUsuario.add(grupoUsuario.getUsuario());
			}
			grupo.setListaUsuario(listaUsuario);
			
			List<Evento> listaEventos = new ArrayList<>();
			List<GrupoEvento> listaGrupoEvento = grupoEventoDao.listaEventosPorIdGrupo(idGrupo);
			for (GrupoEvento grupoEvento : listaGrupoEvento) {
				listaEventos.add(grupoEvento.getEvento());
			}
			grupo.setListaEvento(listaEventos);
			
			result.use(Results.json()).withoutRoot().from(grupo).include("listaUsuario")
			.include("listaEvento")
			.include("listaEvento.usuario")
			.include("usuario").serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
    }

}