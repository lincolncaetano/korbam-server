package br.com.korbam.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_has_evento")
public class UsuarioEvento implements Serializable{


	private static final long serialVersionUID = -4610816972671115337L;
	
	@EmbeddedId 
	private UsuarioEventoId id;
	
	@MapsId("idUsuario")
    @JoinColumn(name="Usuario_ID_USUARIO", referencedColumnName="ID_USUARIO")
	@ManyToOne 
	private Usuario usuario;
	
	@MapsId("idEvento")
    @JoinColumn(name="Evento_ID_EVENTO", referencedColumnName="ID_EVENTO")
	@ManyToOne 
	private Evento evento;

	public UsuarioEventoId getId() {
		return id;
	}

	public void setId(UsuarioEventoId id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
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
		UsuarioEvento other = (UsuarioEvento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}

