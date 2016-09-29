package br.com.korbam.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.korbam.utils.TipoNotificacao;

@Entity
@Table(name = "notificacao")
public class Notificacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1584090016018074865L;
	
	
	@Id
	@GeneratedValue
	@Column(name="ID_NOTIFICACAO")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "ID_USUARIO_NOTIFICADO")  
	private Usuario usuarioNotificado;
	
	@ManyToOne
    @JoinColumn(name = "ID_USUARIO_ENVIO")  
	private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "ID_EVENTO")  
	private Evento evento;
	
	@Column(name="MENSAGEM", length=45)
	private String mensagem;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA")
	private Date dtNotificacao;
	
	@Column(name="TIPO")
	private Long tipo;
	
	@Column(name="STATUS")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Usuario getUsuarioNotificado() {
		return usuarioNotificado;
	}

	public void setUsuarioNotificado(Usuario usuarioNotificado) {
		this.usuarioNotificado = usuarioNotificado;
	}

	public Date getDtNotificacao() {
		return dtNotificacao;
	}

	public void setDtNotificacao(Date dtNotificacao) {
		this.dtNotificacao = dtNotificacao;
	}
	
	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notificacao other = (Notificacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Notificacao(Amizade amz) {
		super();
		this.tipo = TipoNotificacao.AMIZADE.getCodigo();
		setUsuarioNotificado(new Usuario(amz.getId().getIdUsuarioSolicitato()));
		setUsuario(new Usuario(amz.getId().getIdUsuario()));
		setDtNotificacao(new Date());
		this.status = "P";
	}
	
	public Notificacao(UsuarioEvento usuarioEvento) {
		super();
		this.tipo = TipoNotificacao.EVENTO.getCodigo();
		setUsuarioNotificado(new Usuario(usuarioEvento.getUsuario().getId()));
		setUsuario(new Usuario(usuarioEvento.getEvento().getUsuario().getId()));
		setEvento(usuarioEvento.getEvento());
		setDtNotificacao(new Date());
		this.status = "P";
	}
	
	public Notificacao() {
		super();
		setDtNotificacao(new Date());
	}
	
	

}
