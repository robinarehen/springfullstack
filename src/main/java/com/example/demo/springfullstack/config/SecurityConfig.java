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
    protected void configure(HttpSecurity http) throws Exception {

        String[] patterms = new String[]{
                "/",
                "/register",
                "/users",
                "/addtask",
                "/css/**",
                "/webjars/**"
        };

        http.authorizeRequests()
                .antMatchers(patterms)
                .permitAll().anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/users")
                .and()
                .logout().logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String password = bCryptPasswordEncoder.encode("abc123");

        auth.inMemoryAuthentication()
                .passwordEncoder(bCryptPasswordEncoder)
                .withUser("user").password(password).roles("USER")
                .and()
                .withUser("admin").password(password).roles("ADMIN");

        //*/

        String userQuery = "SELECT email as principal, password as credentials, true FROM user WHERE email = ?";
        //String roleQuery = "SELECT user_email as principal, role_name as role FROM user_roles WHERE user_email = ?";

        auth.jdbcAuthentication()
                .dataSource(this.dataSource)
                .passwordEncoder(this.passwordEncoder())
                .usersByUsernameQuery(userQuery)
                //.authoritiesByUsernameQuery(roleQuery)
                .rolePrefix("ROLE_");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
