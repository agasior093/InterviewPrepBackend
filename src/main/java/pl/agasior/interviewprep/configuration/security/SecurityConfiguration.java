package pl.agasior.interviewprep.configuration.security;

import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.agasior.interviewprep.configuration.security.jwt.TokenAuthenticationFilter;
import pl.agasior.interviewprep.configuration.security.oauth2.OAuth2AuthenticationFailureHandler;
import pl.agasior.interviewprep.configuration.security.oauth2.OAuth2AuthenticationSuccessHandler;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.interceptors.RestAuthenticationInterceptor;
import pl.agasior.interviewprep.repositories.CookieRequestRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final UserDetailsService userDetailsService;
    private final DefaultOAuth2UserService oAuth2UserService;
    private final PasswordEncoder encoder;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;
    private final CookieRequestRepository cookieRequestRepository;

    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/auth/**",
            "/tag/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationInterceptor())
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.GET, "/question/**")
                .permitAll()
                .antMatchers("/question/status").hasRole(Role.Admin.toString())
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieRequestRepository)
                .and()
                .redirectionEndpoint().baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint().userService(oAuth2UserService)
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
