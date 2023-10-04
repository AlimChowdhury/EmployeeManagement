package com.alim.EmployeeManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailsService uds;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public SecurityConfig(UserDetailsService uds, BCryptPasswordEncoder encoder) {
        this.uds = uds;
        this.encoder = encoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                .requestMatchers("/home", "/index", "/register", "/saveUser","/login").permitAll()
                .requestMatchers("/welcome").authenticated()
                .requestMatchers("/admin").hasAuthority("Admin")
                .requestMatchers("/mgr").hasAuthority("Manager")
                .requestMatchers("/emp").hasAuthority("Employee")
                .requestMatchers("/hr").hasAuthority("HR")
                .requestMatchers("/common").hasAnyAuthority("Employee", "Manager", "Admin")
                .anyRequest().authenticated()


                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome", true)
                //.loginPage(String.valueOf(new AntPathRequestMatcher("/login")))

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")

                .and()
                .authenticationProvider(authenticationProvider());

        return http.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(uds);
        authenticationProvider.setPasswordEncoder(encoder);
        return authenticationProvider;
    }
}