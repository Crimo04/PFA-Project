package com.example.CandidatMS.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;


//@Configuration
//@EnableWebSecurity
//public class SpringSecurity {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
//    }
//
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests((authorize) ->
//                        authorize.requestMatchers("/register/**").permitAll()
//                                .requestMatchers("/index").permitAll()
//                                .requestMatchers("/users").hasRole("ADMIN")
//                                .requestMatchers("/candidature/**").permitAll()
//                                .requestMatchers("/candidature/view/**").permitAll()
//                                .requestMatchers("/login").permitAll()
//
//
//                )
//                .formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .successHandler((request, response, authentication) -> {
//                                    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//                                    Long userId = userDetails.getId();
//                                    response.sendRedirect("/candidature/" + userId);
//                                })
//                                .permitAll()
//                )
//                .logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//
//
//}
@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/login"))
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/candidature/**").permitAll()
                                .requestMatchers("/candidature/view/**").permitAll()
                                .requestMatchers("/logout").permitAll()
                                .anyRequest().permitAll()
                )
                .httpBasic()
                .and() // Ajouter cette ligne
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    // Réponse par défaut si nécessaire
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"message\": \"Logout successful\"}");
                                })
                                .clearAuthentication(true)
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
