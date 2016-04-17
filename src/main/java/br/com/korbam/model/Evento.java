package br.com.korbam.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.korbam.model.Usuario;

@Entity
@Table(name = "evento")
public class Evento implements Serializable{

	private static final long serialVersionUID = 2185040130073500707L;
	

	@Id
	@GeneratedValue
	@Column(name="ID_EVENTO")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "ID_USUARIO_CAD")  
	private Usuario usuario;
	
	@Column(name="TITULO", length=50)
	private String titulo;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INICIO")
	private Date dtInicio;
	
	@Transient
	private String dtInicioString;
	
	@Transient
	private List<Usuario> listaUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_TERMINO")
	private Date dtTermino;
	
	@Column(name="LATITUDE")
	private Double latitude;
	
	@Column(name="LONGITUDE")
	private Double longitude;
	
	@Column(name="VALOR_INGRESSO")
	private Double vlrIngresso;
	
	@Column(name="RESPONSAVEL", length=100)
	private String responsavel;
	
	@Column(name="SITUACAO", length=15)
	private String situacao;
	
	@Column(name="DESCRICAO", length=100)
	private String descricao;
	
	@Column(name="TIPO", length=15)
	private String tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtTermino() {
		return dtTermino;
	}

	public void setDtTermino(Date dtTermino) {
		this.dtTermino = dtTermino;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getVlrIngresso() {
		return vlrIngresso;
	}

	public void setVlrIngresso(Double vlrIngresso) {
		this.vlrIngresso = vlrIngresso;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public String getDtInicioString() {
		return dtInicioString;
	}

	public void setDtInicioString(String dtInicioString) {
		this.dtInicioString = dtInicioString;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
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
		Evento other = (Evento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
