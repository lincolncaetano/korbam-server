package br.com.korbam.controller;

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
import br.com.korbam.dao.UsuarioDeviceDao;
import br.com.korbam.model.UsuarioDevice;


@Controller
public class UsuarioDeviceController {
	
	@Inject 
	private Result result;
	
	@Inject
    private UsuarioDeviceDao usuarioDeviceDao;
	
	
	@Post("/cadastrarTokenDevice")
	@Consumes(value="application/json")
	public void cadastrarTokenDevice(UsuarioDevice userDevice) {

		try {
			List<UsuarioDevice> listaUsuarioDevice = usuarioDeviceDao.pesquisaUsuarioPorToken(userDevice.getTokenDevice());
	    	if(!listaUsuarioDevice.isEmpty()){
	    		for (UsuarioDevice usuarioDevice : listaUsuarioDevice) {
					if(usuarioDevice.getUsuario().getId() == userDevice.getUsuario().getId()){
						userDevice.setId(usuarioDevice.getId());
					}
				}
	    	}
			
			usuarioDeviceDao.adiciona(userDevice);
			result.use(Results.json()).withoutRoot().from(userDevice).serialize();
		} catch (Exception e) {
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
    }

	@Get
    @Path("/buscaUsuarioDevice/{idUsuario}")
	public void buscaUsuarioDevice(Long idUsuario) {
    	
    	List<UsuarioDevice> listaUsuarioDevice = usuarioDeviceDao.pesquisaUsuarioPorIdUsuario(idUsuario);
    	if(listaUsuarioDevice != null){
			result.use(Results.json()).withoutRoot().from(listaUsuarioDevice)
			.include("usuario")
			.serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
  	
    }
	
	@Delete("/detelaPorToken/{token}")
	@Consumes(value="application/json")
	public void detelaPorToken(String token) {
		
		try {
			usuarioDeviceDao.deletePorToken(token);
			result.use(Results.json()).withoutRoot().from(true).serialize();
		} catch (Exception e) {
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
		
	}
	
	
}