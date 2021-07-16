package com.acme.service;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.instrumentation.spring.httpclients.RestTemplateInterceptor;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.SdkTracerProviderBuilder;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ServicePollerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServicePollerApplication.class, args);
  }

  @Bean
  public RestTemplate restTemplate(OpenTelemetry openTelemetry) {
    RestTemplate restTemplate = new RestTemplate();

    RestTemplateInterceptor restTemplateInterceptor = new RestTemplateInterceptor(openTelemetry);
    restTemplate.getInterceptors().add(restTemplateInterceptor);

    return restTemplate;
  }

  @Bean
  public OpenTelemetry openTelemetry(ObjectProvider<List<SpanExporter>> spanExportersProvider) {

    SdkTracerProviderBuilder tracerProviderBuilder = SdkTracerProvider.builder();

    spanExportersProvider.getIfAvailable(Collections::emptyList).stream()
        .map(SimpleSpanProcessor::create)
        .forEach(tracerProviderBuilder::addSpanProcessor);

    SdkTracerProvider tracerProvider =
        tracerProviderBuilder
            .setResource(Resource.getDefault().merge(
                Resource.create(Attributes.builder()
                    .put(ResourceAttributes.SERVICE_NAME, "service-poller")
                    .build())
            ))
            .build();

    ContextPropagators propagators = ContextPropagators.create(
        W3CTraceContextPropagator.getInstance()
    );

    return OpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .setPropagators(propagators)
        .buildAndRegisterGlobal();
  }

}
