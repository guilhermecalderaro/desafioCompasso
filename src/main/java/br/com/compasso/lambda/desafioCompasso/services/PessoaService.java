package br.com.compasso.lambda.desafioCompasso.services;

<<<<<<< HEAD
import java.util.List;
=======
import java.net.URI;
>>>>>>> c101f2717e0793b669d8e5f0dacb2d0a384016ba
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.compasso.lambda.desafioCompasso.dtos.AtualizacaoTopicoForm;
import br.com.compasso.lambda.desafioCompasso.dtos.PessoaDto;
<<<<<<< HEAD
import br.com.compasso.lambda.desafioCompasso.models.Filme;
=======
import br.com.compasso.lambda.desafioCompasso.dtos.PessoaForm;
>>>>>>> c101f2717e0793b669d8e5f0dacb2d0a384016ba
import br.com.compasso.lambda.desafioCompasso.models.Pessoa;
import br.com.compasso.lambda.desafioCompasso.repositories.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	//Métodoss

	@Transactional(readOnly = true)
	public Page<PessoaDto> retornaTodas(Pageable paginacao){
		Page<Pessoa> pessoas = pessoaRepository.findAll(paginacao);
		
		return PessoaDto.converter(pessoas);
	}

	@Transactional(readOnly = true)
	public Optional<Pessoa> findById(Long id) {
		return pessoaRepository.findById(id);
	}

<<<<<<< HEAD
	public List<Filme> getPessoaFilmes() {
		
		return null;
	}
	
	
=======
	public ResponseEntity<PessoaDto> cadastrarPessoa(
			@Valid PessoaForm form,
			UriComponentsBuilder uriBuilder) {
		Pessoa pessoa = form.converter();
		pessoaRepository.save(pessoa);
				
		URI uri = uriBuilder.path("/pessoas/{id}")
				.buildAndExpand(pessoa.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(new PessoaDto(pessoa));
	}

	public ResponseEntity<PessoaDto> atualizarPessoa(Long id, @Valid AtualizacaoTopicoForm form) {
		Optional<Pessoa> optional = pessoaRepository.findById(id);
		if(optional.isPresent()) {
			optional.get().setNome(form.getNome());
			optional.get().setIdade(form.getIdade());
			Pessoa pessoaAtualizada = pessoaRepository.save(optional.get());
			return ResponseEntity.ok(new PessoaDto(pessoaAtualizada));
		}
		
		return ResponseEntity.notFound().build();
	}
>>>>>>> c101f2717e0793b669d8e5f0dacb2d0a384016ba


}
