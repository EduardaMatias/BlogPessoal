package org.generation.blogpessoal.repository;

import java.util.List;

import org.generation.blogpessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>
/*indica que é um repository de postagem*/
{
	public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo); 
	/*buscar todos pelo titulo*/
}
