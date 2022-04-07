package org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.model.UsuarioLogin;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.generation.blogpessoal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private UsuarioService service;

	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastraUsuario(@Valid @RequestBody Usuario usuario) {
		return service.cadastrarUsuario(usuario)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}

	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> logarUsuario(@Valid @RequestBody Optional<UsuarioLogin> user) {
		return service.autenticarUsuario(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario){		
		return service.atualizarUsuario(usuario)
			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
}
