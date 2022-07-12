package com.redhat.consulting.demo.routes;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.consulting.demo.model.PessoaCsvAdapter;
import com.redhat.consulting.demo.model.PessoaModel;
import com.redhat.consulting.demo.model.Verification;
import com.redhat.consulting.demo.processor.ExceptionProcessor;
import com.redhat.consulting.demo.processor.PersonCsvToModelProcessor;
import com.redhat.consulting.demo.processor.PersonModeToCsvProcessor;
import com.redhat.consulting.demo.processor.PersonValidationEnqueueProcessor;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.BindyType;
import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.jboss.logging.Logger;

/**
 * Esta classe possui as rotas que implementam as regras dos serviços expostos.
 */
@ApplicationScoped
public class PessoaRouteServices extends RouteBuilder {

	private static final Logger logger = Logger.getLogger(PessoaRouteServices.class);

	@Inject
	PersonCsvToModelProcessor personValidationCsvEnqueueProcessor;

	@Inject
	PersonValidationEnqueueProcessor personValidationEnqueueProcessor;

	@Inject
	PersonModeToCsvProcessor personModeToCsvProcessor;

	@Override
	public void configure() throws Exception {

		onException(Exception.class).handled(true).process(new ExceptionProcessor()); // Exemplo de execução de
																						// processor sem injeção de
																						// dependencia.

		/*
		 * Consultar pessoa pelo ID Exemplo com jpq query
		 */
		from("direct:get-pessoa-by-id").routeId("route-get-pessoa-by-id")
				.to("microprofile-metrics:counter:camel_demo_quarkus_count_get_pessoa_by_id?counterIncrement=1")
				.toD("jpa://com.redhat.consulting.demo.entity.PessoaModel?query=select a from PessoaModel a where id = ${header.pessoaId}");

		/*
		 * Obter lista de pessoas Exemplo com named query
		 */
		from("direct:get-all-pessoas").id("route-get-all-pessoas")
				.to("microprofile-metrics:counter:camel_demo_quarkus_count_get_all_pessoas?counterIncrement=1")
				.to("jpa://com.redhat.consulting.demo.entity.PessoaModel?namedQuery=findAll");

		/*
		 * Remove uma pessoa Exemplo com query nativa
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

				.to("direct:verificar-pessoa") // Passa o fluxo para uma rota que irá chamar um serviço REST

				.to("jpa://com.redhat.consulting.demo.entity.Pessoa")

				.process(personValidationEnqueueProcessor) // Este processor adicionara o usuario cadastrado a fila para
				// validacao no servico SOAP.

				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

		from("direct:processar-pessoa-batch").routeId("route-processar-pessoa-batch")
				.log("Reading Persons CSV data from ${header.CamelFileName}")
				// Consume person CSV files
				.pollEnrich(
						"file:{{upload-processor-config.upload-dir}}?fileName=${header.fileName}&noop=true&idempotent=false")
//				.from("file:{{upload-processor-config.upload-dir}}/${header.fileName}")
				.log("Processing file  ${body}").unmarshal().bindy(BindyType.Csv, PessoaCsvAdapter.class).split(body())
				.log("Processing  ${body.cpf}").setProperty("PersonCsv", simple("${body}"))
				.process(personValidationCsvEnqueueProcessor)

				.to("direct:verificar-pessoa") // Passa o fluxo para uma rota que irá chamar um serviço REST

				.to("jpa://com.redhat.consulting.demo.entity.Pessoa").setProperty("pessoaModel", simple("${body}"))

				.process(personValidationEnqueueProcessor).process(personModeToCsvProcessor)

				.aggregate(simple("${body.status}"), new GroupedBodyAggregationStrategy()).completionInterval(5000)
				.log("Processed ${header.CamelAggregatedSize} persons for status '${header.PersonStatus}'")
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

		from("direct:criar-pessoa-batch").routeId("route-criar-pessoa-batch")

				.to("microprofile-metrics:counter:camel_demo_quarkus_count_criar_pessoa?counterIncrement=1")

				.process(personValidationEnqueueProcessor) // Este processor adicionara o usuario cadastrado a fila para
															// validacao no servico SOAP.

				.to("direct:verificar-pessoa") // Passa o fluxo para uma rota que irá chamar um serviço REST

				.to("jpa://com.redhat.consulting.demo.entity.Pessoa")

				.to("direct:aggregatePersons");

		// Aggregate books based on their genre
		from("direct:aggregatePersons").setHeader("PersonStatus", simple("${body.status}"))
				.aggregate(simple("${body.status}"), new GroupedBodyAggregationStrategy()).completionInterval(5000)
				.log("Processed ${header.CamelAggregatedSize} persons for status '${header.PersonStatus}'")
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

		/*
		 * Rota para invocar um serviço rest e completar os dados do objeto PessoaModel
		 */
		from("direct:verificar-pessoa").routeId("route-verificar-pessoa").setHeader("pessoaModel", simple("${body}"))
				.setProperty("pessoaModel", simple("${body}"))

				.marshal().json() // Converto o objeto Java PessoaModel em um JSON
				.log(LoggingLevel.DEBUG, "### Marshal PessoaModel para JSON").removeHeader("CamelHttpPath")
				.log(LoggingLevel.DEBUG, "### Removendo Header CamelHttpPath")

				.to("{{verify.person.url}}?bridgeEndpoint=true&?httpMethod=POST") // Faz uma chamada REST
				.log(LoggingLevel.DEBUG,
						"### Chamando serviço REST {{verify.person.url}}?bridgeEndpoint=true&?httpMethod=POST")
				.setHeader("CamelHttpPath").constant("/pessoa")

				.unmarshal(new JacksonDataFormat(Verification.class)).process(exchange -> { // Exemplo de processor
																							// recomendado para
																							// processamentos curtos e
																							// simples.
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