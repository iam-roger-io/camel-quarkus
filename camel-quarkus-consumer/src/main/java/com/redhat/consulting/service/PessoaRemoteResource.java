package com.redhat.consulting.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.consulting.model.PessoaModel;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/pessoa")
@RegisterRestClient
public interface PessoaRemoteResource {

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePerson(PessoaModel pessoa);
}
