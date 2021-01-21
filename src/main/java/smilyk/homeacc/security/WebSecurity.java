package smilyk.homeacc.security;


import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

import smilyk.homeacc.constants.SecurityConstants;
import smilyk.homeacc.service.user.UserService;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder
         ) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**добавляет cors*/
                .cors().and()
                .csrf().disable().authorizeRequests()

//        .antMatchers("/v1/user").permitAll()

//                /** разрешаем доступ для получения запосов с конкретного IP */
//                .antMatchers("/**")
//                .hasIpAddress(env.getProperty("gateway.ip"))
                /** проверка healthController - permit all */
                .antMatchers("/ping")
                .permitAll()
                /** registration -> method createUser - permit all */
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                .permitAll()

                /**verificstion e-mail - разрешена всем**/
                .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
                .permitAll()
//                /**изменение пароля - доступно всем**/
//                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
//                .permitAll()
//                /**вход по ссылке для изменения пароля из письма доступен всем*/
//                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
//                .permitAll()
//                /** доступ к свагеру */
//                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
//                .permitAll()
//                .antMatchers("/users/**").permitAll()
                .anyRequest().authenticated()
                .and()
                /**
                 * filtr for all object in procedure authentication
                 * фильтр для всех обьектов, которые проходят через процедуру аутентификации
                 */
//                .addFilter(getAuthenticationFilter())
                /**
                 * only user with token can work with project
                 * только тот, кто вошел в пприложение, может менять что то в нем
                 */
                .addFilter(new AuthorizationFilter(authenticationManager()));
                http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * логин пользователя теперь по ссылке ...v1/user/login
     * change user login link to ...v1/user/login
     *
     * @return
     * @throws Exception
     */
    protected AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

    /**
     * adding cors
     *
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
