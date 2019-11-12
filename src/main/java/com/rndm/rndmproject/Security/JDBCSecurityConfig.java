package com.rndm.rndmproject.Security;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import javax.sql.DataSource;

@EnableWebSecurity
public class JDBCSecurityConfig extends BaseSecurityConfig {
    private DataSource dataSource;


    private static final String USERS_QUERY = "select username, email, password , enabled from usuari where username = ?";

    private static final String AUTHORITIES_QUERY = "select username, is_private from usuari where username = ?";

    public JDBCSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY);
    }
}
