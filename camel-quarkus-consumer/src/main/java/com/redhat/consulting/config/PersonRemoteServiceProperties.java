package com.redhat.consulting.config;

import io.quarkus.runtime.annotations.StaticInitSafe;
import io.smallrye.config.ConfigMapping;

@StaticInitSafe
@ConfigMapping(prefix = "person-remote-service")
public interface PersonRemoteServiceProperties {

	public String url();

}
