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
@Table(name = "grupo_tarefa")
public class GrupoTarefa implements Serializable{

	private static final long serialVersionUID = -7813277550130132322L;

	@Id
	@GeneratedValue
	@Column(name="ID_GRUPO_TAREFA")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "ID_USUARIO_CAD")  
	private Usuario usuario;
	
	@Column(name="NOME", length=45)
	private String nome;
	
	@Column(name="COR", length=45)
	private String cor;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
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
		GrupoTarefa other = (GrupoTarefa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GrupoTarefa [id=" + id + ", usuario=" + usuario + ", nome=" + nome + ", cor=" + cor + "]";
	}
	
	

}
