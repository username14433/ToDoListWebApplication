package org.rockend.config;

import org.rockend.entity.User;
import org.rockend.entity.UserRole;
import org.rockend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.Collections;
import java.util.Set;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Для определения того, какие роли нужны для доступа к каким страницам,
    //а также определение специальных эндпоинтов для логина и выхода их аккаунта
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //disable csrf
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/registration", "/error", "/css/**").permitAll()
                        .requestMatchers("/account/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name())
                        .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.name())
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .usernameParameter("email")
                        .defaultSuccessUrl("/account")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout").permitAll()
                )
                .build();
    }

    //Для хэширования пароля
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Для идентификации
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository
                        .findByEmailIgnoreCase(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User with email = " + username + " not found"));
                //Создаём сет из одного элемента - роли пользователя
                Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().toAuthority());
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
            }
        };
    }

    //AuthenticationManager дережирует процессом аутентификации, он нужен для того,
    // чтобы сертифицировать SecurityContext (чтобы Spring верил, что данный Security Context проверенный и надёжный)
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }

    //Данный бин нужен, чтобы сохранять SecurityContext между сессиями (то есть сохраняет его в Httpsession)
//    @Bean
//    public SecurityContextRepository securityContextRepository() {
//        return new HttpSessionSecurityContextRepository();
//    }

}
