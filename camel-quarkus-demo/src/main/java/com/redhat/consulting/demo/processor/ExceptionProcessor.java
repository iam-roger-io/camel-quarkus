package com.redhat.consulting.demo.processor;

import com.redhat.consulting.demo.exception.DataValidationException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.util.json.JsonObject;
import org.jboss.logging.Logger;


/**
 * OrquestradorExceptionProcessor tem o objetivo de centralizar todo o tratamento de exceções lançadas pelas rotas do Orquestrador.
 * Ela deve ser chamada através de uma cláusula onException ou doTry()
 * @author rosantos
 *
 */
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
									
		} else {
			
			exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
			exchange.getIn().setBody(exchange.getProperty(Exchange.EXCEPTION_CAUGHT));
			
		}
	}
	
}
