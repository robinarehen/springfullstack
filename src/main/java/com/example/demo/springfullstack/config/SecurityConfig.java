package com.example.demo.springfullstack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        String userQuery = "SELECT email as principal, password as crendentails, true FROM user WHERE email = ?";
        String roleQuery = "SELECT user_email as principal, role_name as role FROM user_roles WHERE user_email = ?";

        auth.jdbcAuthentication()
                .dataSource(this.dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery)
                .passwordEncoder(this.passwordEncoder())
                .rolePrefix("ROLE_");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] patterns = new String[]{
                "/",
                "/register",
                "/css/**",
                "/webjars/**"
        };

        String[] patternsAdmin = new String[]{
                "/register",
                "/users",
                "/addTask"
        };

        String[] patternsUser = new String[]{
                "/profile"
        };

        http.authorizeRequests()
                .antMatchers(patterns)
                .permitAll()
                .antMatchers(patternsAdmin).hasRole("ADMIN")
                .antMatchers(patternsUser).hasAnyRole("ADMIN,USER")
                .and()
                .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/profile")
                .and()
                .logout().logoutSuccessUrl("/login");
    }
}
