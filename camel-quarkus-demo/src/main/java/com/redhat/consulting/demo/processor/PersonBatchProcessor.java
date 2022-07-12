package com.redhat.consulting.demo.processor;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Exemplo de processor
 */
@ApplicationScoped
public class PersonBatchProcessor implements Processor {

	private static final Logger logger = LogManager.getLogger(PersonBatchProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println(exchange.getIn().getBody());
//		PessoaModel pessoa = exchange.getIn().getBody(PessoaModel.class);
//
//		emitter.send(pessoa);
//
//		logger.debug("### Queued Person(request):" + pessoa.getCpf() + " for validation ");
	}

}
