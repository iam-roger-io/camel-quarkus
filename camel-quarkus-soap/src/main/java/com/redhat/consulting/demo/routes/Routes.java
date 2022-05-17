package com.redhat.consulting.demo.routes;

import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.consulting.demo.model.rest.Verification;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 
 * @author rosantos
 *
 */
@ApplicationScoped
public class Routes extends RouteBuilder {

	private static final Logger logger = LogManager.getLogger(Routes.class);

	@Override
	public void configure() throws Exception {

        restConfiguration()
        .bindingMode(RestBindingMode.auto)
        .enableCORS(true)
        .corsHeaderProperty("Access-Control-Allow-Origin", "*")
        .corsHeaderProperty("Access-Control-Allow-Credentials", "*")
		.corsHeaderProperty("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT, DELETE")
        .corsHeaderProperty("Access-Control-Request-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, api_key, authorization, Referer, User-Agent")
        .corsHeaderProperty("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization, Referer, User-Agent");
		
        rest("/verify")        	
		.post("/person")
		.id("rest-verify-person")
		.consumes("application/json")
		.produces("application/json")
		.outType(Verification.class)
        .bindingMode(RestBindingMode.auto)        
        .to("direct:post-verify-person") ;
		 
        from("direct:post-verify-person").routeId("route-verify-person").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
			
				UUID uuid = UUID.randomUUID();
				Verification verification = new Verification();
				verification.setVerificationCode(uuid.toString());
				verification.setVerificationDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()));
						
			    exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
				exchange.getIn().setBody(verification);
			}
		});
        
        
	}
	
}