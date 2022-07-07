package com.redhat.consulting.queue;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.consulting.demo.soap.DocumentRequest;
import com.redhat.consulting.demo.soap.ValidatorWebService;
import com.redhat.consulting.demo.soap.ValidatorWebServiceService;
import com.redhat.consulting.generated.soap.model.types.DocumentResponse;
import com.redhat.consulting.model.PessoaModel;
import com.redhat.consulting.model.TipoPessoa;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class PersonValidationConsumer {

	private final Logger logger = Logger.getLogger(PersonValidationConsumer.class);

	@Incoming("validate-person-in")
	public void receive(JsonObject input) {
		PessoaModel pessoa = input.mapTo(PessoaModel.class);
		logger.info("Validating Person " + pessoa.getCpf());

		DocumentRequest request = new DocumentRequest();
		request.setCountry("BR");

		if (pessoa.getTipoPessoa().equals(TipoPessoa.FISICA.toString())) {

			request.setType("CPF");

		} else {

			request.setType("CNPJ");

		}

		request.setNumber(pessoa.getCpf());

		ValidatorWebService service = new ValidatorWebServiceService().getValidatorWebServicePort();
		DocumentResponse response = service.validateDocument(request);

		if (!response.isValid()) {
			pessoa.setStatus("rejected");
			pessoa.setStatusMessage("Documento inválido: " + request.getNumber());
		} else {
			pessoa.setStatus("valid");
		}
		logger.info("Person " + pessoa.getCpf() + " isValid: " + response.isValid());

	}
}