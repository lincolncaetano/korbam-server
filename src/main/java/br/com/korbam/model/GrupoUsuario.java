package br.com.korbam.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "grupo_has_usuario")
public class GrupoUsuario  implements Serializable{

	private static final long serialVersionUID = -7563739561494481142L;
	
	@EmbeddedId 
	private GrupoUsuarioId id;
	
	@MapsId("idUsuario")
    @JoinColumn(name="Usuario_ID_USUARIO", referencedColumnName="ID_USUARIO")
	@ManyToOne 
	private Usuario usuario;
	
	@MapsId("idGrupo")
    @JoinColumn(name="Grupo_idGrupo", referencedColumnName="idGrupo")
	@ManyToOne 
	private Grupo grupo;

	public GrupoUsuarioId getId() {
		return id;
	}

	public void setId(GrupoUsuarioId id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		GrupoUsuario other = (GrupoUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
