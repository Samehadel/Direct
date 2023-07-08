package com.direct.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Configuration
public class LuaScriptsConfig {
	public final static String CHECK_THEN_UPDATE_SCRIPT = "checkThenUpdate.lua";
	public final static String BASE_PATH = "lua";

	@Bean(name = "checkThenUpdate")
	public String checkThenUpdate(){
		try {
			ClassPathResource resource = new ClassPathResource(BASE_PATH + "/" + CHECK_THEN_UPDATE_SCRIPT);
			InputStream inputStream = resource.getInputStream();
			return new Scanner(inputStream, "UTF-8").useDelimiter("\\A").next();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
