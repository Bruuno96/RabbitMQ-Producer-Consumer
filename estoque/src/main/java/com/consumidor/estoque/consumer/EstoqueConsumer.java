package com.consumidor.estoque.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.microservico.estoquepreco.constantes.RabbitmqConstantes;
import com.microservico.estoquepreco.dto.EstoqueDTO;

@Component
public class EstoqueConsumer {
	
	@RabbitListener(queues= RabbitmqConstantes.FILA_ESTOQUE)
	private void consumidor(EstoqueDTO estoqueDto) {
		System.out.println("==================== RESPOSTA =========================");
		System.out.println("Código do produto: "+estoqueDto.codigoProduto);
		System.out.println("Quantidade do produto: "+estoqueDto.quantidade);
		System.out.println("=============================================");
	}

}
