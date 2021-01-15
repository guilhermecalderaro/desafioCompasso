package br.com.compasso.lambda.desafioCompasso.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.compasso.lambda.desafioCompasso.dtos.FilmeForm;
import br.com.compasso.lambda.desafioCompasso.models.Filme;
import br.com.compasso.lambda.desafioCompasso.models.Pessoa;
import br.com.compasso.lambda.desafioCompasso.services.FilmeService;
import br.com.compasso.lambda.desafioCompasso.services.PessoaService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class FilmeControllerTest {

	@Autowired
	private FilmeService service;

	@Autowired
	private MockMvc mock;

	static final ObjectMapper mapper = new ObjectMapper();

	// Ok
	@Test
	public void retornaTodosFilmes() {

		List<Filme> filmes = service.getFilmes();

		Filme filme1 = new Filme("A bordo", "Titanic", "Filme trágico", "1997-05-23", "Hollywood", "James Cameron",
				"Leonardo Dicaprio");
		Filme filme2 = new Filme(
				"An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.",
				"Harry Potter e a Pedra Filosofal", "", "2001-11-23", "Warner Bros.", "Chris Columbus",
				"Daniel Radcliffe");
		Filme filme3 = new Filme("tt", "calculadora", "ttttt", "1999-05-12", "Hollywood", "felipe alves",
				"Leonardo Da vinci");

		List<Filme> filmesTeste = new ArrayList<>();

		filmesTeste.add(filme1);
		filmesTeste.add(filme2);
		filmesTeste.add(filme3);

		assertEquals(filme1.getNome(), filmes.get(0).getNome());
		assertEquals(filme2.getNome(), filmes.get(1).getNome());
		assertEquals(filme3.getNome(), filmes.get(2).getNome());
	}

	// Ok
	@Test
	public void retornaFilmeNaoExistente() {
		Long id = 80L;
		Optional<Filme> filme = service.getFilmeById(id);

		assertTrue(filme.isEmpty());
	}

	// Not Ok
	@Test
	public void cadastraFilmeVazio() {
		FilmeForm ff = new FilmeForm(null, null, null, null, null, null, null);
		Filme filme = ff.converter();
		service.postFilme(filme);

		Long id = 4L;

		Optional<Filme> filmeTest = service.getFilmeById(id);

		System.out.println(filmeTest);

		assertTrue(filmeTest.isEmpty());
	}

//	@Test
//	public void deletaFilmeById() {
//		Long id = 1L;
//
//        Optional<Filme> filme = service.getFilmeById(id);
//        
//        service.delete(id);
//        
//	}

	@Test
	public void adicionaCategoriaNoFilme() throws Exception {
		URI uriCategoriaFilme = new URI("/filmes/associar-categoria/1/1");

		mock.perform(post(uriCategoriaFilme).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.CREATED.value()));

	}

	@Test
	public void adicionaCategoriaDuplicadaNoFilme() throws Exception {
		URI uriCategoriaFilme = new URI("/filmes/associar-categoria/1/1");

		mock.perform(post(uriCategoriaFilme).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

	}

	@Test
	@Order(1)
	public void adicionaPessoaNoFilme() throws Exception {
		URI uriCategoriaFilme = new URI("/filmes/associar-pessoa/1/2");

		mock.perform(post(uriCategoriaFilme).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.CREATED.value()));

	}

	@Test
	@Order(2)
	public void adicionaPessoaDuplicadaNoFilme() throws Exception {
		URI uriCategoriaFilme = new URI("/filmes/associar-pessoa/1/2");

		mock.perform(post(uriCategoriaFilme).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

	}

}
