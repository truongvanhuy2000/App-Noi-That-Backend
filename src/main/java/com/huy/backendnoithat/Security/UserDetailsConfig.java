package com.huy.backendnoithat.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class UserDetailsConfig {
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, active FROM account WHERE username=? "
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT account.username, roles.role " +
                        "FROM account " +
                        "INNER JOIN roles " +
                        "ON account.id=roles.account_id " +
                        "WHERE account.active=true and account.enabled=true " +
                        "AND account.username=?"
        );
        return jdbcUserDetailsManager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
