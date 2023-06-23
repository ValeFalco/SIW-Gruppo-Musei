package it.uniroma3.siw.musei.config;

import static it.uniroma3.siw.musei.model.enumeration.Ruolo.ADMIN;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource datasource;

    /* Permessi publici GET */
    private static final String[] PUBLIC_GET_PATH = {
            "/", "/index", "/login", "/register", "/images/**", "/layout/**"
    };

    /* Permessi publici POST */
    private static final String[] PUBLIC_POST_PATHS = {
            "/login", "/register"
    };

    /* Permessi amministratore GET */
    private static final String[] ADMIN_GET_PATH = {
            "/admin/**"
    };

    private static final String[] ADMIN_POST_PATH = {
            "/admin/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* Autorizzazioni */
                .authorizeRequests()
                /* Permessi Publici */
                .antMatchers(GET, PUBLIC_GET_PATH).permitAll()
                .antMatchers(POST, PUBLIC_POST_PATHS).permitAll()
                /* Permessi da Amministratore */
                .antMatchers(GET, ADMIN_GET_PATH).hasAnyAuthority(ADMIN.toString())
                .antMatchers(POST, ADMIN_POST_PATH).hasAnyAuthority(ADMIN.toString())
                /* Path restanti agli autenticati */
                .anyRequest().authenticated()
                .and()
                /* login */
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/default")
                .failureUrl("/login?error=true")
                .and()
                /* logout paragraph: qui definiamo il logout */
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(this.datasource)
                .authoritiesByUsernameQuery("SELECT username, ruolo FROM credenziali WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username=?");
    }

}
