package com.redhat.consulting.demo.routes;

import javax.ws.rs.core.MediaType;

import com.redhat.consulting.demo.model.PessoaModel;
import com.redhat.consulting.demo.processor.ExceptionProcessor;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.jboss.logging.Logger;

/**
 * Esta classe define os endpoints rest que são expostos pela aplicação.
 */
public class PessoaRouteResources extends RouteBuilder {

	private static final Logger logger = Logger.getLogger(PessoaRouteResources.class);

	@Override
	public void configure() throws Exception {
		onException(Exception.class).handled(true).process(new ExceptionProcessor());

		restConfiguration().bindingMode(RestBindingMode.json).apiContextPath("/api-doc/swagger.json")
				.apiContextRouteId("rest-documentation").apiProperty("api.title", "API Pessoa ")
				.apiProperty("api.version", "1.0.0")
				.apiProperty("api.description", "Aplicação de Exemplo com Camel Quarkus Demo").enableCORS(true)
				.corsHeaderProperty("Access-Control-Allow-Origin", "*")
				.corsHeaderProperty("Access-Control-Allow-Credentials", "*")
				.corsHeaderProperty("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT, DELETE")
				.corsHeaderProperty("Access-Control-Request-Headers",
						"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, api_key, authorization, Referer, User-Agent")
				.corsHeaderProperty("Access-Control-Allow-Headers",
						"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization, Referer, User-Agent");

		rest("/pessoa").get("/{pessoaId}").id("rest-get-pessoa-by-id").apiDocs(true)
				.description("Este serviço retorna os dados de uma pessoa baseado no ID")
				.produces(MediaType.APPLICATION_JSON).outType(PessoaModel.class).bindingMode(RestBindingMode.json)
				.param().name("pessoaId").type(RestParamType.path).description("Id utilizado para recuperar uma pessoa")
				.dataType("string").endParam().responseMessage().code(200).endResponseMessage().responseMessage()
				.code(500).message("Sistema indisponível").endResponseMessage().to("direct:get-pessoa-by-id")

				.get().produces(MediaType.APPLICATION_JSON).bindingMode(RestBindingMode.json)
				.to("direct:get-all-pessoas").responseMessage().code(200).endResponseMessage().responseMessage()
				.code(500).message("Sistema indisponível").endResponseMessage()

				.delete("/{pessoaId}").produces(MediaType.APPLICATION_JSON).bindingMode(RestBindingMode.json)
				.to("direct:delete-pessoa")

				.post().produces(MediaType.APPLICATION_JSON).consumes(MediaType.APPLICATION_JSON)
				.type(PessoaModel.class).bindingMode(RestBindingMode.json).to("direct:criar-pessoa").responseMessage()
				.code(200).message("criado").endResponseMessage().responseMessage().code(400)
				.message("Parâmetro inválido na request").endResponseMessage().responseMessage().code(500)
				.message("Sistema indisponível").endResponseMessage()

				.post("/processar_batch/{fileName}").produces(MediaType.APPLICATION_JSON)
				.consumes(MediaType.APPLICATION_JSON).bindingMode(RestBindingMode.json)
				.to("direct:processar-pessoa-batch").responseMessage().code(200).endResponseMessage().responseMessage()
				.code(400).message("Parâmetro inválido na request").endResponseMessage().responseMessage().code(500)
				.message("Sistema indisponível").endResponseMessage()

				.put().produces(MediaType.APPLICATION_JSON).consumes(MediaType.APPLICATION_JSON).type(PessoaModel.class)
				.bindingMode(RestBindingMode.json).to("direct:criar-pessoa").responseMessage().code(201)
				.message("criado").endResponseMessage().responseMessage().code(500).message("Sistema indisponível")
				.endResponseMessage();

	}

}