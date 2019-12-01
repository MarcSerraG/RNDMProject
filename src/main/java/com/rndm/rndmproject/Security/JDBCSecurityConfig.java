package com.rndm.rndmproject.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

//@Profile({"security_data_h2", "oracle"})
@EnableWebSecurity//(debug = true)
public class JDBCSecurityConfig extends BaseSecurityConfig {
    private DataSource dataSource;

    private static final String USERS_QUERY = "select username, password, enabled from usuari where username = ? and connected = 0";

    private static final String AUTHORITIES_QUERY = "select username, is_private, 'true' as enabled from usuari where username = ?";

    public JDBCSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Configure authentication manager
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USERS_QUERY)
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY);
    }
}
