package com.redhat.consulting.util;

import java.lang.reflect.Type;

import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.reactive.messaging.MessageConverter;
import io.smallrye.reactive.messaging.amqp.IncomingAmqpMetadata;
import io.vertx.core.json.JsonObject;

public class JsonToObjectConverter implements MessageConverter {

	@Override
	public boolean canConvert(Message<?> in, Type target) {
		System.out.println("Received Message " + in);
		System.out.println("Converting to " + target);
		System.out.println("Values " + (JsonObject) in.getPayload());
		return in.getMetadata(IncomingAmqpMetadata.class)

				.map(meta -> meta.getContentType().equals("application/json") && target instanceof Class).orElse(false);

	}

	@Override
	public Message<?> convert(Message<?> in, Type target) {
		System.out.println("Received Message " + in);
		System.out.println("Converting to " + target);
		System.out.println("Values " + (JsonObject) in.getPayload());
		return in.withPayload(((JsonObject) in.getPayload()).mapTo((Class<?>) target));
	}
}