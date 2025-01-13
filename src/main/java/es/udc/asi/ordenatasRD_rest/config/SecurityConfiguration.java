package es.udc.asi.ordenatasRD_rest.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import es.udc.asi.ordenatasRD_rest.model.domain.UserAuthority;
import es.udc.asi.ordenatasRD_rest.security.JWTConfigurer;
import es.udc.asi.ordenatasRD_rest.security.MyAccessDeniedHandler;
import es.udc.asi.ordenatasRD_rest.security.MyUnauthorizedEntryPoint;
import es.udc.asi.ordenatasRD_rest.security.MyUserDetailsService;
import es.udc.asi.ordenatasRD_rest.security.TokenProvider;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

  @Autowired
  private Properties properties;

  @Autowired
  private MyUnauthorizedEntryPoint myUnauthorizedEntryPoint;

  @Autowired
  private MyAccessDeniedHandler myAccessDeniedHandler;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private MyUserDetailsService myUserDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .csrf((csrf) -> csrf.disable())
      .exceptionHandling((exceptionHandling) -> exceptionHandling
        .authenticationEntryPoint(myUnauthorizedEntryPoint)
        .accessDeniedHandler(myAccessDeniedHandler)
      )
      .headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.disable()))
      .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests((authorize) -> authorize
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAuthority(UserAuthority.ADMIN.name())
        .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAuthority(UserAuthority.ADMIN.name())
        .requestMatchers(HttpMethod.DELETE, "/api/users/{id}/imagen").permitAll()
        .requestMatchers("/**").authenticated())
      .with(securityConfigurerAdapter(), Customizer.withDefaults());
    // @formatter:on
    return http.build();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins(properties.getClientHost());
      }
    };
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Autowired
  public void configureAuth(AuthenticationManagerBuilder auth) {
    try {
      auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
    } catch (Exception e) {
      throw new BeanInitializationException("SecurityConfiguration.configureAuth failed", e);
    }
  }

  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(tokenProvider);
  }
}
