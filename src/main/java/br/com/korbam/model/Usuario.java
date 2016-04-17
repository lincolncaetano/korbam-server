package br.com.korbam.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
	

	private static final long serialVersionUID = 7432146009377841133L;

	@Id
	@GeneratedValue
	@Column(name="ID_USUARIO")
	private Long id;
	
	@Column(name="NICKNAME", length=100)
	private String username;
	
	@Column(name="PRIMEIRO_NOME", length=45)
	private String nome;
	
	@Column(name="ULTIMO_NOME", length=45)
	private String ultimoNome;
	
	@Column(name="SEXO", length=12)
	private String sexo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_NASCIMENTO")
	private Date dataNascimento;
	
	@Column(name="ESTADO_CIVIL", length=15)
	private String estadoCivil;
	
	
	@Column(name="EMAIL", length=100)
	private String email;
	
	@Column(name="EMAIL_ALTERNATIVO", length=100)
	private String emailAlternativo;
	
	@Column(name="PAIS", length=50)
	private String pais;
	
	@Column(name="ESTADO", length=2)
	private String estado;
	
	@Column(name="CIDADE", length=50)
	private String cidade;
	
	@Column(name="SENHA", length=50)
	private String password;
	
	@Column(name="TOKEN", length=5)
	private String token;
	
	
	@OneToMany(mappedBy="id.idUsuario")
    private List<Amizade> listaAmigos;
	
	public Usuario() {
		super();
	}
	
	public Usuario(Long id) {
		super();
		this.id = id;
	}
	
	public List<Amizade> getListaAmigos() {
		return listaAmigos;
	}

	public void setListaAmigos(List<Amizade> listaSeguindo) {
		this.listaAmigos = listaSeguindo;
	}

	@Transient
	private String foto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailAlternativo() {
		return emailAlternativo;
	}

	public void setEmailAlternativo(String emailAlternativo) {
		this.emailAlternativo = emailAlternativo;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nome=" + nome + ", ultimoNome=" + ultimoNome
				+ ", sexo=" + sexo + ", dataNascimento=" + dataNascimento + ", estadoCivil=" + estadoCivil + ", email="
				+ email + ", emailAlternativo=" + emailAlternativo + ", pais=" + pais + ", estado=" + estado
				+ ", cidade=" + cidade + ", password=" + password + ", foto=" + foto + "]";
	}

	
	
	

}
