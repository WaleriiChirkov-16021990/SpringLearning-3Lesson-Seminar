package ru.gb.springdemo.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.springdemo.service.PersonDetailService;

import javax.sql.DataSource;

//@Configuration
@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    public final PasswordEncoder passwordEncoder;
    private final PersonDetailService personDetailService;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder, PersonDetailService personDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.personDetailService = personDetailService;
    }

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


    //    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//
////                .httpBasic(Customizer.withDefaults())
////                .csrf(csrfToken -> csrfToken.disable())
////                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
////                                authorizationManagerRequestMatcherRegistry
////                                        .requestMatchers("/public", "/api/**").permitAll()
//////                                .requestMatchers("/public","/api/**").permitAll()
////                                        .requestMatchers("/issues/**").hasAuthority("ISSUE")
////                                        .requestMatchers("/readers/**").hasAuthority("READER")
////                                        .requestMatchers("/books/**").hasAuthority("BOOK")
//////                                .requestMatchers("/api/**").authenticated()
//////                                        .anyRequest().denyAll()
////                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/auth/login")
//                        .loginProcessingUrl("/process_login")
//                        .defaultSuccessUrl("/auth/people", true)
//                        .failureUrl("/auth/login?error"))
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/auth/login"))
//                .build();
//    }

    //    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
//                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                                .requestMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                                .requestMatchers("/auth/**", "/api/**", "/api/people/**", "/api/roles/**", "/api/people_roles/*").permitAll()
                                .requestMatchers("/ui/issues/**").hasAuthority("ISSUE")
                                .requestMatchers("/ui/books/**").hasAuthority("BOOK")
                                .requestMatchers("/ui/readers/**").hasAuthority("READER")
                                .anyRequest().permitAll()
//                        .requestMatchers("/api/people", "/api/roles").authenticated()
//                        .anyRequest().denyAll()
                )
                .formLogin(formLogin -> formLogin.loginPage("/api/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/api/people", true)
                        .failureUrl("/login.html?error=true")
                )
                .userDetailsService(personDetailService)
                .logout(logout -> logout
                        .logoutUrl("/logout")
                )
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
