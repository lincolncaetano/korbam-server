package br.com.korbam.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_device")
public class UsuarioDevice implements Serializable{

	private static final long serialVersionUID = 3208370127517635003L;
	
	@Id
	@GeneratedValue
	@Column(name="ID_USUARIO_DEVICE")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "ID_USUARIO")  
	private Usuario usuario;

	@Column(name="TOKEN_DEVICE", length=300)
	private String tokenDevice;

	@Column(name="TIPO_DEVICE", length=1)
	private String tipoDevice;

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

	public String getTokenDevice() {
		return tokenDevice;
	}

	public void setTokenDevice(String tokenDevice) {
		this.tokenDevice = tokenDevice;
	}

	public String getTipoDevice() {
		return tipoDevice;
	}

	public void setTipoDevice(String tipoDevice) {
		this.tipoDevice = tipoDevice;
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
		UsuarioDevice other = (UsuarioDevice) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
