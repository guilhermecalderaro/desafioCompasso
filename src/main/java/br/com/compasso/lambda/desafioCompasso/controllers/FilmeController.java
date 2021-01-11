package br.com.compasso.lambda.desafioCompasso.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.lambda.desafioCompasso.dtos.FilmeCompletoDto;
import br.com.compasso.lambda.desafioCompasso.dtos.FilmeDto;
import br.com.compasso.lambda.desafioCompasso.dtos.FilmeForm;
import br.com.compasso.lambda.desafioCompasso.models.Filme;
import br.com.compasso.lambda.desafioCompasso.services.FilmeService;

@RestController
@RequestMapping(value = "/filmes")
public class FilmeController {

	@Autowired
	private FilmeService filmeService;
	//
	@GetMapping
	public List<FilmeDto> filmes() {
		List<Filme> filmes = filmeService.getFilmes();
		return FilmeDto.converter(filmes);
	}
	
	@GetMapping(value = "/completo")
	public List<FilmeCompletoDto> filmesCompletos() {
		List<Filme> filmes = filmeService.getFilmes();
		return FilmeCompletoDto.converter(filmes);
	}


	@PostMapping
	public  ResponseEntity<FilmeCompletoDto> cadastrar(@RequestBody @Valid FilmeForm form, UriComponentsBuilder uriBuilder) {
		Filme filme = form.converter();
		return filmeService.postFilme(filme, uriBuilder);
		
		//return ResponseEntity.created(uri).body(new FilmeCompletoDto(filme));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Filme> atualizar(@PathVariable Long id, @RequestBody Filme obj){
		obj = filmeService.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		filmeService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
