package br.com.korbam.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.UsuarioDao;
import br.com.korbam.model.Usuario;

@Controller
public class IndexController {
	
	@Inject 
	private Result result;
	
	@Get("/")
    public void home() {
        result.include("mensagem", "Olá, VRaptor 4!");
    }

	@Inject
    private UsuarioDao usuarioDao;
	
	
	@Post("/login")
    @Consumes(value="application/json")
	public void login(Usuario usr) {
		

		Usuario usuario = usuarioDao.pesquisaUsuarioPorUserName(usr);
		if(usuario != null){
			result.use(Results.json()).withoutRoot().from(usuario).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from("false").serialize();
		}
		
    }
	
}