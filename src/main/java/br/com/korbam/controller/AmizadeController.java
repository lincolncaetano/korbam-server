package br.com.korbam.controller;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.AmizadeDao;
import br.com.korbam.model.Amizade;
import br.com.korbam.model.Usuario;



@Controller
public class AmizadeController {
	
	@Inject 
	private Result result;
	
	@Inject
    private AmizadeDao amizadeDao;
	

	
	@Post("/solicitaAmizade")
	@Consumes(value="application/json")
	public void solicitaAmizade(Amizade amz) {
		
		amz.setUsuario(new Usuario(amz.getId().getIdUsuario()));
		amz.setUsuarioSolicitado(new Usuario(amz.getId().getIdUsuarioSolicitato()));
			
		amizadeDao.adiciona(amz);
		
		result.use(Results.json()).withoutRoot().from(true).serialize();
	
    }
	
	@Get
	@Path("/listaAmigos/{idUsuario}")
	public void listaAmigos(Long idUsuario) {
		
		List<Amizade> lista = amizadeDao.listaTodos();
		List<Usuario> listaAmi = new ArrayList<Usuario>();
		for (Amizade amizade : lista) {
			if(amizade.getUsuario().getId().equals(idUsuario)){
				listaAmi.add(amizade.getUsuarioSolicitado());
			}else{
				listaAmi.add(amizade.getUsuario());
			}
		}
		
		if(!listaAmi.isEmpty()){
			result.use(Results.json()).withoutRoot().from(listaAmi).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
	}

}