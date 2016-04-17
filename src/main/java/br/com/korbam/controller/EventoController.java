package br.com.korbam.controller;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;


import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.AmizadeDao;
import br.com.korbam.dao.EventoDao;
import br.com.korbam.dao.UsuarioEventoDao;
import br.com.korbam.model.Amizade;
import br.com.korbam.model.Evento;
import br.com.korbam.model.Usuario;
import br.com.korbam.model.UsuarioEvento;
import br.com.korbam.model.UsuarioEventoId;



@Controller
public class EventoController {
	
	@Inject 
	private Result result;
	
	@Inject
    private EventoDao eventoDao;
	
	@Inject
    private UsuarioEventoDao usuarioEventoDao;
	
	@Post("/salvarEvento")
	@Consumes(value="application/json")
	public void salvarEvento(Evento evento) {
		
		try{
			
			DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
			Date date = (Date)formatter.parse(evento.getDtInicioString());
			
			evento.setDtInicio(date);
			evento.setDescricao("teste");
			
			eventoDao.adiciona(evento);
			
			for (Usuario usuario : evento.getListaUsuario()) {
				UsuarioEvento usrEvento = new UsuarioEvento();
				usrEvento.setId(new UsuarioEventoId(usuario.getId(), evento.getId()));
				
				usrEvento.setEvento(evento);
				usrEvento.setUsuario(usuario);
				
				usuarioEventoDao.adiciona(usrEvento);
			}
			

			result.use(Results.json()).withoutRoot().from(true).serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
		
		
	
    }
	
	@Post("/salvarUsuarioEvento")
	@Consumes(value="application/json")
	public void salvarUsuarioEvento(UsuarioEvento usuarioEvento) {
		try{
			usuarioEventoDao.adiciona(usuarioEvento);
			result.use(Results.json()).withoutRoot().from(true).serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
	
    }
	
	@Get("/pesquisaEventoPorUsuario/{idUsuario}")
	public void pesquisaEventoPorUsuario(Long idUsuario) {
		
		List<Evento> lista = eventoDao.pesquisaEventoPorIdUsuario(idUsuario);
		if(lista != null){
			result.use(Results.json()).withoutRoot().from(lista).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from("false").serialize();
		}
		
    }
	
	@Get("/buscaEventoUsuarioPorUsuarioData/{idUsuario}/{dataEvento}")
	public void buscaEventoUsuarioPorUsuarioData(Long idUsuario, String dataEvento) {
		
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			date = (Date)formatter.parse(dataEvento);
			List<UsuarioEvento> lista = usuarioEventoDao.buscaUsuarioEventoPorIdUsuario(idUsuario);
			
			List<Evento> listaRetorno = new ArrayList<>();
			for (UsuarioEvento usuarioEvento : lista) {
				if(usuarioEvento.getEvento().getDtInicio().equals(date)){
					listaRetorno.add(usuarioEvento.getEvento());
				}
			}
			if(!listaRetorno.isEmpty()){
				result.use(Results.json()).withoutRoot().from(listaRetorno).serialize();
			}else{
				result.use(Results.json()).withoutRoot().from("false").serialize();
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.use(Results.json()).withoutRoot().from("false").serialize();
		}
		
    }

}