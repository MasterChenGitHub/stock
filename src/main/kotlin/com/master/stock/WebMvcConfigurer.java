package com.master.stock;

import com.google.gson.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
			@Override
			public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
				if (src == src.longValue()) {
					return new JsonPrimitive(src.longValue());
				} else {
					return new JsonPrimitive(src);
				}
			}
		}).registerTypeAdapter(Long.class, new JsonSerializer<Long>() {

			@Override
			public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
				if (src > 9007199254740991L || src < -9007199254740991L) {
					return new JsonPrimitive(src.toString());
				} else {
					return new JsonPrimitive(src);
				}
			}
		}).serializeNulls();
		Gson gson = builder.create();

		GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
		converter.setGson(gson);
		converter.setDefaultCharset(Charset.forName("utf-8"));
		List<MediaType> mediaTypesList = new ArrayList<>();
		mediaTypesList.add(MediaType.APPLICATION_JSON);
		mediaTypesList.add(MediaType.APPLICATION_ATOM_XML);
		mediaTypesList.add(MediaType.APPLICATION_FORM_URLENCODED);
		mediaTypesList.add(MediaType.APPLICATION_OCTET_STREAM);
		mediaTypesList.add(MediaType.APPLICATION_PDF);
		mediaTypesList.add(MediaType.APPLICATION_RSS_XML);
		mediaTypesList.add(MediaType.APPLICATION_XHTML_XML);
		mediaTypesList.add(MediaType.APPLICATION_XML);
		mediaTypesList.add(MediaType.IMAGE_GIF);
		mediaTypesList.add(MediaType.IMAGE_JPEG);
		mediaTypesList.add(MediaType.IMAGE_PNG);
		mediaTypesList.add(MediaType.TEXT_EVENT_STREAM);
		mediaTypesList.add(MediaType.TEXT_HTML);
		mediaTypesList.add(MediaType.TEXT_MARKDOWN);
		mediaTypesList.add(MediaType.TEXT_PLAIN);
		mediaTypesList.add(MediaType.TEXT_XML);
		converter.setSupportedMediaTypes(mediaTypesList);
		converters.add(0, converter);
	}

	/**
	 * 跨域访问
	 * 
	 * @return
	 */
	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**");
	}

	/*
	 * (non-Javadoc) 静态资源拦截
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#
	 * addResourceHandlers(org.springframework.web.servlet.config.annotation.
	 * ResourceHandlerRegistry)
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}



	/**
	 * 自定义配置
	 * 
	 * @return
	 */
	@Bean(name = "conf")
	@ConfigurationProperties("conf")
	public Properties getConfProperties() {
		return new Properties();
	}

	@Bean(name = "restTemplate")
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}
}
