package com.direct.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfiguration {

	@Value("${files.basepath}")
	@Getter
	@Setter
	private String profileImagesBasePath;
}
