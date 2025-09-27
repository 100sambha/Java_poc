package com.oauth2.config;

import com.oauth2.service.LogoutHandlerService;
import com.oauth2.config.jwtConfig.JwtAccessTokenFilter;
import com.oauth2.config.jwtConfig.JwtRefreshTokenFilter;
import com.oauth2.config.jwtConfig.JwtTokenUtils;
import com.oauth2.config.userConfig.UserInfoManagerConfig;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.oauth2.repo.RefreshTokenRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final UserInfoManagerConfig userInfoManagerConfig;
	private final RSAKeyRecord rsaKeyRecord;
	private final JwtTokenUtils jwtTokenUtils;
	private final RefreshTokenRepo refreshTokenRepo;
	private final LogoutHandlerService logoutHandlerService;

	@Order(1)
	@Bean
	public SecurityFilterChain signinSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher("/sign-in/**").csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.userDetailsService(userInfoManagerConfig)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> {
					ex.authenticationEntryPoint(((request, response, authException) -> response
							.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())));
				}).httpBasic(Customizer.withDefaults()).build();
	}

	@Order(2)
	@Bean
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(AbstractHttpConfigurer::disable).securityMatcher("/api/**")
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtAccessTokenFilter(rsaKeyRecord, jwtTokenUtils),
						UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> {
					log.info("[SecurityConfig:apiSecurityFilterChain] exception due to {}", ex);
					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
				})
//                .userDetailsService(userInfoManagerConfig)
				.httpBasic(Customizer.withDefaults()).build();
	}

	@Order(3)
	@Bean
	public SecurityFilterChain refreshTokenSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher("/refresh-token/**").csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtRefreshTokenFilter(rsaKeyRecord, jwtTokenUtils, refreshTokenRepo),
						UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> {
					log.error("[SecurityConfig:refreshTokenSecurityFilterChain] exception due to :{}", ex);
					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
				}).httpBasic(Customizer.withDefaults()).build();
	}

	@Order(4)
	@Bean
	public SecurityFilterChain signOutSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher("/logout/**").csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtAccessTokenFilter(rsaKeyRecord, jwtTokenUtils),
						UsernamePasswordAuthenticationFilter.class)
				.logout(logout -> logout.logoutUrl("/logout").addLogoutHandler(logoutHandlerService)
						.logoutSuccessHandler(
								((request, response, authentication) -> SecurityContextHolder.clearContext())))
				.exceptionHandling(ex -> {
					log.error("[SecurityConfig:logoutSecurityFilterChain] exception due to {}:", ex);
					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
				}).build();
	}

	@Order(5)
	@Bean
	public SecurityFilterChain signupSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher("/signup/**").csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
	}

	@Order(6)
	@Bean
	public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher("/h2-console").authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
				.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console"))
				.headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable()).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();
	}

	@Bean
	JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(rsaKeyRecord.rsaPublicKey()).privateKey(rsaKeyRecord.rsaPrivateKey()).build();

		JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwkSource);
	}
}
