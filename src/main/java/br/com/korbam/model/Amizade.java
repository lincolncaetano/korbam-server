package br.com.korbam.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "amizade")
public class Amizade  implements Serializable{

	private static final long serialVersionUID = 3670795334917459867L;

	@EmbeddedId 
	private AmizadeId id;
	
	@MapsId("idUsuario")
    @JoinColumn(name="Amizade_ID_Usuario", referencedColumnName="ID_USUARIO")
	@OneToOne 
	private Usuario usuario;
	
	@MapsId("idUsuarioSolicitato")
    @JoinColumn(name="Amizade_ID_Usuario_solicitado", referencedColumnName="ID_USUARIO")
	@OneToOne 
	private Usuario usuarioSolicitado;
	
	private String status;

	public AmizadeId getId() {
		return id;
	}

	public void setId(AmizadeId id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioSolicitado() {
		return usuarioSolicitado;
	}

	public void setUsuarioSolicitado(Usuario usuarioSolicitado) {
		this.usuarioSolicitado = usuarioSolicitado;
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
		Amizade other = (Amizade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
}
