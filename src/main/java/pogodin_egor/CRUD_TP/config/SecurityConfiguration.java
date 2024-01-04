package pogodin_egor.CRUD_TP.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(login->login.
                        loginPage("/auth/login").permitAll()
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/",true)
                        .failureUrl("/auth/login?error"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login").permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                )
                .authorizeHttpRequests(req-> req
                        .requestMatchers("auth/login/**").permitAll()
                        .requestMatchers("/admin/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/**").hasAnyAuthority("ADMIN", "USER")
                        .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
