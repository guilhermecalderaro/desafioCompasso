package br.com.compasso.lambda.desafioCompasso.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.lambda.desafioCompasso.dtos.FilmeDto;
import br.com.compasso.lambda.desafioCompasso.dtos.FilmeForm;
import br.com.compasso.lambda.desafioCompasso.models.Filme;
import br.com.compasso.lambda.desafioCompasso.repositories.FilmeRepository;

@RestController
@RequestMapping(value = "/filmes")
public class FilmeController {

	@Autowired
	private FilmeRepository filmeRepository;
	
	@GetMapping
	public List<FilmeDto> filmes(){
		List<Filme> filmes = filmeRepository.findAll();
				
		return FilmeDto.converter(filmes);
	}
	
	@PostMapping
	public ResponseEntity<FilmeDto> cadastrar(@RequestBody FilmeForm form, UriComponentsBuilder uriBuilder) {
		Filme filme = form.converter();
		filmeRepository.save(filme);
		URI uri = uriBuilder.path("/filmes/{id}").buildAndExpand(filme.getId()).toUri();
		return ResponseEntity.created(uri).body(new FilmeDto(filme));
	}
	
}
