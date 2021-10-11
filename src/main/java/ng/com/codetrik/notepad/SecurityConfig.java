/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad;
/*
 @Author Hamzat Habibllahi
 */

import ng.com.codetrik.notepad.user.AppUserDetailsService;
import ng.com.codetrik.notepad.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/users/authenticate").hasAnyRole(Role.USER.name(),Role.DEVELOPER.name())
                .antMatchers(HttpMethod.PUT,"/api/v1/users/{id}").hasAnyRole(Role.USER.name(),Role.DEVELOPER.name())
                .antMatchers(HttpMethod.DELETE,"/api/v1/users/{id}").hasRole(Role.DEVELOPER.name())
                .antMatchers(HttpMethod.GET,"/api/v1/notes").hasRole(Role.DEVELOPER.name())
                .antMatchers(HttpMethod.GET,"/api/v1/notes/{id}").hasRole(Role.DEVELOPER.name())
                .antMatchers(HttpMethod.POST,"/api/v1/notes").hasAnyRole(Role.USER.name(),Role.DEVELOPER.name())
                .antMatchers(HttpMethod.PUT,"/api/v1/notes/{id}").hasAnyRole(Role.USER.name(),Role.DEVELOPER.name())
                .antMatchers(HttpMethod.GET,"/api/v1/notes/{id}").hasAnyRole(Role.USER.name(),Role.DEVELOPER.name())
                .antMatchers(HttpMethod.GET,"/api/v1/notes").hasAnyRole(Role.USER.name(),Role.DEVELOPER.name())
                .antMatchers(HttpMethod.DELETE,"/api/v1/notes/{id}").hasAnyRole(Role.USER.name(),Role.DEVELOPER.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthProvider());
    }

    @Bean
    public UserDetailsService getUserDetailService(){
        return new AppUserDetailsService();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(10,new SecureRandom("cyberlord_abiz1992".getBytes()));
    }

    @Bean
    public DaoAuthenticationProvider getAuthProvider(){
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthProvider.setUserDetailsService(getUserDetailService());
        return daoAuthProvider;
    }
}
