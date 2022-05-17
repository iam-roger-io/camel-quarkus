package com.redhat.consulting.demo.processor;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.consulting.demo.exception.DataValidationException;
import com.redhat.consulting.demo.model.PessoaModel;
import com.redhat.consulting.demo.model.TipoPessoa;
import com.redhat.consulting.demo.soap.DocumentRequest;
import com.redhat.consulting.demo.soap.ValidatorWebService;
import com.redhat.consulting.demo.soap.ValidatorWebServiceService;
import com.redhat.consulting.generated.soap.model.types.DocumentResponse;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Exemplo de processor
 */
@ApplicationScoped
public class ValidarPessoaProcessor implements Processor {

	private static final Logger logger = LogManager.getLogger(ValidarPessoaProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		PessoaModel pessoa = exchange.getIn().getBody(PessoaModel.class);
		
		DocumentRequest request = new DocumentRequest();		
		request.setCountry("BR");
		
		
		if (pessoa.getTipoPessoa().equals(TipoPessoa.FISICA)) {
			
			request.setType("CPF");
		
		} else {
			
			request.setType("CNPJ");			
			
		}
		
		request.setNumber(pessoa.getCpf());
		
		ValidatorWebService service =  new ValidatorWebServiceService().getValidatorWebServicePort();
		DocumentResponse response = service.validateDocument(request);
		
		if (!response.isValid()) {
			
			throw new DataValidationException("Documento inválido: " + request.getNumber());
			
		}
				
		logger.debug("### Resultado de service.validateDocument(request):" + response.isValid() + " messagem: " + response.getMessage());
		
		
	}

}
