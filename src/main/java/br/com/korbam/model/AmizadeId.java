package br.com.korbam.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class AmizadeId  implements Serializable{
	
	private static final long serialVersionUID = -5985221641198906742L;
	
	private Long idUsuario;
	private Long idUsuarioSolicitato;
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdUsuarioSolicitato() {
		return idUsuarioSolicitato;
	}
	public void setIdUsuarioSolicitato(Long idUsuarioSolicitato) {
		this.idUsuarioSolicitato = idUsuarioSolicitato;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result + ((idUsuarioSolicitato == null) ? 0 : idUsuarioSolicitato.hashCode());
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
		AmizadeId other = (AmizadeId) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		if (idUsuarioSolicitato == null) {
			if (other.idUsuarioSolicitato != null)
				return false;
		} else if (!idUsuarioSolicitato.equals(other.idUsuarioSolicitato))
			return false;
		return true;
	}
	
	
	

}
