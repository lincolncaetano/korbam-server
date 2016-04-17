package br.com.korbam.model;

import java.io.Serializable;


import javax.persistence.Embeddable;

@Embeddable
public class UsuarioEventoId implements Serializable{
	
	private static final long serialVersionUID = 3122553199471486828L;

	public UsuarioEventoId() {
		super();
	}

	public UsuarioEventoId(Long idUsuario, Long idEvento) {
		super();
		this.idUsuario = idUsuario;
		this.idEvento = idEvento;
	}

	private Long idUsuario;
	
	private Long idEvento;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEvento == null) ? 0 : idEvento.hashCode());
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		UsuarioEventoId other = (UsuarioEventoId) obj;
		if (idEvento == null) {
			if (other.idEvento != null)
				return false;
		} else if (!idEvento.equals(other.idEvento))
			return false;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
	
	

}
