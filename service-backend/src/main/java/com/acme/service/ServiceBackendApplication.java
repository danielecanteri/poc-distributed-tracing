package com.acme.service;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.instrumentation.spring.autoconfigure.SamplerProperties;
import io.opentelemetry.instrumentation.spring.httpclients.RestTemplateInterceptor;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.sdk.trace.samplers.Sampler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class ServiceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBackendApplication.class, args);
	}

	@Bean
	public OpenTelemetry openTelemetry(
			SamplerProperties samplerProperties,
			ObjectProvider<List<SpanExporter>> spanExportersProvider) {
		SdkTracerProviderBuilder tracerProviderBuilder = SdkTracerProvider.builder();

		spanExportersProvider.getIfAvailable(Collections::emptyList).stream()
				.map(SimpleSpanProcessor::create)
				.forEach(tracerProviderBuilder::addSpanProcessor);

		SdkTracerProvider tracerProvider =
				tracerProviderBuilder
						.setSampler(Sampler.traceIdRatioBased(samplerProperties.getProbability()))
						.setResource(Resource.create(Attributes.of(
								AttributeKey.stringKey("service.name"), "service-backend"
						)))
						.build();
		return OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();
	}

	@Bean
	public RestTemplate restTemplate(OpenTelemetry openTelemetry) {
		RestTemplate restTemplate = new RestTemplate();
		RestTemplateInterceptor restTemplateInterceptor = new RestTemplateInterceptor(openTelemetry);
		restTemplate.getInterceptors().add(restTemplateInterceptor);

		return restTemplate;
	}

}
