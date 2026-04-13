package ru.shmelev.stomatologyapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final SetupFilter setupFilter;

    @Autowired
    public SecurityConfig(SetupFilter setupFilter) {
        this.setupFilter = setupFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(setupFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth

                        // public
                        .requestMatchers("/setup/**", "/css/**", "/js/**", "/login").permitAll()

                        // read
                        .requestMatchers("/doctors", "/appointments").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/doctors/{id}", "/appointments/{id}").hasAnyRole("ADMIN", "DOCTOR")

                        // write
                        .requestMatchers("/doctors/new", "/appointments/new", "/specializations/new").hasRole("ADMIN")
                        .requestMatchers("/doctors", "/doctors/{id}", "/appointments", "/appointments/done", "/specializations").hasRole("ADMIN")

                        // fallback
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/doctors", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}