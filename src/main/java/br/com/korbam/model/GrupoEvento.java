package br.com.korbam.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "grupo_has_evento")
public class GrupoEvento implements Serializable{

	private static final long serialVersionUID = -4304936618485222513L;

	@EmbeddedId 
	private GrupoEventoId id;
	
	@MapsId("idGrupo")
    @JoinColumn(name="ID_GRUPO", referencedColumnName="ID_GRUPO")
	@ManyToOne 
	private Grupo grupo;
	
	@MapsId("idEvento")
    @JoinColumn(name="ID_EVENTO", referencedColumnName="ID_EVENTO")
	@ManyToOne 
	private Evento evento;

	public GrupoEventoId getId() {
		return id;
	}

	public void setId(GrupoEventoId id) {
		this.id = id;
	}


	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
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
		GrupoEvento other = (GrupoEvento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}

