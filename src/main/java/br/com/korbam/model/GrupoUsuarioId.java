package br.com.korbam.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class GrupoUsuarioId  implements Serializable{
	

	private static final long serialVersionUID = -2422603326382173329L;

	private Long idUsuario;
	
	private Long idGrupo;
	
	public GrupoUsuarioId() {
		super();
	}

	public GrupoUsuarioId(Long idUsuario, Long idGrupo) {
		super();
		this.idUsuario = idUsuario;
		this.idGrupo = idGrupo;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
		GrupoUsuarioId other = (GrupoUsuarioId) obj;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
	
	

}
