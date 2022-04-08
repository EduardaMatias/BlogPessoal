package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) /* a api vai subir numa posta randomica */
@TestInstance(TestInstance.Lifecycle.PER_CLASS) /* indica que é teste unitário(por classe) */
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {
		usuarioRepository
				.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "123456", "https://imgur.com/a/OKUfbq7"));

		usuarioRepository.save(
				new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "123456", "https://imgur.com/a/OKUfbq7"));

		usuarioRepository.save(
				new Usuario(0L, "Adriana da Silva", "adriana@email.com", "123456", "https://imgur.com/a/OKUfbq7"));

		usuarioRepository
				.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com", "123456", "https://imgur.com/a/OKUfbq7"));
	}

	@Test
	@DisplayName("Retorna apenas um usuário")
	public void deveRetornarUmUsuario() {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}

	@Test
	@DisplayName("Retorna três usuários")
	public void deveRetornaTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals/* indica que a função vai retornar apenas 2 usuarios */(3 /* resultado perfeito */,
				listaDeUsuarios.size() /* tamanho da requisição listaDeUsuarios */);

		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
	}

}
