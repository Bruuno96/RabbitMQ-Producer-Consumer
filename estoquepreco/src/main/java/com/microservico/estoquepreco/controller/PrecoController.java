package com.microservico.estoquepreco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservico.estoquepreco.constantes.RabbitmqConstantes;
import com.microservico.estoquepreco.dto.PrecoDTO;
import com.microservico.estoquepreco.service.RabbitMQService;

@RestController
@RequestMapping(value = "preco")
public class PrecoController {
	
	@Autowired
	private RabbitMQService service;
	
	
	@PutMapping
	private ResponseEntity alteraPreco(@RequestBody PrecoDTO precoDto) {
		
		this.service.enviaMensagem(RabbitmqConstantes.FILA_PRECO, precoDto);
		
		return new ResponseEntity(HttpStatus.OK);		
	}

}
