package com.redhat.consulting.demo.soap;

import javax.jws.WebService;

import com.redhat.consulting.demo.model.DocumentRequest;
import com.redhat.consulting.demo.model.DocumentResponse;
import com.redhat.consulting.demo.wrapper.DocumentValidator;
/**
 * 
 * @author rosantos
 *
 */

@WebService(endpointInterface = "com.redhat.consulting.demo.soap.ValidatorWebService")
public class ValidatorWebServiceImpl implements ValidatorWebService {

	@Override
	public DocumentResponse validateDocument(DocumentRequest request) {

		DocumentResponse response = new DocumentResponse();
		response.setValid(true);
		
		switch (request.getCountry()) {
		
		case "BR":
			
			
			response = validateDocumentBR(request, response);
			
			break;

		default:

			response.setValid(false);
			response.setMessage("Invalid Country");
			
			break;
		}
		
		
		return response;
		
	}

	private DocumentResponse validateDocumentBR(DocumentRequest request, DocumentResponse response) {
		
		if (request.getType().equalsIgnoreCase("CPF")) {
			
			if (!DocumentValidator.isCPF(request.getNumber())) {
				
				response.setValid(false);
				response.setMessage("CPF is invalid");
				
			}
			
		} else if (request.getType().equalsIgnoreCase("CNPJ")) {

			if (!DocumentValidator.isCNPJ(request.getNumber())) {
				response.setValid(false);
				response.setMessage("CNPJ is invalid");
			}
			
		} else {
			response.setValid(false);
			response.setMessage("Document type is invalid");
		}
		
		return response;
	}
	
}