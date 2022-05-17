package com.redhat.consulting.demo.routes;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.consulting.demo.model.PessoaModel;
import com.redhat.consulting.demo.model.Verification;
import com.redhat.consulting.demo.processor.ExceptionProcessor;
import com.redhat.consulting.demo.processor.ValidarPessoaProcessor;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.jboss.logging.Logger;
/**
 *  Esta classe possui as rotas que implementam as regras dos serviços expostos.
 */
@ApplicationScoped
public class PessoaRouteServices extends RouteBuilder {

	private static final Logger logger = Logger.getLogger(PessoaRouteServices.class);
			
	@Inject
	private ValidarPessoaProcessor validarPessoaProcessor;
	
	@Override
	public void configure() throws Exception {

        onException(Exception.class)
    	.handled(true)
    	.process(new ExceptionProcessor()); // Exemplo de execução de processor sem injeção de dependencia.
		 					
		/*
		 * Consultar pessoa pelo ID
		 * Exemplo com jpq query
		 */
        from("direct:get-pessoa-by-id").routeId("route-get-pessoa-by-id")        
        .to("microprofile-metrics:counter:camel_demo_quarkus_count_get_pessoa_by_id?counterIncrement=1")
        .toD("jpa://com.redhat.consulting.demo.entity.PessoaModel?query=select a from PessoaModel a where id = ${header.pessoaId}");
                
        /*
         * Obter lista de pessoas
         * Exemplo com named query
         */
		from("direct:get-all-pessoas").id("route-get-all-pessoas")		
		.to("microprofile-metrics:counter:camel_demo_quarkus_count_get_all_pessoas?counterIncrement=1")
		.to("jpa://com.redhat.consulting.demo.entity.PessoaModel?namedQuery=findAll");	
                
        /*
         * Remove uma pessoa
         * Exemplo com query nativa
         */
        from("direct:delete-pessoa").routeId("route-delete-pessoa")
        .to("microprofile-metrics:counter:camel_demo_quarkus_count_delete_pessoa?counterIncrement=1")
        .toD("jpa://com.redhat.consulting.demo.entity.PessoaModel?nativeQuery=delete from tb_pessoa  where pessoaID = ${header.pessoaId}")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
        
        /*
         * Insert nova pessoa na base de dados.
         */
        from("direct:criar-pessoa").routeId("route-criar-pessoa")        
        
        .to("microprofile-metrics:counter:camel_demo_quarkus_count_criar_pessoa?counterIncrement=1")
        
        .process(validarPessoaProcessor) //Este processor injetado (CDI) irá chamar um serviço SOAP.
        
        .to("direct:verificar-pessoa") // Passa o fluxo para uma rota que irá chamar um serviço REST  		
        
        .to("jpa://com.redhat.consulting.demo.entity.Pessoa")   		
        
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
    	
        .setBody(header(Exchange.HTTP_RESPONSE_TEXT));                
        
    	/*
		 * Rota para invocar um serviço rest e completar os dados do objeto PessoaModel
		 */
		from("direct:verificar-pessoa").routeId("route-verificar-pessoa")
		.setHeader("pessoaModel", simple("${body}"))
		.setProperty("pessoaModel", simple("${body}"))
		
		.marshal().json() // Converto o objeto Java PessoaModel em um JSON
			.log(LoggingLevel.DEBUG, "### Marshal PessoaModel para JSON")		
		.removeHeader("CamelHttpPath")
			.log(LoggingLevel.DEBUG, "### Removendo Header CamelHttpPath")			
			
		.to("{{verify.person.url}}?bridgeEndpoint=true&?httpMethod=POST") // Faz uma chamada REST
			.log(LoggingLevel.DEBUG, "### Chamando serviço REST {{verify.person.url}}?bridgeEndpoint=true&?httpMethod=POST")					
			.setHeader("CamelHttpPath").constant("/pessoa")
		
		.unmarshal(new JacksonDataFormat(Verification.class))
		.process(exchange -> { // Exemplo de processor recomendado para processamentos curtos e simples.
        	PessoaModel pessoa = exchange.getProperty("pessoaModel", PessoaModel.class);
        	Verification verified = exchange.getIn().getBody(Verification.class);
        	if (null != pessoa) {
	        	
        		pessoa.setVerificationCode(verified.getVerificationCode());
	        	pessoa.setVerificationDate(verified.getVerificationDate());
	        	
	        	logger.info("### Incluindo informações sobre a verificação");
	        	
        	} 
        	exchange.getIn().setBody(pessoa);
        });		        
        
	}
	
}