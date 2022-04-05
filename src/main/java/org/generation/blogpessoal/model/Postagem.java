package org.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity /* transforma em tabela */
@Table(name = "tb_postagens") /* nomeia tabela */
public class Postagem {

	@Id /* informa que é uma chave primária */
	@GeneratedValue(strategy = GenerationType.IDENTITY) /* indica o auto increment */
	private Long id;

	@NotNull /* garante que a coluna não vai aceitar valor ´null´ */
	private String titulo;

	@NotNull
	@Size(min = 4, max = 50) /* define valor max e min de caracteres */
	private String texto;

	@UpdateTimestamp /* formata de acordo com a data do computador */
	private LocalDateTime data;

	/* relacionamento */
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

	/* getters e setter para pegar e enviar dados */
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

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
