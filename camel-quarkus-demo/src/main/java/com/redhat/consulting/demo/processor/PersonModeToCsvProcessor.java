package com.redhat.consulting.demo.processor;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.consulting.demo.model.PessoaCsvAdapter;
import com.redhat.consulting.demo.model.PessoaModel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

/*
 * Converts informations from Model to CVS model
 */
@ApplicationScoped
public class PersonModeToCsvProcessor implements Processor {

	private static final Logger logger = LogManager.getLogger(PersonModeToCsvProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		PessoaCsvAdapter adapter = exchange.getProperty("PersonCsv", PessoaCsvAdapter.class);

		PessoaModel pessoa = exchange.getIn().getBody(PessoaModel.class);

		logger.info("Converting Model -> CSV: " + adapter.getCpf());

		BeanUtils.copyProperties(pessoa, adapter);

		exchange.getIn().setBody(adapter);
	}

}
