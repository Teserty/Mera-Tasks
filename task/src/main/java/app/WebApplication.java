package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


public class WebApplication implements WebApplicationInitializer {
    Logger logger = LoggerFactory.getLogger(WebApplication.class);
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.warn("deployed");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(EmailServiceConfiguration.class);
        context.refresh();
        context.setServletContext(servletContext);
        servletContext.addListener(new ContextLoaderListener(context));
    }

}
