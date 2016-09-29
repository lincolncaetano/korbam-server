package br.com.korbam.utils;

public enum TipoNotificacao {

	AMIZADE(1L, "AMIZADE"),
    GRUPO(2L, "GRUPO"),
    HORARIO(3L, "HORARIO"),
    EVENTO(4L, "EVENTO");
	
	private Long codigo;
    private String tipo;
	
	private TipoNotificacao(Long codigo, String tipo) {
		this.codigo = codigo;
		this.tipo = tipo;
	}
	
	public Long getCodigo() {
		return codigo;
	}
	public String getTipo() {
		return tipo;
	}
	
	
    
    

}
