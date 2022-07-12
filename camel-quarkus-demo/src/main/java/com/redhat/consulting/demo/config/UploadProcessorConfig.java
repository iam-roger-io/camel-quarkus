package com.redhat.consulting.demo.config;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@StaticInitSafe
@ConfigMapping(prefix = "upload-processor-config")
public interface UploadProcessorConfig {

	public String uploadDir();

	public String batchProcessorUrl();

}
