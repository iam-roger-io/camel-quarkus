package com.redhat.consulting.demo.routes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.consulting.demo.config.UploadProcessorConfig;
import com.redhat.consulting.demo.model.PessoaModel;
import com.redhat.consulting.demo.processor.UploadProcessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/batch")
public class PersonBatchRoute {

	private static final Logger logger = LogManager.getLogger(PersonBatchRoute.class);

	@Inject
	UploadProcessor uploadProcessor;

	@Inject
	UploadProcessorConfig uploadProcessorConfig;

	private ExecutorService executorService = Executors.newCachedThreadPool();

	@POST()
	@Path("/person")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Object echo(@MultipartForm MultipartFormDataInput input) throws Exception {
		List<String> files = uploadProcessor.uploadFile(input);
		Client client = ClientBuilder.newBuilder().executorService(executorService).build();
		Map<String, Object> result = new HashMap<>();
		if (files != null) {
			for (String fileName : files) {
				Map<String, Object> params = new HashMap<>();
				params.put("fileName", fileName);
				PessoaModel entity = new PessoaModel();
				entity.setNome("batch-person");
				Response response = client
						.target(uploadProcessorConfig.batchProcessorUrl() + "/processar_batch/" + fileName).request()
						.header("Content-Type", MediaType.APPLICATION_JSON).post(Entity.json(entity));
				result.put(fileName, Entity.json(response.getEntity()));
				logger.info("Response for " + fileName + ": " + response.readEntity(String.class));

			}
		}
		return result;
	}
}
