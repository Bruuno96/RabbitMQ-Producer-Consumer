package com.microservico.estoquepreco.connections;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import com.microservico.estoquepreco.constantes.RabbitmqConstantes;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;


@Component
public class RabbitMQConnection {
	
	private static final String NOME_EXCHANGE = "amq.direct";
	
	private AmqpAdmin amqpadmin; 
	
	public RabbitMQConnection(AmqpAdmin amqpadmin) {
		this.amqpadmin = amqpadmin; 
	}

	// Método que retorna uma fila 
	private Queue fila(String nomeFila) {
		// parametros do construtor da fila (nome da fila, durável= true/false, exclusiva= true/false, autoDelete= true/false)
		return new Queue(nomeFila, true, false,false);
	}
	
	// Método que retorna uma exchange do tipo direct, no caso ja setamos na linha 19 uma constante da exchange como amqp.direct
	private DirectExchange trocaDireta() {
		return new DirectExchange(NOME_EXCHANGE) ;
		
	}
	
	// Método que retorna o binding (relacionamento) da fila em questão com a exchange
	private Binding relacionamento(Queue fila, DirectExchange troca) {
		return new Binding(fila.getName(), DestinationType.QUEUE, troca.getName(), fila.getName(), null);
	}
	
	// Método que cria as filas e faz o relacionamento, a anotação @PostConstruct serve para quando a aplicação subir ele ja executar esse método adiciona()
	@PostConstruct
	private void adiciona() {
		Queue filaEstoque = this.fila(RabbitmqConstantes.FILA_ESTOQUE);
		Queue filaPreco = this.fila(RabbitmqConstantes.FILA_PRECO);
		
		DirectExchange troca = this.trocaDireta();
		
		Binding ligacaoEstoque = this.relacionamento(filaEstoque, troca);
		Binding ligacaoPreco = this.relacionamento(filaPreco, troca);

		
		// Criando as filas no RABBITMQ
		this.amqpadmin.declareQueue(filaEstoque);
		
		this.amqpadmin.declareQueue(filaPreco);
		
		this.amqpadmin.declareExchange(troca);
		
		this.amqpadmin.declareBinding(ligacaoEstoque);
		this.amqpadmin.declareBinding(ligacaoPreco);
	}
}
