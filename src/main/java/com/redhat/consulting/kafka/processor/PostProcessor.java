package com.redhat.consulting.kafka.processor;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaEndpoint;
import org.apache.camel.component.kafka.KafkaProducer;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.function.ThrowingSupplier;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jboss.logging.Logger;


@Singleton
public class PostProcessor implements Processor {
	
	private static final Logger logger = Logger.getLogger(PostProcessor.class);
	private String username = null;
	private String password = null;
    
	@Override
	public void process(Exchange exchange) throws Exception {

		
		String messageKey = exchange.getIn().getHeader("messageKey", String.class);
		String json = exchange.getIn().getBody(String.class);
		
		getCredentials(exchange);
		
		logger.info("### Mensagem recebida pelo usuário " + this.username);		
        logger.debug("### Mensagem recebida pelo usuário " + this.username + " :: messageKey: " + messageKey);
        logger.debug("### Mensagem recebida pelo usuário " + this.username + " :: body: " + json);
		
        exchange.getIn().setHeader("ProxyRestKafkaUsernName", this.username);
        exchange.getIn().setHeader("ProxyRestKafkaPassword", this.password);
        exchange.getIn().setBody(json);
        
        Properties kafkaProps = new Properties();
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "amqstreams-kafka-external-bootstrap-telefonica-amq-streams-dev.apps-crc.testing:443");
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProps.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.scram.ScramLoginModule required username='"+ this.username + "' password='Umq4mOgaAU4WIGTja3dCcO6RydjT1Tcr';");
        
   		kafkaProps.put("ssl.truststore.location", "/tmp/kafka/truststore.jks");
     	kafkaProps.put("ssl.truststore.password","password");
     	kafkaProps.put("security.protocol","SASL_SSL");
        kafkaProps.put("sasl.mechanism","SCRAM-SHA-512");
              
        CamelContext context = new DefaultCamelContext();
        KafkaComponent component = new KafkaComponent(context);
        KafkaProducer camelProducer = new KafkaProducer((KafkaEndpoint) component.createEndpoint("kafka:haac-fault"));     
       
        try {
        	camelProducer.setKafkaProducer(new org.apache.kafka.clients.producer.KafkaProducer<String,String>(kafkaProps));
        	camelProducer.process(exchange); exchange.getIn().getBody(String.class);
        	        	
        } catch (Exception e) {
        	
        	camelProducer.getKafkaProducer().close();
        	
        	throw e;

        } finally {
        	camelProducer.close();
        	component.close();        	
        }        
        
        
	}

	private void getCredentials(Exchange exchange) throws UnsupportedEncodingException {
		
        String authorizationHeader = exchange.getIn().getHeader("Authorization", String.class);
        String[] splitCredentials = null; 
       
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes, "UTF-8");
            splitCredentials = credentials.split(":", 2);
            this.username = splitCredentials[0] ;
            this.password = splitCredentials[1];
            
            logger.debug("## Basic Authentication: username: " + this.username + " password: " + this.password);
           
        }
	}
	
}

