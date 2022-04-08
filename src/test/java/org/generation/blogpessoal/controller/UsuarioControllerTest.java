package org.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Test
	@Order(1)
	@DisplayName("Cadastrar apenas um usuário")
	public void deveCadastrarUmUsuario() {
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0l, "Chandler Bing",
				"bingchandler@gmail.com", "monicageller", "https://imgur.com/a/OKUfbq7"));

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
				Usuario.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode()); /* valida se a resposta é igual a 201(created) */
		assertEquals(requisicao.getBody().getNome(),
				resposta.getBody().getNome()); /* verifica se o nome e usuario forem persistidos no db */
		assertEquals(requisicao.getBody().getUsuario(),
				resposta.getBody().getUsuario()); /* traz o usuario que foi criado */

	}

	@Test
	@Order(2)
	@DisplayName("Não deve permitir a duplicação de Usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L, "Maria da Silva", "maria_silva@email.com.br", "12345678",
				"https://imgur.com/a/OKUfbq7"));

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, "Maria da Silva",
				"maria_silva@email.com.br", "12345678", "https://imgur.com/a/OKUfbq7"));

		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao,
				Usuario.class);

		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
	}

	@Test
	@Order(3)
	@DisplayName("Alterar um usuario")
	public void deveAtualizarUmUsuario() {

		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L, "Juliana Andrews",
				"juliana_andrews@email.com.br", "juliana123", "https://imgur.com/a/OKUfbq7"));

		Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), "Juliana Andrews Ramos",
				"juliana_ramos@email.com.br", "juliana123", "https://imgur.com/a/OKUfbq7");

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
		assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario());
	}

	@Test
	@Order(4)
	@DisplayName("Listar todos os usuários")
	public void deveMostrarTodosUsuarios() {

		usuarioService.cadastrarUsuario(new Usuario(0L, "Sabrina Sanches", "sabrina_sanches@email.com.br",
				"sabrina 123", "https://imgur.com/a/OKUfbq7"));

		usuarioService.cadastrarUsuario(new Usuario(0L, "Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123",
				"https://imgur.com/a/OKUfbq7"));

		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/usuarios/all",
				HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
