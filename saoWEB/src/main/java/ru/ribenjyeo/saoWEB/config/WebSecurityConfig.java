package ru.ribenjyeo.saoWEB.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import ru.ribenjyeo.saoWEB.service.jwt.AuthEntryPointJwt;
import ru.ribenjyeo.saoWEB.service.jwt.AuthTokenFilter;
import ru.ribenjyeo.saoWEB.security.service.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter ();
    }


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService (userDetailsService).passwordEncoder (passwordEncoder ());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean ();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring ()
                .antMatchers (HttpMethod.OPTIONS, "/**")
                .antMatchers ("/app/**/*.{js,html}")
                .antMatchers ("/content/**")
                .antMatchers ("/swagger-ui.html")
                .antMatchers ("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors ().and ().csrf ().disable ()
                .exceptionHandling ().authenticationEntryPoint (unauthorizedHandler).and ()
                .sessionManagement ().sessionCreationPolicy (SessionCreationPolicy.STATELESS).and ()
                .authorizeRequests ()
                .antMatchers ("/auth/**").permitAll ()
                .antMatchers ("/api/**").permitAll ()
                .anyRequest ().authenticated ();

        http.addFilterBefore (authenticationJwtTokenFilter (), UsernamePasswordAuthenticationFilter.class);
    }
}
