package com.redhat.consulting.kafka.processor;

import java.util.concurrent.ExecutionException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.util.json.JsonObject;
import org.jboss.logging.Logger;

import com.redhat.consulting.kafka.exception.DataValidationException;


public class ExceptionProcessor implements Processor {
	
	private static final Logger logger = Logger.getLogger(ExceptionProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		if (exchange.getProperty(Exchange.EXCEPTION_CAUGHT) instanceof DataValidationException) {

			DataValidationException ex = (DataValidationException) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
						
			JsonObject jsonobj = new JsonObject();
			jsonobj.put("mensagem", ex.getMessage());						
			exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
			exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 400);
			exchange.getIn().setBody(jsonobj.toJson());
				
			
		} else if (exchange.getProperty(Exchange.EXCEPTION_CAUGHT) instanceof ExecutionException ) {							
		
			ExecutionException  exception = ((ExecutionException) exchange.getProperty(Exchange.EXCEPTION_CAUGHT));
			exchange.getIn().setBody("");
			if (exception.getMessage().contains("org.apache.kafka.common.errors.SaslAuthenticationException")) {				
				exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 401);
				logger.error("### Erro de autenticação: " + exception.getMessage());
			} else {
				exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
				logger.error("### Erro ao criar produtor kafka: " + exception.getMessage());
			}
			
		} else {
			
			exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
			exchange.getIn().setBody(exchange.getProperty(Exchange.EXCEPTION_CAUGHT));
			logger.error("### Erro executar serviço " + exchange.getProperty(Exchange.EXCEPTION_CAUGHT).toString());
			
		}
	}
	
}
