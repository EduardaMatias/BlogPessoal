package org.generation.blogpessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired /* deixa a classe de outro pacote disponível */
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth/* alias(apelido) */) throws Exception {

		auth.userDetailsService(userDetailsService);

		auth.inMemoryAuthentication().withUser("root").password(passwordEncoder().encode("root"))
				.authorities("ROLE_USER"); /* indica que root root significa um usuario em memoria */
	}

	@Bean /* a instrução funcionará em toda a aplicação */
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests().antMatchers("/usuarios/logar").permitAll().antMatchers(
				"/usuarios/cadastrar").permitAll().antMatchers(HttpMethod.OPTIONS).permitAll().anyRequest()
				.authenticated().and().httpBasic().and().sessionManagement().sessionCreationPolicy(
						SessionCreationPolicy.STATELESS) /*
															 * indica que um token nunca será pra sempre(o usuario será
															 * deslogado e terá que logar novamente)
															 */
				.and().cors().and().csrf().disable();
	}
}
