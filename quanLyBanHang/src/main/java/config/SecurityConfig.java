package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // Bỏ passwordEncoder bean - để Spring tự tạo DelegatingPasswordEncoder
    // DelegatingPasswordEncoder hiểu {noop}, {bcrypt}, etc.

    @Bean
    public UserDetailsService userDetailsService() {
        // Log để kiểm tra
        System.out.println("===========================================");
        System.out.println(">>> SecurityConfig: Creating admin user");
        System.out.println(">>> Username: admin");
        System.out.println(">>> Password: admin123 (plain text with {noop})");
        System.out.println("===========================================");
        
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // Cho phép TẤT CẢ requests
                // Security sẽ được handle bởi @PreAuthorize trong Controllers
                .anyRequest().permitAll()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
            .csrf()
                .ignoringAntMatchers("/h2-console/**")
            .and()
            .headers()
                .frameOptions().sameOrigin();

        return http.build();
    }
}
