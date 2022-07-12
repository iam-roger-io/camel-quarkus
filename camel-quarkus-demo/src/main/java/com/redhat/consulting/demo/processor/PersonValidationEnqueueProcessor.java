package com.redhat.consulting.demo.processor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.consulting.demo.model.PessoaModel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

/*
 * Exemplo de processor
 */
@ApplicationScoped
public class PersonValidationEnqueueProcessor implements Processor {

	private static final Logger logger = LogManager.getLogger(PersonValidationEnqueueProcessor.class);

	@Inject
	@Channel("validate-person-out")
	Emitter<PessoaModel> emitter;

	@Override
	public void process(Exchange exchange) throws Exception {

		PessoaModel pessoa = exchange.getIn().getBody(PessoaModel.class);

		emitter.send(pessoa);

		logger.debug("### Queued Person(request):" + pessoa.getCpf() + " for validation ");
		exchange.getIn().setBody(pessoa);
	}

}
