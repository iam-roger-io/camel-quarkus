package com.redhat.consulting.queue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.consulting.demo.soap.DocumentRequest;
import com.redhat.consulting.demo.soap.ValidatorWebService;
import com.redhat.consulting.demo.soap.ValidatorWebServiceService;
import com.redhat.consulting.generated.soap.model.types.DocumentResponse;
import com.redhat.consulting.model.PessoaModel;
import com.redhat.consulting.model.TipoPessoa;
import com.redhat.consulting.service.PersonRemoteService;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class PersonValidationConsumer {

	private final Logger logger = Logger.getLogger(PersonValidationConsumer.class);

	@Inject
	PersonRemoteService pessoaRemoteService;

	@Incoming("validate-person-in")
	public void receive(JsonObject input) {
		PessoaModel person = input.mapTo(PessoaModel.class);
		if (person.getId() != null && person.getCpf() != null && !person.getCpf().isBlank()) {
			logger.info("Validating Person " + person.getCpf());

			DocumentRequest request = new DocumentRequest();
			request.setCountry("BR");

			if (person.getTipoPessoa().equals(TipoPessoa.FISICA.toString())) {

				request.setType("CPF");

			} else {

				request.setType("CNPJ");

			}

			request.setNumber(person.getCpf());

			ValidatorWebService service = new ValidatorWebServiceService().getValidatorWebServicePort();
			DocumentResponse response = service.validateDocument(request);

			if (!response.isValid()) {
				person.setStatus("rejected");
				person.setStatusMessage(request.getType() + " inválido: " + request.getNumber());
			} else {
				person.setStatus("valid");
			}
			pessoaRemoteService.updatePerson(person);

			logger.info("Person " + person.getCpf() + " isValid: " + response.isValid());
		}

	}
}