package br.com.korbam.controller;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.NotificacaoDao;
import br.com.korbam.model.Notificacao;


@Controller
public class NotificacaoController {
	
	@Inject 
	private Result result;
	
	@Inject
    private NotificacaoDao notificacaoDao;
	
	
	@Get
    @Path("/buscaNotificacoes/{idUsuario}")
	public void buscaNotificacoes(Long idUsuario) {
    	
    	List<Notificacao> listaNot = notificacaoDao.pesquisaEventoPorIdUsuario(idUsuario);
    	if(listaNot != null){
			result.use(Results.json()).withoutRoot().from(listaNot)
			.include("usuarioNotificado")
			.include("usuario")
			.include("evento")
			.serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
    	
    }
	
	@Post("/atualizaNotificacao")
    @Consumes(value="application/json")
	public void atualizaNotificacao(List<Notificacao> listaNotif){
		for (Notificacao notificacao : listaNotif) {
			notificacao.setStatus("A");
			notificacaoDao.adiciona(notificacao);
		}
		
		result.use(Results.json()).withoutRoot().from(false).serialize();
	}
	
	
}