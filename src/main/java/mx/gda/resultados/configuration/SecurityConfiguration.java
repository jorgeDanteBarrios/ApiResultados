package mx.gda.resultados.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*	 add {noop} when dont use PasswordEncoder Ej  password("{noop}admin123")*/
		auth.inMemoryAuthentication()
		.withUser("admin").password(passwordEncoder().encode("n57KrFBwt")).roles("ADMIN")  //admin
		.and()
		.withUser("apiResultadosUser").password(passwordEncoder().encode("P6YkATQ")).roles("CONSULTA")  // for external
		;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* .antMatchers(HttpMethod.GET, "/ApiLogin/**").hasRole("ADMIN") */
		http
		 .cors().and()
		 .httpBasic()
         .and()
         .authorizeRequests()
         .antMatchers("/ApiResultados/**").authenticated()
         //.antMatchers(HttpMethod.GET, "/ApiResultados/**").hasAnyRole("ADMIN","CONSULTA")
         //.antMatchers(HttpMethod.POST, "/ApiResultados/orden").authenticated()
         .and()
         .csrf().disable()
         .formLogin().disable();
							
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	  @Bean
	  public CorsFilter corsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(Boolean.valueOf(true));
	    config.addAllowedOrigin("*");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter((CorsConfigurationSource)source);
	  }

}
