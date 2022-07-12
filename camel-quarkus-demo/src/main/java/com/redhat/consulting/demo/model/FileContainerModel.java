package com.redhat.consulting.demo.model;

import java.io.InputStream;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileContainerModel {

	@FormParam("file")
	@PartType(MediaType.APPLICATION_OCTET_STREAM)
	public InputStream file;

	@FormParam("fileName")
	@PartType(MediaType.TEXT_PLAIN)
	public String fileName;
}
