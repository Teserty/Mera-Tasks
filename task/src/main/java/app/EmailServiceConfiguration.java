package app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmailServiceConfiguration {

    private final SimpleBean simpleBean;
    @Autowired
    public EmailServiceConfiguration(SimpleBean simpleBean) {
        this.simpleBean = simpleBean;
    }
    @Bean
    public SimpleBean getSimpleBean(){
        return new SimpleBean();
    }
}
