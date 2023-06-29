package com.direct.app.security;

import com.direct.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	private UserService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	private static final String[] AUTH_WHITELIST = {
			"/h2",
			"/h2/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/signin",
			"/users/test",
			"/users/find/**",
            SecurityConstants.SIGN_UP_URL
    };
	
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
				.and().authorizeRequests().anyRequest().authenticated()
				.and()
				.addFilter(new AuthenticationFilter(authenticationManager()))
				.addFilter(new AuthorizationFilter(authenticationManager()));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authManager) throws Exception {
		authManager.userDetailsService(userDetailsService).passwordEncoder(encoder);

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowCredentials(true)
				.allowedHeaders("X-Requested-With",
						"Origin",
						"Content-Type",
						"Accept",
						"Access-Control-Allow-Origin",
						"Access-Control-Allow-Headers")
				.allowedOrigins("http://localhost:3000")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.exposedHeaders("Access-Control-Allow-Headers", "Authorization", "virtualUserId", "Message",
						"Access-Control-Allow-Headers",
						"Origin", "Accept, X-Requested-With",
						"Content-Type", "Access-Control-Request-Method",
						"Access-Control-Request-Headers");
			}
		};
	}
}
