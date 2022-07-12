package com.redhat.consulting.demo.processor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedMap;

import com.redhat.consulting.demo.config.UploadProcessorConfig;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Singleton
public class UploadProcessor {

	@Inject
	UploadProcessorConfig config;

	public List<String> uploadFile(MultipartFormDataInput input) {
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<String> fileNames = new ArrayList<>();
		List<InputPart> inputParts = uploadForm.get("file");
		String fileName = null;
		for (InputPart inputPart : inputParts) {
			try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);
				fileNames.add(fileName);
				InputStream inputStream = inputPart.getBody(InputStream.class, null);
				writeFile(inputStream, fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fileNames;
	}

	private void writeFile(InputStream inputStream, String fileName) throws IOException {
		byte[] bytes = IOUtils.toByteArray(inputStream);
		File customDir = new File(config.uploadDir());
		fileName = customDir.getAbsolutePath() + File.separator + fileName;

		Path path = Paths.get(fileName);
		File f = path.toFile();
		if (f.exists()) {
			Files.delete(path);
		}
		Files.write(path, bytes, StandardOpenOption.CREATE_NEW);
	}

	private String getFileName(MultivaluedMap<String, String> header) {
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
				String[] name = filename.split("=");
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "";
	}
}
