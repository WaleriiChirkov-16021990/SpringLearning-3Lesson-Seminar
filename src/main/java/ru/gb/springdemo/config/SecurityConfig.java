package ru.gb.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.springdemo.service.PersonDetailService;

@EnableWebSecurity
public class SecurityConfig {
//    private final PersonDetailService personDetailService;

//    @Autowired
//    public SecurityConfig(PersonDetailService personDetailService) {
//        this.personDetailService = personDetailService;
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/api/auth/login", "/api/auth/register", "/error")
//                .permitAll()
//                .antMatchers("/admin/**").hasAuthority("admin")
//                .antMatchers("/admin/**").hasAuthority("user")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/auth/login")
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/api/people", true)
//                .failureUrl("/auth/login?error")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/api/auth/login");
//    }
//

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//                .csrf(csrfToken -> csrfToken.disable())
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                                authorizationManagerRequestMatcherRegistry
//                                        .requestMatchers("/public", "/api/**").permitAll()
////                                .requestMatchers("/public","/api/**").permitAll()
//                                        .requestMatchers("/issues/**").hasAuthority("ISSUE")
//                                        .requestMatchers("/readers/**").hasAuthority("READER")
//                                        .requestMatchers("/books/**").hasAuthority("BOOK")
////                                .requestMatchers("/api/**").authenticated()
////                                        .anyRequest().denyAll()
//                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/auth/people", true)
                        .failureUrl("/auth/login?error"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"))
                .build();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(personDetailService)
//                .passwordEncoder(getPasswordEncoder());
//    }

//    @Autowired
//    public void configureAuthManagerBuilder(AuthenticationManagerBuilder builder,PersonDetailService personDetailService) throws Exception {
//        builder.userDetailsService(personDetailService)
//                .passwordEncoder(getPasswordEncoder());
//    }

    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
