package com.redhat.consulting.demo.processor;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.consulting.demo.model.PessoaCsvAdapter;
import com.redhat.consulting.demo.model.PessoaModel;
import com.redhat.consulting.demo.model.TipoPessoa;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

/*
 * Converts from CSV to Model
 */
@ApplicationScoped
public class PersonCsvToModelProcessor implements Processor {

	private static final Logger logger = LogManager.getLogger(PersonCsvToModelProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		PessoaCsvAdapter adapter = exchange.getIn().getBody(PessoaCsvAdapter.class);

		logger.info("Converting CSV -> Model: " + adapter.getCpf());

		PessoaModel pessoa = new PessoaModel();

		BeanUtils.copyProperties(adapter, pessoa);

		try {
			if (adapter.getId() <= 0) {
				pessoa.setId(0);
			}
			if (TipoPessoa.JURIDICA.toString().equals(adapter.getTipoPessoa())) {
				pessoa.setTipoPessoa(TipoPessoa.JURIDICA);
			} else {
				pessoa.setTipoPessoa(TipoPessoa.FISICA);
			}
			pessoa.setStatus("in_review");
		} catch (Exception e) {
			pessoa.setId(0);
			pessoa.setTipoPessoa(TipoPessoa.FISICA);
			pessoa.setStatus("in_review");
		}

//		

		exchange.getIn().setBody(pessoa);
	}

}
