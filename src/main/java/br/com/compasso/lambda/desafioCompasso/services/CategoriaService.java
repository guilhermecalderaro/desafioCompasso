package br.com.compasso.lambda.desafioCompasso.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.lambda.desafioCompasso.dtos.CategoriaDto;
import br.com.compasso.lambda.desafioCompasso.dtos.CategoriaForm;
import br.com.compasso.lambda.desafioCompasso.exception.ObjectNotFoundException;
import br.com.compasso.lambda.desafioCompasso.models.Categoria;
import br.com.compasso.lambda.desafioCompasso.models.Filme;
import br.com.compasso.lambda.desafioCompasso.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Transactional(readOnly = true)
	public List<Categoria> getCategorias() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias;
	}
	
	public void delete(long id) {
		categoriaRepository.deleteById(id);
	}

	public Categoria getById(long id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class));
	}
	
	public Optional<Categoria> findByNome(String nome) {
		return categoriaRepository.findByNome(nome);
	}

	public ResponseEntity<CategoriaDto> postCategoria(@Valid CategoriaForm form, UriComponentsBuilder uriBuilder) {
		Categoria categoria = form.converter();
		List<Categoria> categorias = getCategorias();
		if (categorias.contains(categoria)) {
			System.out.println("Tentou adicionar Categoria repetida");
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		else if (categoria.getNome() == null || categoria.getNome().isEmpty())
		{
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}
		
		categoriaRepository.save(categoria);
		
		URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoriaDto(categoria));
	}
}

