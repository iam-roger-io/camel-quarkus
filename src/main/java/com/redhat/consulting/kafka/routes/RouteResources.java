package com.redhat.consulting.kafka.routes;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.apache.camel.component.kafka.KafkaProducer;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.jboss.logging.Logger;

import com.redhat.consulting.kafka.processor.ExceptionProcessor;
import com.redhat.consulting.kafka.processor.PostProcessor;

import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * Esta classe define os endpoints rest que são expostos pela aplicação.
 */
public class RouteResources extends RouteBuilder {

	private KubernetesClient kubernetesClient;
	private String user;
	
	private static final Logger logger = Logger.getLogger(RouteResources.class);
		
	@Override
	public void configure() throws Exception {
      onException(Exception.class)    
        .maximumRedeliveries(2)
    	.process(new ExceptionProcessor())
    	.handled(true).end();
        
        restConfiguration()
        .bindingMode(RestBindingMode.off)
		.apiContextPath("/api-doc/swagger.json")
        .apiProperty("api.title", "API Pessoa ")
        .apiProperty("api.version", "1.0.0")
        .apiProperty("api.description", "Aplicação de Exemplo com Camel Quarkus Demo")
        .enableCORS(true)
        .corsHeaderProperty("Access-Control-Allow-Origin", "*")
        .corsHeaderProperty("Access-Control-Allow-Credentials", "*")
		.corsHeaderProperty("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT, DELETE")
        .corsHeaderProperty("Access-Control-Request-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, api_key, authorization, Referer, User-Agent")
        .corsHeaderProperty("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization, Referer, User-Agent");
        
        rest("/amqstreams/producer/{topicName}")                 
              .post()
            	.produces("text/plain")
            	.consumes("application/json")
		        .bindingMode(RestBindingMode.off)		        
		        .to("direct:kafka-producer")		        
		        .responseMessage().code(201).message("Enviado").endResponseMessage()
		        .responseMessage().code(401).message("401 Unauthorized").endResponseMessage()
		        .responseMessage().code(500).message("Sistema indisponível").endResponseMessage();

        from("direct:kafka-producer").routeId("route-kafka-producer")
        .log(LoggingLevel.INFO, "Mensagem recebida do usuário")
        .process(new PostProcessor())
       // .toD("kafka:haac-fault?saslJaasConfig=org.apache.kafka.common.security.scram.ScramLoginModule required username='${header.ProxyRestKafkaUsernName}' password='Umq4mOgaAU4WIGTja3dCcO6RydjT1Tcr';")
       	.log("Mensagem enviada ao AMQ Streams com sucesso pelo usuario: ${header.ProxyRestKafkaUsernName}")         
       	.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));

        
                
        
   
        
	}

}