package br.com.korbam.model;

import java.io.Serializable;


import javax.persistence.Embeddable;

@Embeddable
public class GrupoEventoId implements Serializable{
	
	private static final long serialVersionUID = -7076799817610143825L;

	public GrupoEventoId() {
		super();
	}

	public GrupoEventoId(Long idGrupo, Long idEvento) {
		super();
		this.idGrupo = idGrupo;
		this.idEvento = idEvento;
	}

	private Long idGrupo;
	
	private Long idEvento;

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
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
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
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
		GrupoEventoId other = (GrupoEventoId) obj;
		if (idEvento == null) {
			if (other.idEvento != null)
				return false;
		} else if (!idEvento.equals(other.idEvento))
			return false;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}



}
