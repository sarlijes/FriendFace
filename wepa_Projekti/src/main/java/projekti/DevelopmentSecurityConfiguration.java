package projekti;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(1000) 
@Configuration
public class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity sec) throws Exception {
        // Pyyntöjä ei tarkasteta
        sec.ignoring().antMatchers("/**");
    }
}
