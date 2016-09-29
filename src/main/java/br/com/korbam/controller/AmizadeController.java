package br.com.korbam.controller;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.AmizadeDao;
import br.com.korbam.dao.NotificacaoDao;
import br.com.korbam.dao.UsuarioDeviceDao;
import br.com.korbam.model.Amizade;
import br.com.korbam.model.AmizadeId;
import br.com.korbam.model.Notificacao;
import br.com.korbam.model.Usuario;
import br.com.korbam.model.UsuarioDevice;



@Controller
public class AmizadeController {
	
	@Inject 
	private Result result;
	
	@Inject
    private AmizadeDao amizadeDao;

	@Inject
	private NotificacaoDao notificacaoDao;
	
	@Inject
	private UsuarioDeviceDao usuarioDeviceDao;
	
	
	@Post("/solicitaAmizade")
	@Consumes(value="application/json")
	public void solicitaAmizade(Amizade amz) {
		
		amz.setUsuario(new Usuario(amz.getId().getIdUsuario()));
		amz.setUsuarioSolicitado(new Usuario(amz.getId().getIdUsuarioSolicitato()));
		amizadeDao.adiciona(amz);
		
		Notificacao notificacao = new Notificacao(amz);
		notificacao.setMensagem("Amizade Solicitada");
		notificacaoDao.adiciona(notificacao);
		
		List<UsuarioDevice> listaUsuarioDevice = usuarioDeviceDao.pesquisaUsuarioPorIdUsuario(amz.getId().getIdUsuarioSolicitato());
		for (UsuarioDevice usuarioDevice : listaUsuarioDevice) {
			if(usuarioDevice.getTipoDevice().equals("I")){
				EnviaNotificacaoIOS enviaNotIOS = new EnviaNotificacaoIOS("Amizade Solicitada", usuarioDevice.getTokenDevice());
				Thread threadNot = new Thread(enviaNotIOS);
				threadNot.start();	
			}else{
				EnviaNotificacaoAndroid enviaNot = new EnviaNotificacaoAndroid("Amizade Solicitada", usuarioDevice.getTokenDevice());
				Thread threadNot = new Thread(enviaNot);
				threadNot.start();
			}
		}
		
		result.use(Results.json()).withoutRoot().from(true).serialize();
	
    }
	
	@Get
	@Path("/listaAmigos/{idUsuario}")
	public void listaAmigos(Long idUsuario) {
		
		List<Amizade> lista = amizadeDao.listaTodosAmigos(idUsuario);
		
		List<Usuario> listaAmi = new ArrayList<Usuario>();
		for (Amizade amizade : lista) {
			if(amizade.getUsuario().getId().equals(idUsuario)){
				listaAmi.add(amizade.getUsuarioSolicitado());
			}else if(amizade.getUsuarioSolicitado().getId().equals(idUsuario)){
				listaAmi.add(amizade.getUsuario());
			}
		}

		if(!listaAmi.isEmpty()){
			result.use(Results.json()).withoutRoot().from(listaAmi).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
	}
	
	@Get
	@Path("/buscaAmizadeId/{idUsuario}/{idUsuarioSolicitado}")
	public void statusAmizade(Long idUsuario, Long idUsuarioSolicitado) {
		
		AmizadeId amzId = new AmizadeId(idUsuario, idUsuarioSolicitado);
		Amizade amz = new Amizade();
		amz.setId(amzId);
		
		Amizade amizadeRet = amizadeDao.pesquisaAmizadePorId(amz);
		
		if(amizadeRet!= null){
			result.use(Results.json()).withoutRoot().from(amizadeRet).include("id").serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
	}
	
	@Delete("/cancelarAmizade/{idUsuario}/{idUsuarioSolicitado}")
	@Consumes(value="application/json")
	public void cancelarAmizade(Long idUsuario, Long idUsuarioSolicitado) {
		
		AmizadeId amzId = new AmizadeId(idUsuario, idUsuarioSolicitado);
		Amizade amz = new Amizade();
		amz.setId(amzId);
		
		try {
			amizadeDao.delete(amz);
			result.use(Results.json()).withoutRoot().from(true).serialize();
		} catch (Exception e) {
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
	}
}