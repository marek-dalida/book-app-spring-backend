package com.books.zpt3.auth;


import com.books.zpt3.sevices.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(this.userDetailsService());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/dashboard").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/dashboard/{id}").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/dashboard/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/dashboard").hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                .cors()
                .disable()
                .csrf()
                .disable();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfig = new CorsConfiguration();
        List<String> allowedOrigins = new ArrayList<>();

        corsConfig.setAllowedMethods(List.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));

        corsConfig.applyPermitDefaultValues().setAllowedOrigins(allowedOrigins);
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("*");

        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }
}
