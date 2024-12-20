package com.example.project_management_tool.presentation.config;

import com.example.project_management_tool.application.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;


    public SecurityConfig (
            UserDetailsServiceImpl userDetailsService,
                           JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/auth/signup",
                            "auth/login",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                            ).permitAll()
                            .requestMatchers(HttpMethod.GET,"/companies").hasAuthority("COMPANY_READ")
                            .requestMatchers(HttpMethod.PATCH, "/companies").hasAuthority("COMPANY_UPDATE")
                            .requestMatchers(HttpMethod.GET, "/companies/{companyId}/managers").hasAuthority("MANAGER_READ")
                            .requestMatchers(HttpMethod.POST, "/companies/{companyId}/managers").hasAuthority("MANAGER_CREATE")
                            .requestMatchers(HttpMethod.PATCH, "/companies/{companyId}/managers").hasAuthority("MANAGER_UPDATE")
                            .requestMatchers(HttpMethod.DELETE, "/companies/{companyId}/managers").hasAuthority("MANAGER_DELETE")
                            .requestMatchers(HttpMethod.GET, "/companies/{companyId}/roles").hasAuthority("ROLE_READ")
                            .requestMatchers(HttpMethod.POST, "/companies/{companyId}/roles").hasAuthority("ROLE_CREATE")
                            .requestMatchers(HttpMethod.PATCH, "/companies/{companyId}/roles").hasAuthority("ROLE_UPDATE")
                            .requestMatchers(HttpMethod.DELETE, "/companies/{companyId}/roles").hasAuthority("ROLE_DELETE")
                            .requestMatchers(HttpMethod.GET, "/companies/{companyId}/users").hasAuthority("USER_READ")
                            .requestMatchers(HttpMethod.POST, "/companies/{companyId}/users").hasAuthority("USER_CREATE")
                            .requestMatchers(HttpMethod.PATCH, "/companies/{companyId}/users").hasAuthority("USER_UPDATE")
                            .requestMatchers(HttpMethod.DELETE, "/companies/{companyId}/users").hasAuthority("USER_DELETE")
                            .requestMatchers(HttpMethod.GET, "/companies/{companyId}/workspaces").hasAuthority("WORKSPACE_READ")
                            .requestMatchers(HttpMethod.POST, "/companies/{companyId}/workspaces").hasAuthority("WORKSPACE_CREATE")
                            .requestMatchers(HttpMethod.PATCH, "/companies/{companyId}/workspaces").hasAuthority("WORKSPACE_UPDATE")
                            .requestMatchers(HttpMethod.DELETE, "/companies/{companyId}/workspaces").hasAuthority("WORKSPACE_DELETE")

                            .anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager(http));
                /*.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll());*/

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}
