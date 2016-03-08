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
import br.com.korbam.dao.AmizadeDao;
import br.com.korbam.dao.UsuarioDao;
import br.com.korbam.model.Amizade;
import br.com.korbam.model.Usuario;

import java.security.*;
import java.math.*;

@Controller
public class AmizadeController {
	
	@Inject 
	private Result result;
	
	@Inject
	private Mailer mailer;
	

	@Inject
    private AmizadeDao amizadeDao;
	
	

	
	@Post("/solicitaAmizade")
	@Consumes(value="application/json")
	public void solicitaAmizade(Amizade amz) {
	
    }

}