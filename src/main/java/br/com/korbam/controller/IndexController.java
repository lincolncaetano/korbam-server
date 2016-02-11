package br.com.korbam.controller;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.simplemail.Mailer;
import br.com.caelum.vraptor.view.Results;
import br.com.korbam.dao.UsuarioDao;
import br.com.korbam.model.Usuario;

import java.security.*;
import java.math.*;

@Controller
public class IndexController {
	
	@Inject 
	private Result result;
	
	@Inject
	private Mailer mailer;
	
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
		
		String s= usr.getPassword();
		MessageDigest m;
		
		System.out.println(usr.getUsername());
		System.out.println(usr.getPassword());
		
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			String passwordCpt = new BigInteger(1,m.digest()).toString(16)+"";
			
			if(usuario != null && usuario.getPassword().equals(passwordCpt)){
				result.use(Results.json()).withoutRoot().from(usuario).serialize();
			}else{
				result.use(Results.json()).withoutRoot().from(false).serialize();
			}
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
    }
	
	@Get("/pesquisa")
	public void pesquisa() {
		

		List<Usuario> lista = usuarioDao.listaTodos();
		if(lista != null){
			result.use(Results.json()).withoutRoot().from(lista).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from("false").serialize();
		}
		
    }
	
	@Post("/cadastrarUsuario")
	@Consumes(value="application/json")
	public void cadastrarUsuario(Usuario usr) {

		try {
			
			String s= usr.getPassword();
			MessageDigest m=MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			
			String passwordCpt = new BigInteger(1,m.digest()).toString(16)+"";
			usr.setPassword(passwordCpt);
			
			
			usuarioDao.adiciona(usr);
			result.use(Results.json()).withoutRoot().from("true").serialize();
		} catch (Exception e) {
			result.use(Results.json()).withoutRoot().from("false").serialize();
		}
		
    }
	
	@Post("/enviarToken")
	@Consumes(value="application/json")
	public void enviarEmail(Usuario usr) {
		try {
		
			Usuario usrToken = usuarioDao.pesquisaUsuarioPorEmail(usr);
			if(usrToken!= null){
				
				int min = 10000;
				int max = 99999;
				int token = (int) (Math.random() * (max - min) + min);
				
				usrToken.setToken(token+"");
				usuarioDao.adiciona(usrToken);
				
				Email email = new SimpleEmail();
				email.setSubject("teste Email");
				email.addTo("lincolncaetano@gmail.com");
				email.setMsg("Token = "+token+"");
				
				mailer.send(email);
				
				
			}
			result.use(Results.json()).withoutRoot().from(true).serialize();
		
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
    }
	
	@Post("/alteraSenha")
	@Consumes(value="application/json")
	public void alteraSenha(Usuario usr) {
		
		Usuario usrToken = usuarioDao.pesquisaUsuarioPorEmail(usr);
		if(usrToken.getToken().equals(usr.getToken())){

			usrToken.setPassword(usr.getPassword());
			usrToken.setToken("");
			usuarioDao.adiciona(usrToken);
			result.use(Results.json()).withoutRoot().from(true).serialize();
			
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
	}
	
	@Get
	@Path("/validaEmail/{usr.email}")
	public void validaEmail(Usuario usr) {
		
		Usuario user = usuarioDao.pesquisaUsuarioPorEmail(usr);
		
		if(user != null){
			result.use(Results.json()).withoutRoot().from(true).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
	}
	
	@Get
	@Path("/validaUsername/{usr.username}")
	public void validaUsername(Usuario usr) {
		
		Usuario user = usuarioDao.pesquisaUsuarioPorUsername(usr);
		
		if(user != null){
			result.use(Results.json()).withoutRoot().from(true).serialize();
		}else{
			result.use(Results.json()).withoutRoot().from(false).serialize();
		}
	}
	
	@Get
    @Path("/pesquisaUsuario/{userName}")
	public void listaPostagem(String userName) {
    	
    	List<Usuario> lista = usuarioDao.pesquisaUsuarioPorUserName(userName);
    	result.use(Results.json()).withoutRoot().from(lista).serialize();
    	
    }


	
}