package com.redhat.consulting.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.consulting.config.PersonRemoteServiceProperties;
import com.redhat.consulting.model.PessoaModel;

@ApplicationScoped
public class PersonRemoteService {
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private Client client;
	private String baseUrl;// = "http://localhost:8080";

	@Inject
	public PersonRemoteService(PersonRemoteServiceProperties personRemoteServiceProperties) {
		baseUrl = personRemoteServiceProperties.url();
		client = ClientBuilder.newBuilder().executorService(executorService).build();
	}

	public Response updatePerson(PessoaModel person) {
		return client.target(baseUrl + "/pessoa").request().header("Content-Type", MediaType.APPLICATION_JSON)
				.put(Entity.json(person));
	}
}
