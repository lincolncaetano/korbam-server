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
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.ChecklistDao;
import br.com.korbam.dao.EventoDao;
import br.com.korbam.dao.GrupoEventoDao;
import br.com.korbam.dao.NotificacaoDao;
import br.com.korbam.dao.UsuarioDeviceDao;
import br.com.korbam.dao.UsuarioEventoDao;
import br.com.korbam.model.Checklist;
import br.com.korbam.model.Evento;
import br.com.korbam.model.GrupoEvento;
import br.com.korbam.model.GrupoEventoId;
import br.com.korbam.model.Notificacao;
import br.com.korbam.model.Usuario;
import br.com.korbam.model.UsuarioDevice;
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
	
	@Inject
	private NotificacaoDao notificacaoDao;

	@Inject
	private UsuarioDeviceDao usuarioDeviceDao;
	
	@Inject
	private GrupoEventoDao grupoEventoDao;
	
	@Inject
	private ChecklistDao checklistDao;
	
	@Post("/salvarEvento")
	@Consumes(value="application/json")
	public void salvarEvento(Evento evento) {
		
		try{
			
			prepararEvento(evento);
			result.use(Results.json()).withoutRoot().from(evento).serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
    }
	
	private void prepararEvento(Evento evento){
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = (Date)formatter.parse(evento.getDtInicioString());
			evento.setDtInicio(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(evento.getGrupoTarefa() != null && evento.getGrupoTarefa().getId() == 0){
			evento.setGrupoTarefa(null);
		}
		
		
		
		eventoDao.adiciona(evento);
		
		UsuarioEvento usuarioEventoAdmin = new UsuarioEvento();
		usuarioEventoAdmin.setId(new UsuarioEventoId(evento.getUsuario().getId(), evento.getId()));
		usuarioEventoAdmin.setUsuario(evento.getUsuario());
		usuarioEventoAdmin.setEvento(evento);
		usuarioEventoAdmin.setStatus("A");
		usuarioEventoDao.adiciona(usuarioEventoAdmin);

		
		//ligar aos usuario convidados
		
		if(evento.getListaUsuario() != null && !evento.getListaUsuario().isEmpty()){
			for (UsuarioEvento usrEvento : evento.getListaUsuario()) {
				
				usrEvento.setId(new UsuarioEventoId(usrEvento.getUsuario().getId(), evento.getId()));
				usrEvento.setEvento(evento);
				usuarioEventoDao.adiciona(usrEvento);
				
				Notificacao notificacao = new Notificacao(usrEvento);
				notificacao.setMensagem("Evento");
				notificacaoDao.adiciona(notificacao);
				
				List<UsuarioDevice> listaUsuarioDevice = usuarioDeviceDao.pesquisaUsuarioPorIdUsuario(usrEvento.getUsuario().getId());
				for (UsuarioDevice usuarioDevice : listaUsuarioDevice) {
					String msg = evento.getUsuario().getUsername()+" te convidou para participar do evento "+evento.getTitulo();
					
					if(usuarioDevice.getTipoDevice().equals("I")){
						EnviaNotificacaoIOS enviaNotIOS = new EnviaNotificacaoIOS(msg, usuarioDevice.getTokenDevice());
						Thread threadNot = new Thread(enviaNotIOS);
						threadNot.start();	
					}else{
						EnviaNotificacaoAndroid enviaNot = new EnviaNotificacaoAndroid(msg, usuarioDevice.getTokenDevice());
						Thread threadNot = new Thread(enviaNot);
						threadNot.start();
					}
				}
				
			}
		}
		
		
	}
	
	@Post("/salvarUsuarioEvento")
	@Consumes(value="application/json")
	public void salvarUsuarioEvento(UsuarioEvento usuarioEvento) {
		try{
			usuarioEventoDao.adiciona(usuarioEvento);
			List<UsuarioDevice> listaUsuarioDevice = usuarioDeviceDao.pesquisaUsuarioPorIdUsuario(usuarioEvento.getEvento().getUsuario().getId());
			String msg = "";
			if(usuarioEvento.getStatus().equals("A")){
				msg = usuarioEvento.getUsuario().getUsername()+" aceitou convite para o evento "+usuarioEvento.getEvento().getTitulo();
			}else if(usuarioEvento.getStatus().equals("R")){
				msg = usuarioEvento.getUsuario().getUsername()+" recusou convite para o evento "+usuarioEvento.getEvento().getTitulo();
			}
			for (UsuarioDevice usuarioDevice : listaUsuarioDevice) {
				if(usuarioDevice.getTipoDevice().equals("I")){
					EnviaNotificacaoIOS enviaNotIOS = new EnviaNotificacaoIOS(msg, usuarioDevice.getTokenDevice());
					Thread threadNot = new Thread(enviaNotIOS);
					threadNot.start();	
				}else{
					EnviaNotificacaoAndroid enviaNot = new EnviaNotificacaoAndroid(msg, usuarioDevice.getTokenDevice());
					Thread threadNot = new Thread(enviaNot);
					threadNot.start();
				}
			}
			
			result.use(Results.json()).withoutRoot().from(usuarioEvento).include("usuario").serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
	
    }
	
	@Get("/pesquisaEventoPorUsuario/{idUsuario}")
	public void pesquisaEventoPorUsuario(Long idUsuario) {
		
		List<Evento> lista = eventoDao.pesquisaEventoPorIdUsuario(idUsuario);

		for (Evento evento : lista) {
			evento.setListaUsuario(usuarioEventoDao.buscaListaUsuarioPorEvento(evento.getId()));
		}
		 
		if(!lista.isEmpty()){
			result.use(Results.json()).withoutRoot().from(lista).include("usuario")
			.include("listaUsuario").include("listaUsuario.id").serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
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
			
			List<Evento> listaEventosUsuario = eventoDao.pesquisaEventoPorIdUsuario(idUsuario);
			for (Evento evento : listaEventosUsuario) {
				if(evento.getDtInicio().equals(date)){
					listaRetorno.add(evento);
				}
			}
			
			for (Evento evento : listaRetorno) {
				evento.setListaUsuario(usuarioEventoDao.buscaListaUsuarioPorEvento(evento.getId()));
			}
			
			if(!listaRetorno.isEmpty()){
				result.use(Results.json()).withoutRoot().from(listaRetorno).include("usuario")
				.include("listaUsuario").include("listaUsuario.id").serialize();
			}else{
				result.use(Results.json()).withoutRoot().from(false).serialize();
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
    }
	
	
	@Get("/buscaUsuarioEventoPendente/{idUsuario}")
	public void buscaUsuarioEventoPendente(Long idUsuario) {
		
		try {

			List<UsuarioEvento> lista = usuarioEventoDao.buscaUsuarioEventoPorIdUsuarioPendente(idUsuario);
						
			if(!lista.isEmpty()){
				result.use(Results.json()).withoutRoot().from(lista).include("evento").include("evento.usuario").serialize();
			}else{
				result.use(Results.json()).withoutRoot().from(false).serialize();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
    }
	
	@Get("/buscaListaUsuarioPorEvento/{idEvento}")
	public void buscaListaUsuarioPorEvento(Long idEvento) {
		
		try {
			List<UsuarioEvento> lista = usuarioEventoDao.buscaListaUsuarioPorEvento(idEvento);
					
			if(!lista.isEmpty()){
				result.use(Results.json()).withoutRoot().from(lista).include("usuario").serialize();
			}else{
				result.use(Results.json()).withoutRoot().from(false).serialize();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
    }
	
	@Get("/buscaTodosUsuarioEventoPorIdUsuario/{idUsuario}")
	public void buscaTodosUsuarioEventoPorIdUsuario(Long idUsuario) {
		
		List<UsuarioEvento> lista = usuarioEventoDao.buscaTodosUsuarioEventoPorIdUsuario(idUsuario);

		if(!lista.isEmpty()){
			result.use(Results.json()).withoutRoot().from(lista).include("id").include("usuario").include("evento").include("evento.usuario").include("evento.grupoTarefa").serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
    }
	
	@Delete("/deteleUsuarioEvento/{idUsuario}/{idEvento}")
	@Consumes(value="application/json")
	public void deteleUsuarioEvento(Long idUsuario, Long idEvento) {
		
		UsuarioEventoId euId = new UsuarioEventoId(idUsuario, idEvento);
		UsuarioEvento usuarioEvento = new UsuarioEvento();
		usuarioEvento.setId(euId);
		
		try {
			usuarioEventoDao.delete(usuarioEvento);
			result.use(Results.json()).withoutRoot().from(true).serialize();
		} catch (Exception e) {
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
	}
	
	@Delete("/detelaEvento/{idEvento}")
	@Consumes(value="application/json")
	public void detelaEvento(Long idEvento) {
		
		try {
			notificacaoDao.deletePorEvento(idEvento);
			usuarioEventoDao.deletePorEvento(idEvento);
			grupoEventoDao.deletePorEvento(idEvento);
			
			Evento ev = new Evento();
			ev.setId(idEvento);
			eventoDao.delete(ev);
			result.use(Results.json()).withoutRoot().from(true).serialize();
		} catch (Exception e) {
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
	}
	
	
	@Post("/salvarGrupoEvento")
	@Consumes(value="application/json")
	public void salvarGrupoEvento(GrupoEvento grupoEvento) {
		try{
			
			
			List<UsuarioEvento> listaUsuarioEvento = new ArrayList<UsuarioEvento>();
			
			for (Usuario usuario : grupoEvento.getGrupo().getListaUsuario()) {
				if(!grupoEvento.getEvento().getUsuario().getId().equals(usuario.getId())){
					UsuarioEvento userEvento = new UsuarioEvento();
					userEvento.setUsuario(usuario);
					userEvento.setStatus("P");
					listaUsuarioEvento.add(userEvento);
				}
			}
			
			grupoEvento.getEvento().setListaUsuario(listaUsuarioEvento);
			prepararEvento(grupoEvento.getEvento());
			
			GrupoEventoId id = new GrupoEventoId(grupoEvento.getGrupo().getId(), grupoEvento.getEvento().getId());
			grupoEvento.setId(id);
			
			grupoEventoDao.adiciona(grupoEvento);
			
			result.use(Results.json()).withoutRoot().from(true).serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
	
    }
	
	@Get("/pesquisaUsuarioEventoPorId/{idUsuario}/{idEvento}")
	public void pesquisaUsuarioEventoPorId(Long idUsuario, Long idEvento) {
		try{
			UsuarioEvento usuarioEvento = usuarioEventoDao.pesquisaUsuarioEventoPorId(idEvento, idUsuario);
			if(usuarioEvento != null){
				result.use(Results.json()).withoutRoot().from(usuarioEvento)
				.include("usuario")
				.include("evento").include("evento.usuario")
				.serialize();
			}else{
				result.use(Results.json()).withoutRoot().from(true).serialize();
			}
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
	}
	
	@Post("/salvarChecklist")
	@Consumes(value="application/json")
	public void salvarCheckList(Checklist check) {
		try{		
			checklistDao.adiciona(check);
			result.use(Results.json()).withoutRoot().from(check).serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
    }
	
	@Delete("/detelaChecklist/{idChecklist}")
	@Consumes(value="application/json")
	public void detelaChecklist(Long idChecklist) {
		
		try {
			Checklist checklist = new Checklist();
			checklist.setId(idChecklist);
			checklistDao.delete(checklist);
			result.use(Results.json()).withoutRoot().from(true).serialize();
		} catch (Exception e) {
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
	}
	
	@Get("/pesquisaCheckListPorEvento/{idEvento}")
	public void pesquisaCheckListPorEvento(Long idEvento) {
		try{
			List<Checklist> listaRetorno = checklistDao.pesquisaChecklistPorEvento(idEvento);
			result.use(Results.json()).withoutRoot().from(listaRetorno).serialize();
		}catch(Exception e){
			result.use(Results.json()).withoutRoot().from(false).serialize();
			e.printStackTrace();
		}
	}

}